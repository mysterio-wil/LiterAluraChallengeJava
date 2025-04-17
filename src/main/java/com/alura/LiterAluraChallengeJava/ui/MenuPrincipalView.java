package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.SpringContextProvider;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.util.ExportarLibrosUtil;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import com.alura.LiterAluraChallengeJava.model.Autor;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.FileChooser;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.opencsv.CSVReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class MenuPrincipalView {
    @Autowired
    private LibroRepository libroRepository;
    @Autowired
    private AutorRepository autorRepository;

    // --- Utilidad para normalizar cadenas (título, autor) ---
    public static String normalizar(String s) {
        if (s == null) return "";
        return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                .replaceAll("[^a-zA-Z0-9 ]", "")
                .toLowerCase()
                .replaceAll("\\s+", " ")
                .trim();
    }

    public void mostrar(Stage stage) {
        BorderPane root = new BorderPane();

        // Menú superior
        MenuBar menuBar = new MenuBar();
        Menu menuArchivo = new Menu("Archivo");
        MenuItem salir = new MenuItem("Salir");
        salir.setOnAction(evSalir -> {
            stage.close();
            javafx.application.Platform.exit();
            System.exit(0);
        });
        menuArchivo.getItems().addAll(salir);

        Menu menuHerramientas = new Menu("Herramientas");
        // Sección principal
        MenuItem buscarLibros = new MenuItem("Buscar libros");
        MenuItem importarGutendex = new MenuItem("Importar desde Gutendex");
        Menu menuImportarArchivo = new Menu("Importar desde archivo...");
        MenuItem importarCSV = new MenuItem("CSV");
        MenuItem importarExcel = new MenuItem("Excel");
        menuImportarArchivo.getItems().addAll(importarCSV, importarExcel);
        // Exportar datos como submenú
        Menu menuExportarDatos = new Menu("Exportar datos");
        MenuItem exportarCSV = new MenuItem("CSV");
        MenuItem exportarExcel = new MenuItem("Excel");
        menuExportarDatos.getItems().addAll(exportarCSV, exportarExcel);
        MenuItem exportarFavoritos = new MenuItem("Exportar favoritos");
        menuHerramientas.getItems().addAll(
            buscarLibros,
            importarGutendex,
            menuImportarArchivo,
            menuExportarDatos,
            exportarFavoritos,
            new SeparatorMenuItem()
        );
        // Gestión de autores
        Menu menuAutores = new Menu("Gestión de autores");
        MenuItem verAutores = new MenuItem("Ver autores");
        MenuItem agregarAutor = new MenuItem("Agregar autor");
        MenuItem editarAutor = new MenuItem("Editar autor");
        MenuItem eliminarAutor = new MenuItem("Eliminar autor");
        menuAutores.getItems().addAll(verAutores, agregarAutor, editarAutor, eliminarAutor);
        menuHerramientas.getItems().addAll(menuAutores, new SeparatorMenuItem());
        // Estadísticas
        Menu menuEstadisticas = new Menu("Estadísticas");
        MenuItem librosPorIdioma = new MenuItem("Libros por idioma");
        MenuItem librosMasDescargados = new MenuItem("Libros más descargados");
        MenuItem autoresProlificos = new MenuItem("Autores más prolíficos");
        MenuItem descargasTotales = new MenuItem("Descargas totales");
        menuEstadisticas.getItems().addAll(librosPorIdioma, librosMasDescargados, autoresProlificos, descargasTotales);
        menuHerramientas.getItems().addAll(menuEstadisticas, new SeparatorMenuItem());
        // Respaldar/restaurar
        MenuItem respaldarBD = new MenuItem("Respaldar base de datos");
        MenuItem restaurarBD = new MenuItem("Restaurar base de datos");
        menuHerramientas.getItems().addAll(respaldarBD, restaurarBD, new SeparatorMenuItem());
        // Configuración
        Menu menuConfiguracion = new Menu("Configuración");
        MenuItem tema = new MenuItem("Tema claro/oscuro");
        MenuItem idioma = new MenuItem("Idioma de la interfaz");
        menuConfiguracion.getItems().addAll(tema, idioma);
        menuHerramientas.getItems().addAll(menuConfiguracion, new SeparatorMenuItem());
        // Ayuda
        MenuItem ayuda = new MenuItem("Ayuda / Acerca de");
        menuHerramientas.getItems().addAll(ayuda);
        // Añadir menús al menú bar
        menuBar.getMenus().setAll(menuArchivo, menuHerramientas);

        // Handlers para acciones ya implementadas
        buscarLibros.setOnAction(evBuscar -> {
            BusquedaLibrosView busquedaView = SpringContextProvider.getBean(BusquedaLibrosView.class);
            busquedaView.mostrar(stage);
        });
        importarGutendex.setOnAction(evGutendex -> {
            BuscarGutendexView buscarGutendexView = SpringContextProvider.getBean(BuscarGutendexView.class);
            buscarGutendexView.mostrar(stage);
        });

        // Handlers para importar desde archivo (CSV y Excel)
        importarCSV.setOnAction(evCSV -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;
            try (CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                AtomicInteger totalLeidos = new AtomicInteger(0);
                AtomicInteger totalDuplicados = new AtomicInteger(0);
                AtomicInteger totalErrores = new AtomicInteger(0);
                javafx.stage.Stage progressStage = new javafx.stage.Stage();
                progressStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                progressStage.setTitle("Importando libros...");
                javafx.scene.control.ProgressBar progressBar = new javafx.scene.control.ProgressBar(0);
                javafx.scene.control.Label progressLabel = new javafx.scene.control.Label("Iniciando importación...");
                javafx.scene.control.ListView<String> librosListView = new javafx.scene.control.ListView<>();
                librosListView.setPrefHeight(200);
                javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10, progressLabel, progressBar, librosListView);
                vbox.setStyle("-fx-padding: 20; -fx-min-width: 400px; -fx-min-height: 300px;");
                progressStage.setScene(new javafx.scene.Scene(vbox));
                progressStage.setResizable(false);
                progressStage.show();
                // Lee todas las líneas
                java.util.List<String[]> todasLasLineas = new java.util.ArrayList<>();
                String[] parts;
                boolean firstLine = true;
                while ((parts = reader.readNext()) != null) {
                    if (firstLine) { firstLine = false; continue; }
                    todasLasLineas.add(parts);
                }
                int totalFilas = todasLasLineas.size();
                javafx.concurrent.Task<Void> importTask = new javafx.concurrent.Task<>() {
                    @Override
                    protected Void call() {
                        for (int i = 0; i < totalFilas; i++) {
                            String[] parts = todasLasLineas.get(i);
                            if (parts.length != 6) continue;
                            String titulo = parts[0];
                            String autorNombre = parts[1];
                            String idiomaLibro = parts[2];
                            Integer fechaNacimiento = null;
                            Integer fechaFallecimiento = null;
                            try { fechaNacimiento = parts[3].trim().isEmpty() ? null : Integer.parseInt(parts[3].trim()); } catch (Exception ignored) {}
                            try { fechaFallecimiento = parts[4].trim().isEmpty() ? null : Integer.parseInt(parts[4].trim()); } catch (Exception ignored) {}
                            int descargas = 0;
                            try { descargas = parts[5].trim().isEmpty() ? 0 : Integer.parseInt(parts[5].trim()); } catch (Exception ignored) {}
                            final Integer fnac = fechaNacimiento;
                            final Integer ffalle = fechaFallecimiento;
                            Optional<Autor> autorOpt = autorRepository.findByNombre(autorNombre);
                            Autor autor;
                            if (autorOpt.isPresent()) {
                                autor = autorOpt.get();
                                if (fnac != null) autor.setFechaNacimiento(fnac);
                                if (ffalle != null) autor.setFechaFallecimiento(ffalle);
                                autorRepository.save(autor);
                            } else {
                                autor = new Autor();
                                autor.setNombre(autorNombre);
                                autor.setFechaNacimiento(fnac);
                                autor.setFechaFallecimiento(ffalle);
                                autor = autorRepository.save(autor);
                            }
                            String tituloNorm = normalizar(titulo);
                            String autorNorm = normalizar(autorNombre);
                            boolean existe = libroRepository.findAll().stream()
                                    .anyMatch(l -> normalizar(l.getTitulo()).equals(tituloNorm)
                                            && normalizar(l.getAutor().getNombre()).equals(autorNorm)
                                            && normalizar(l.getIdioma()).equals(normalizar(idiomaLibro)));
                            if (existe) {
                                totalDuplicados.incrementAndGet();
                                final int progress = i + 1;
                                final String msg = "Duplicado ignorado: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                                continue;
                            }
                            Libro libro = new Libro();
                            libro.setTitulo(titulo);
                            libro.setAutor(autor);
                            libro.setIdioma(idiomaLibro);
                            libro.setNumeroDescargas(descargas);
                            try {
                                libroRepository.save(libro);
                                totalLeidos.incrementAndGet();
                                final int progress = i + 1;
                                final String msg = "Importado: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                            } catch (org.springframework.dao.DataIntegrityViolationException dupEx) {
                                totalDuplicados.incrementAndGet();
                                final int progress = i + 1;
                                final String msg = "Duplicado ignorado: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                            } catch (Exception exLibro) {
                                totalErrores.incrementAndGet();
                                final int progress = i + 1;
                                final String msg = "Error: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                            }
                        }
                        return null;
                    }
                };
                progressBar.progressProperty().bind(importTask.progressProperty());
                progressLabel.textProperty().bind(importTask.messageProperty());
                importTask.setOnSucceeded(evCSVTask -> {
                    progressStage.close();
                    Alert ok = new Alert(Alert.AlertType.INFORMATION, "Importación CSV finalizada: " + totalLeidos.get() + " libros importados. " + totalDuplicados.get() + " duplicados ignorados. " + (totalErrores.get() > 0 ? ("Errores: " + totalErrores.get()) : ""));
                    ok.setTitle("Éxito");
                    ok.showAndWait();
                });
                new Thread(importTask).start();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al importar CSV: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });
        importarExcel.setOnAction(evExcel -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;
            try (FileInputStream fis = new FileInputStream(file)) {
                Workbook workbook = new XSSFWorkbook(fis);
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
                int columnasEsperadas = 6;
                AtomicInteger totalLeidos = new AtomicInteger(0);
                AtomicInteger totalDuplicados = new AtomicInteger(0);
                AtomicInteger totalErrores = new AtomicInteger(0);
                List<Libro> librosImportados = new ArrayList<>();

                // --- Ventana flotante de progreso usando Task ---
                javafx.stage.Stage progressStage = new javafx.stage.Stage();
                progressStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
                progressStage.setTitle("Importando libros...");
                javafx.scene.control.ProgressBar progressBar = new javafx.scene.control.ProgressBar(0);
                javafx.scene.control.Label progressLabel = new javafx.scene.control.Label("Iniciando importación...");
                javafx.scene.control.ListView<String> librosListView = new javafx.scene.control.ListView<>();
                librosListView.setPrefHeight(200);
                javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox(10, progressLabel, progressBar, librosListView);
                vbox.setStyle("-fx-padding: 20; -fx-min-width: 400px; -fx-min-height: 300px;");
                progressStage.setScene(new javafx.scene.Scene(vbox));
                progressStage.setResizable(false);
                progressStage.show();
                // --- Fin ventana flotante ---

                // --- Task para importación en segundo plano ---
                int totalFilas = sheet.getLastRowNum();
                Task<Void> importTask = new Task<>() {
                    @Override
                    protected Void call() {
                        for (int i = 1; i <= totalFilas; i++) {
                            org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                            if (row == null || row.getPhysicalNumberOfCells() != columnasEsperadas) continue;
                            String titulo = row.getCell(0).getStringCellValue();
                            String autorNombre = row.getCell(1).getStringCellValue();
                            String idiomaLibro = row.getCell(2).getStringCellValue();
                            Integer fechaNacimiento = null;
                            Integer fechaFallecimiento = null;
                            try { fechaNacimiento = row.getCell(3) == null ? null : (int) row.getCell(3).getNumericCellValue(); } catch (Exception ignored) {}
                            try { fechaFallecimiento = row.getCell(4) == null ? null : (int) row.getCell(4).getNumericCellValue(); } catch (Exception ignored) {}
                            int descargas = 0;
                            try { descargas = row.getCell(5) == null ? 0 : (int) row.getCell(5).getNumericCellValue(); } catch (Exception ignored) {}
                            final Integer fnac = fechaNacimiento;
                            final Integer ffalle = fechaFallecimiento;
                            Optional<Autor> autorOpt = autorRepository.findByNombre(autorNombre);
                            Autor autor;
                            if (autorOpt.isPresent()) {
                                autor = autorOpt.get();
                                if (fnac != null) autor.setFechaNacimiento(fnac);
                                if (ffalle != null) autor.setFechaFallecimiento(ffalle);
                                autorRepository.save(autor);
                            } else {
                                autor = new Autor();
                                autor.setNombre(autorNombre);
                                autor.setFechaNacimiento(fnac);
                                autor.setFechaFallecimiento(ffalle);
                                autor = autorRepository.save(autor);
                            }
                            // Normalización para comparación de duplicados (Excel)
                            String tituloNorm = normalizar(titulo);
                            String autorNorm = normalizar(autorNombre);
                            boolean existe = libroRepository.findAll().stream()
                                    .anyMatch(l -> normalizar(l.getTitulo()).equals(tituloNorm)
                                            && normalizar(l.getAutor().getNombre()).equals(autorNorm)
                                            && normalizar(l.getIdioma()).equals(normalizar(idiomaLibro)));
                            if (existe) {
                                totalDuplicados.incrementAndGet();
                                final int progress = i;
                                final String msg = "Duplicado ignorado: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                                continue;
                            }
                            Libro libro = new Libro();
                            libro.setTitulo(titulo);
                            libro.setAutor(autor);
                            libro.setIdioma(idiomaLibro);
                            libro.setNumeroDescargas(descargas);
                            try {
                                libroRepository.save(libro);
                                totalLeidos.incrementAndGet();
                                final int progress = i;
                                final String msg = "Importado: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                            } catch (org.springframework.dao.DataIntegrityViolationException dupEx) {
                                totalDuplicados.incrementAndGet();
                                final int progress = i;
                                final String msg = "Duplicado ignorado: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                            } catch (Exception exLibro) {
                                totalErrores.incrementAndGet();
                                final int progress = i;
                                final String msg = "Error: " + titulo;
                                updateProgress(progress, totalFilas);
                                updateMessage("Importando libro " + progress + " de " + totalFilas);
                                javafx.application.Platform.runLater(() -> librosListView.getItems().add(msg));
                            }
                        }
                        return null;
                    }
                };
                progressBar.progressProperty().bind(importTask.progressProperty());
                progressLabel.textProperty().bind(importTask.messageProperty());
                importTask.setOnSucceeded(evExcelTask -> {
                    progressStage.close();
                    try { workbook.close(); } catch (Exception ignore) {}
                    Alert ok = new Alert(Alert.AlertType.INFORMATION, "Importación Excel finalizada: " + totalLeidos.get() + " libros importados. " + totalDuplicados.get() + " duplicados ignorados. " + (totalErrores.get() > 0 ? ("Errores: " + totalErrores.get()) : ""));
                    ok.setTitle("Éxito");
                    ok.showAndWait();
                });
                new Thread(importTask).start();
                // --- Fin Task ---
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al importar Excel: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });

        // Handlers para exportar datos (CSV y Excel)
        exportarCSV.setOnAction(evExportCSV -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar libros como CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) return;
            try {
                List<Libro> libros = libroRepository.findAll();
                if (libros.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No hay libros para exportar.");
                    alert.setTitle("Exportar CSV");
                    alert.showAndWait();
                    return;
                }
                ExportarLibrosUtil.exportarCSV(libros, file);
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Exportación CSV exitosa. Archivo guardado en: " + file.getAbsolutePath());
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al exportar CSV: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });
        exportarExcel.setOnAction(evExportExcel -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar libros como Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) return;
            try {
                List<Libro> libros = libroRepository.findAll();
                if (libros.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No hay libros para exportar.");
                    alert.setTitle("Exportar Excel");
                    alert.showAndWait();
                    return;
                }
                ExportarLibrosUtil.exportarXLSX(libros, file);
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Exportación Excel exitosa. Archivo guardado en: " + file.getAbsolutePath());
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al exportar Excel: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });

        // Handler para exportar favoritos a CSV
        exportarFavoritos.setOnAction(evExportFav -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar favoritos como CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            File file = fileChooser.showSaveDialog(stage);
            if (file == null) return;
            try {
                var favoritoRepo = SpringContextProvider.getBean(com.alura.LiterAluraChallengeJava.repository.FavoritoLibroRepository.class);
                var favoritos = favoritoRepo.findAll();
                if (favoritos.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "No hay favoritos para exportar.");
                    alert.setTitle("Exportar favoritos");
                    alert.showAndWait();
                    return;
                }
                com.alura.LiterAluraChallengeJava.util.ExportarFavoritosUtil.exportarCSV(favoritos, file);
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Exportación de favoritos exitosa. Archivo guardado en: " + file.getAbsolutePath());
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al exportar favoritos: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });

        // Banner central
        Label mensaje = new Label("Bienvenidos a\nLiterAlura");
        mensaje.setStyle("-fx-font-size: 48px; -fx-font-family: 'Comic Sans MS', 'Comic Sans', cursive; -fx-text-alignment: center; -fx-font-weight: bold; -fx-text-fill: #222;");
        StackPane centro = new StackPane(mensaje);
        centro.setAlignment(Pos.CENTER);

        // Layout final
        VBox topBox = new VBox(menuBar);
        root.setTop(topBox);
        root.setCenter(centro);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("LiterAlura - Catálogo de Libros");
        stage.setScene(scene);
        stage.show();
    }
}

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

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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
        salir.setOnAction(e -> {
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
        buscarLibros.setOnAction(e -> {
            BusquedaLibrosView busquedaView = SpringContextProvider.getBean(BusquedaLibrosView.class);
            busquedaView.mostrar(stage);
        });
        importarGutendex.setOnAction(e -> {
            BuscarGutendexView buscarGutendexView = SpringContextProvider.getBean(BuscarGutendexView.class);
            buscarGutendexView.mostrar(stage);
        });

        // Handlers para importar desde archivo (CSV y Excel)
        importarCSV.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo CSV");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV (*.csv)", "*.csv"));
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean firstLine = true;
                int columnasEsperadas = 6;
                int totalLeidos = 0;
                int totalDuplicados = 0;
                List<Libro> librosImportados = new ArrayList<>();
                // Validar encabezado
                String encabezado = br.readLine();
                String encabezadoEsperado = "titulo,autor,idioma,fechaNacimiento,fechaFallecimiento,descargas";
                if (encabezado == null || !encabezado.replaceAll("\\s+", "").equalsIgnoreCase(encabezadoEsperado)) {
                    Alert err = new Alert(Alert.AlertType.ERROR, "El archivo no tiene el encabezado correcto.\nSe espera: " + encabezadoEsperado);
                    err.setTitle("Error de formato");
                    err.showAndWait();
                    return;
                }
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;
                    String[] parts = line.split(",");
                    if (parts.length != columnasEsperadas) continue;
                    String titulo = parts[0].replaceAll("^\"|\"$", "");
                    String autorNombre = parts[1].replaceAll("^\"|\"$", "");
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
                    // Normalización para comparación de duplicados
                    String tituloNorm = normalizar(titulo);
                    String autorNorm = normalizar(autorNombre);
                    boolean existe = libroRepository.findAll().stream()
                            .anyMatch(l -> normalizar(l.getTitulo()).equals(tituloNorm)
                                    && normalizar(l.getAutor().getNombre()).equals(autorNorm)
                                    && normalizar(l.getIdioma()).equals(normalizar(idiomaLibro)));
                    if (existe) {
                        totalDuplicados++;
                        continue;
                    }
                    Libro libro = new Libro();
                    libro.setTitulo(titulo);
                    libro.setAutor(autor);
                    libro.setIdioma(idiomaLibro);
                    libro.setNumeroDescargas(descargas);
                    librosImportados.add(libro);
                    totalLeidos++;
                }
                libroRepository.saveAll(librosImportados);
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Importación CSV exitosa: " + totalLeidos + " libros importados. " + totalDuplicados + " duplicados ignorados.");
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al importar CSV: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });
        importarExcel.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar archivo Excel");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel (*.xlsx)", "*.xlsx"));
            File file = fileChooser.showOpenDialog(stage);
            if (file == null) return;
            try (FileInputStream fis = new FileInputStream(file)) {
                Workbook workbook = new XSSFWorkbook(fis);
                org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);
                int columnasEsperadas = 6;
                int totalLeidos = 0;
                int totalDuplicados = 0;
                List<Libro> librosImportados = new ArrayList<>();
                // Validar encabezado
                org.apache.poi.ss.usermodel.Row headerRow = sheet.getRow(0);
                String encabezadoEsperado = "titulo,autor,idioma,fechaNacimiento,fechaFallecimiento,descargas";
                StringBuilder encabezadoArchivo = new StringBuilder();
                for (int c = 0; c < columnasEsperadas; c++) {
                    if (c > 0) encabezadoArchivo.append(",");
                    encabezadoArchivo.append(headerRow.getCell(c).getStringCellValue().replaceAll("\\s+", ""));
                }
                if (!encabezadoArchivo.toString().equalsIgnoreCase(encabezadoEsperado)) {
                    Alert err = new Alert(Alert.AlertType.ERROR, "El archivo Excel no tiene el encabezado correcto.\nSe espera: " + encabezadoEsperado);
                    err.setTitle("Error de formato");
                    err.showAndWait();
                    workbook.close();
                    return;
                }
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Saltar encabezado
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
                        totalDuplicados++;
                        continue;
                    }
                    Libro libro = new Libro();
                    libro.setTitulo(titulo);
                    libro.setAutor(autor);
                    libro.setIdioma(idiomaLibro);
                    libro.setNumeroDescargas(descargas);
                    librosImportados.add(libro);
                    totalLeidos++;
                }
                libroRepository.saveAll(librosImportados);
                workbook.close();
                Alert ok = new Alert(Alert.AlertType.INFORMATION, "Importación Excel exitosa: " + totalLeidos + " libros importados. " + totalDuplicados + " duplicados ignorados.");
                ok.setTitle("Éxito");
                ok.showAndWait();
            } catch (Exception ex) {
                Alert err = new Alert(Alert.AlertType.ERROR, "Error al importar Excel: " + ex.getMessage());
                err.setTitle("Error");
                err.showAndWait();
            }
        });

        // Handlers para exportar datos (CSV y Excel)
        exportarCSV.setOnAction(e -> {
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
        exportarExcel.setOnAction(e -> {
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

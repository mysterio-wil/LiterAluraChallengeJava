package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.gutendex.GutendexService;
import com.alura.LiterAluraChallengeJava.gutendex.LibroGutendexDTO;
import com.alura.LiterAluraChallengeJava.model.Usuario;
import com.alura.LiterAluraChallengeJava.repository.UsuarioRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BuscarGutendexView {
    @Autowired
    private GutendexService gutendexService;

    @Autowired
    private MenuPrincipalView menuPrincipalView;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void mostrar(Stage stage) {
        Label lblBuscar = new Label("Buscar libro por título:");
        TextField txtBuscar = new TextField();
        Label lblAyuda = new Label("Ingrese una palabra clave o parte del título. Ejemplo: quijote, don quijote, pride, etc.");
        lblAyuda.setStyle("-fx-font-size: 11px; -fx-text-fill: #555;");
        Label lblIdioma = new Label("Idioma:");
        TextField txtIdioma = new TextField();

        Button btnBuscar = new Button("Buscar en Gutendex");
        Button btnImportar = new Button("Importar a base de datos");
        Button btnVolver = new Button("Volver");

        TableView<LibroGutendexDTO> tablaResultados = new TableView<>();
        TableColumn<LibroGutendexDTO, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        TableColumn<LibroGutendexDTO, String> colAutor = new TableColumn<>("Autor(es)");
        colAutor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAutores()));
        TableColumn<LibroGutendexDTO, String> colIdioma = new TableColumn<>("Idioma");
        colIdioma.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdioma()));
        TableColumn<LibroGutendexDTO, Number> colDescargas = new TableColumn<>("Descargas");
        colDescargas.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getDescargas()));
        tablaResultados.getColumns().addAll(colTitulo, colAutor, colIdioma, colDescargas);
        tablaResultados.setPrefHeight(180);

        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
        Runnable limpiarMensaje = () -> Platform.runLater(() -> lblMensaje.setText(""));

        Button btnFavorito = new Button("Agregar a favoritos");
        btnFavorito.setOnAction(ev -> {
            Platform.runLater(() -> lblMensaje.setText("Intentando agregar a favoritos..."));
            new Thread(() -> {
                try {
                    LibroGutendexDTO libroSeleccionado = tablaResultados.getSelectionModel().getSelectedItem();
                    if (libroSeleccionado == null) {
                        Platform.runLater(() -> {
                            lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                            lblMensaje.setText("Por favor selecciona un libro de la tabla.");
                        });
                        return;
                    }
                    var libroRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.LibroRepository.class);
                    var autorRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.AutorRepository.class);
                    var favoritoRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.FavoritoLibroRepository.class);
                    String nombreAutor = libroSeleccionado.getAutores();
                    var autorOpt = autorRepo.findByNombre(nombreAutor);
                    if (autorOpt.isEmpty()) {
                        Platform.runLater(() -> {
                            lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                            lblMensaje.setText("Primero importa el libro a la base de datos antes de agregarlo a favoritos.");
                        });
                        return;
                    }
                    var autor = autorOpt.get();
                    var libroOpt = libroRepo.findByTituloAndAutorNombreAndIdiomaAndNumeroDescargas(
                        libroSeleccionado.getTitulo(),
                        libroSeleccionado.getAutores(),
                        libroSeleccionado.getIdioma(),
                        libroSeleccionado.getDescargas()
                    );
                    if (libroOpt.isEmpty()) {
                        Platform.runLater(() -> {
                            lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                            lblMensaje.setText("Primero importa el libro a la base de datos antes de agregarlo a favoritos.");
                        });
                        return;
                    }
                    var libro = libroOpt.get();
                    boolean yaEsFavorito = favoritoRepo.findByLibro(libro).isPresent();
                    if (yaEsFavorito) {
                        Platform.runLater(() -> {
                            lblMensaje.setStyle("-fx-text-fill: #006400; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                            lblMensaje.setText("Este libro ya está en favoritos.");
                        });
                        return;
                    }
                    // Obtener usuario por defecto
                    Usuario usuarioDefault = usuarioRepository.findByNombre("default");
                    if (usuarioDefault == null) {
                        usuarioDefault = usuarioRepository.save(new Usuario("default"));
                    }
                    com.alura.LiterAluraChallengeJava.model.FavoritoLibro favorito = new com.alura.LiterAluraChallengeJava.model.FavoritoLibro(libro, usuarioDefault);
                    favoritoRepo.save(favorito);
                    Platform.runLater(() -> {
                        lblMensaje.setStyle("-fx-text-fill: #006400; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                        lblMensaje.setText("Añadido con éxito");
                    });
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Platform.runLater(() -> {
                        lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                        lblMensaje.setText("Error: " + ex.getMessage());
                    });
                } finally {
                    try { Thread.sleep(5000); } catch (InterruptedException ignored) {}
                    limpiarMensaje.run();
                }
            }).start();
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        form.add(lblBuscar, 0, 0);
        form.add(txtBuscar, 1, 0);
        form.add(lblAyuda, 1, 1);
        form.add(lblIdioma, 0, 2);
        form.add(txtIdioma, 1, 2);

        HBox botones = new HBox(10, btnBuscar, btnImportar, btnFavorito, btnVolver);
        botones.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(20, form, botones, lblMensaje, tablaResultados);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        btnBuscar.setOnAction(e -> {
            lblMensaje.setText("");
            tablaResultados.setItems(FXCollections.observableArrayList());
            btnBuscar.setDisable(true);
            new Thread(() -> {
                try {
                    List<LibroGutendexDTO> resultados = gutendexService.buscarLibros(
                        txtBuscar.getText(),
                        txtIdioma.getText()
                    );
                    Platform.runLater(() -> {
                        ObservableList<LibroGutendexDTO> data = FXCollections.observableArrayList(resultados);
                        if (data.isEmpty()) {
                            lblMensaje.setText("No se encontró ningún resultado en Gutendex para los criterios ingresados. Pruebe con una sola palabra clave o variantes del título.");
                        } else {
                            lblMensaje.setText("");
                        }
                        tablaResultados.setItems(data);
                        btnBuscar.setDisable(false);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        lblMensaje.setText("Error al consultar la API de Gutendex");
                        btnBuscar.setDisable(false);
                    });
                }
            }).start();
        });

        btnImportar.setOnAction(e -> {
            LibroGutendexDTO libroSeleccionado = tablaResultados.getSelectionModel().getSelectedItem();
            if (libroSeleccionado == null) {
                lblMensaje.setStyle("-fx-text-fill: #B22222; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                lblMensaje.setText("Por favor selecciona un libro de la tabla.");
                return;
            }
            lblMensaje.setText("");
            btnImportar.setDisable(true);
            new Thread(() -> {
                try {
                    var libroRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.LibroRepository.class);
                    var autorRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.AutorRepository.class);

                    // --- Normalización de campos para evitar duplicados ---
                    String tituloNorm = libroSeleccionado.getTitulo().trim().toLowerCase();
                    String idiomaNorm = libroSeleccionado.getIdioma().trim().toLowerCase();
                    String nombreAutorNorm = libroSeleccionado.getAutores().trim().toLowerCase();

                    // Buscar autor normalizado
                    com.alura.LiterAluraChallengeJava.model.Autor autor = autorRepo.findByNombre(nombreAutorNorm)
                        .orElseGet(() -> autorRepo.save(new com.alura.LiterAluraChallengeJava.model.Autor(nombreAutorNorm, null, null)));

                    // Buscar libro por entidad Autor y campos normalizados
                    var libroOpt = libroRepo.findByTituloAndAutorAndIdioma(tituloNorm, autor, idiomaNorm);
                    if (libroOpt.isPresent()) {
                        // Si ya existe, actualiza descargas si lo deseas
                        com.alura.LiterAluraChallengeJava.model.Libro libroExistente = libroOpt.get();
                        libroExistente.setNumeroDescargas(libroSeleccionado.getDescargas());
                        libroRepo.save(libroExistente);
                        Platform.runLater(() -> {
                            lblMensaje.setText("El libro ya existe. Se actualizó el número de descargas.");
                            btnImportar.setDisable(false);
                        });
                        return;
                    }
                    // Si no existe, crea uno nuevo
                    com.alura.LiterAluraChallengeJava.model.Libro libro = new com.alura.LiterAluraChallengeJava.model.Libro(
                        tituloNorm,
                        idiomaNorm,
                        libroSeleccionado.getDescargas(),
                        autor
                    );
                    libroRepo.save(libro);
                    Platform.runLater(() -> {
                        lblMensaje.setStyle("-fx-text-fill: #006400; -fx-font-size: 12px; -fx-padding: 8 0 0 0;");
                        lblMensaje.setText("Libro importado correctamente.");
                        btnImportar.setDisable(false);
                    });
                } catch (Exception ex) {
                    Platform.runLater(() -> {
                        lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px;");
                        lblMensaje.setText("Error al importar el libro: " + ex.getMessage());
                        btnImportar.setDisable(false);
                    });
                }
            }).start();
        });

        btnVolver.setOnAction(e -> menuPrincipalView.mostrar(stage));

        Scene scene = new Scene(vbox, 700, 400);
        stage.setTitle("Buscar libros en Gutendex");
        stage.setScene(scene);
        stage.show();
    }
}

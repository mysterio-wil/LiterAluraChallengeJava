package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.gutendex.GutendexService;
import com.alura.LiterAluraChallengeJava.gutendex.LibroGutendexDTO;
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

        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-text-fill: #c00; -fx-font-size: 12px;");

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

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setAlignment(Pos.CENTER);
        form.add(lblBuscar, 0, 0);
        form.add(txtBuscar, 1, 0);
        form.add(lblAyuda, 1, 1);
        form.add(lblIdioma, 0, 2);
        form.add(txtIdioma, 1, 2);

        HBox botones = new HBox(10, btnBuscar, btnImportar, btnVolver);
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
            LibroGutendexDTO seleccionado = tablaResultados.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                lblMensaje.setText("Seleccione un libro para importar.");
                return;
            }
            lblMensaje.setText("");
            btnImportar.setDisable(true);
            new Thread(() -> {
                try {
                    // Obtener repositorios desde Spring
                    var libroRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.LibroRepository.class);
                    var autorRepo = com.alura.LiterAluraChallengeJava.SpringContextProvider.getBean(
                        com.alura.LiterAluraChallengeJava.repository.AutorRepository.class);
                    // Buscar o crear autor
                    String nombreAutor = seleccionado.getAutores();
                    com.alura.LiterAluraChallengeJava.model.Autor autor = autorRepo.findByNombre(nombreAutor)
                        .orElseGet(() -> autorRepo.save(new com.alura.LiterAluraChallengeJava.model.Autor(nombreAutor, null, null)));
                    // Buscar si ya existe el libro
                    boolean existe = libroRepo.findByTituloYAutor(seleccionado.getTitulo(), nombreAutor).isPresent();
                    if (existe) {
                        Platform.runLater(() -> {
                            lblMensaje.setText("El libro ya existe en la base de datos.");
                            btnImportar.setDisable(false);
                        });
                        return;
                    }
                    // Crear y guardar libro
                    com.alura.LiterAluraChallengeJava.model.Libro libro = new com.alura.LiterAluraChallengeJava.model.Libro(
                        seleccionado.getTitulo(),
                        seleccionado.getIdioma(),
                        seleccionado.getDescargas(),
                        autor
                    );
                    libroRepo.save(libro);
                    Platform.runLater(() -> {
                        lblMensaje.setStyle("-fx-text-fill: green; -fx-font-size: 12px;");
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

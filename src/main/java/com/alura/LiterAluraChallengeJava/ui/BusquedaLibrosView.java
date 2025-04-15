package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.model.Libro;
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
public class BusquedaLibrosView {
    @Autowired
    private BusquedaLibrosController controller;

    public void mostrar(Stage stage) {
        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();
        Label lblAutor = new Label("Autor:");
        TextField txtAutor = new TextField();
        Label lblIdioma = new Label("Idioma:");
        TextField txtIdioma = new TextField();
        Button btnBuscar = new Button("Buscar");
        Button btnVolver = new Button("Volver");

        TableView<Libro> tablaResultados = new TableView<>();
        TableColumn<Libro, String> colTitulo = new TableColumn<>("Título");
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        TableColumn<Libro, String> colAutor = new TableColumn<>("Autor");
        colAutor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(
                data.getValue().getAutor() != null ? data.getValue().getAutor().getNombre() : ""));
        TableColumn<Libro, String> colIdioma = new TableColumn<>("Idioma");
        colIdioma.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getIdioma()));
        TableColumn<Libro, Integer> colDescargas = new TableColumn<>("Descargas");
        colDescargas.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(
                data.getValue().getNumeroDescargas() != null ? data.getValue().getNumeroDescargas() : 0).asObject());
        tablaResultados.getColumns().addAll(colTitulo, colAutor, colIdioma, colDescargas);

        // Mensaje de feedback
        Label lblMensaje = new Label();
        lblMensaje.setStyle("-fx-text-fill: red; -fx-font-size: 12px;");

        btnBuscar.setOnAction(e -> {
            String titulo = txtTitulo.getText();
            String autor = txtAutor.getText();
            String idioma = txtIdioma.getText();
            lblMensaje.setText("");
            if ((titulo == null || titulo.trim().isEmpty()) &&
                (autor == null || autor.trim().isEmpty()) &&
                (idioma == null || idioma.trim().isEmpty())) {
                lblMensaje.setText("No ingresó ningún parámetro de búsqueda");
                tablaResultados.setItems(FXCollections.observableArrayList());
                return;
            }
            List<Libro> resultados = controller.buscarLibros(titulo, autor, idioma);
            if (resultados == null || resultados.isEmpty()) {
                lblMensaje.setText("No se encontró ningún resultado");
            } else {
                lblMensaje.setText("");
            }
            ObservableList<Libro> data = FXCollections.observableArrayList(resultados);
            tablaResultados.setItems(data);
        });

        btnVolver.setOnAction(e -> {
            MenuPrincipalView menu = new MenuPrincipalView();
            menu.mostrar(stage);
        });

        GridPane form = new GridPane();
        form.setAlignment(Pos.CENTER);
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(20));
        form.add(lblTitulo, 0, 0);
        form.add(txtTitulo, 1, 0);
        form.add(lblAutor, 0, 1);
        form.add(txtAutor, 1, 1);
        form.add(lblIdioma, 0, 2);
        form.add(txtIdioma, 1, 2);
        HBox botones = new HBox(10, btnBuscar, btnVolver);
        botones.setAlignment(Pos.CENTER);
        VBox vbox = new VBox(20, form, botones, lblMensaje, tablaResultados);
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setPadding(new Insets(20));

        Scene scene = new Scene(vbox, 700, 450);
        stage.setTitle("Búsqueda avanzada de libros");
        stage.setScene(scene);
        stage.show();
    }
}

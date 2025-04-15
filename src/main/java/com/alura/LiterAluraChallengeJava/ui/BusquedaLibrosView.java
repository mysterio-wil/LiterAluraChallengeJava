package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.model.Libro;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BusquedaLibrosView {
    public void mostrar(Stage stage) {
        Label lblTitulo = new Label("Título:");
        TextField txtTitulo = new TextField();
        Label lblAutor = new Label("Autor:");
        TextField txtAutor = new TextField();
        Label lblIdioma = new Label("Idioma:");
        TextField txtIdioma = new TextField();
        Button btnBuscar = new Button("Buscar");

        TableView<Libro> tablaResultados = new TableView<>(); // Preparado para mostrar libros

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
        form.add(btnBuscar, 1, 3);

        VBox root = new VBox(20, form, tablaResultados);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Búsqueda avanzada de libros");
        stage.setScene(scene);
        stage.show();
    }
}

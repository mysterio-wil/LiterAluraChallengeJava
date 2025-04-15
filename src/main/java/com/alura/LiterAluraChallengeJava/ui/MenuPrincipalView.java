package com.alura.LiterAluraChallengeJava.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuPrincipalView {
    public void mostrar(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Button btnBuscar = new Button("Buscar libros");
        Button btnFavoritos = new Button("Favoritos");
        Button btnExportar = new Button("Exportar datos");
        Button btnSalir = new Button("Salir");

        btnBuscar.setOnAction(e -> {
            BusquedaLibrosView busquedaView = new BusquedaLibrosView();
            busquedaView.mostrar(stage);
        });
        btnFavoritos.setOnAction(e -> System.out.println("Favoritos (pendiente)"));
        btnExportar.setOnAction(e -> System.out.println("Exportar datos (pendiente)"));
        btnSalir.setOnAction(e -> stage.close());

        root.getChildren().addAll(btnBuscar, btnFavoritos, btnExportar, btnSalir);

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("LiterAlura - Menú Principal");
        stage.setScene(scene);
        stage.show();
    }
}

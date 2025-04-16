package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.SpringContextProvider;
import org.springframework.stereotype.Component;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

@Component
public class MenuPrincipalView {
    public void mostrar(Stage stage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        Button btnBuscar = new Button("Buscar libros");
        Button btnImportarGutendex = new Button("Importar libros desde Gutendex");
        Button btnFavoritos = new Button("Favoritos");
        Button btnExportar = new Button("Exportar datos");
        Button btnSalir = new Button("Salir");

        btnBuscar.setOnAction(e -> {
            BusquedaLibrosView busquedaView = SpringContextProvider.getBean(BusquedaLibrosView.class);
            busquedaView.mostrar(stage);
        });
        btnImportarGutendex.setOnAction(e -> {
            BuscarGutendexView buscarGutendexView = SpringContextProvider.getBean(BuscarGutendexView.class);
            buscarGutendexView.mostrar(stage);
        });
        btnFavoritos.setOnAction(e -> System.out.println("Favoritos (pendiente)"));
        btnExportar.setOnAction(e -> System.out.println("Exportar datos (pendiente)"));
        btnSalir.setOnAction(e -> {
            stage.close();
            javafx.application.Platform.exit();
            System.exit(0);
        });

        root.getChildren().addAll(btnBuscar, btnImportarGutendex, btnFavoritos, btnExportar, btnSalir);

        Scene scene = new Scene(root, 400, 320);
        stage.setTitle("LiterAlura - Menú Principal");
        stage.setScene(scene);
        stage.show();
    }
}

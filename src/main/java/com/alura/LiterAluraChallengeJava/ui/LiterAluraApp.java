package com.alura.LiterAluraChallengeJava.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LiterAluraApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("LiterAlura - Interfaz Gráfica");
        Label label = new Label("¡Bienvenido a LiterAlura con JavaFX!");
        Scene scene = new Scene(label, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

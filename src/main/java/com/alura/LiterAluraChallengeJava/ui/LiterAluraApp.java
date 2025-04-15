package com.alura.LiterAluraChallengeJava.ui;

import javafx.application.Application;
import javafx.stage.Stage;

public class LiterAluraApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        MenuPrincipalView menu = new MenuPrincipalView();
        menu.mostrar(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

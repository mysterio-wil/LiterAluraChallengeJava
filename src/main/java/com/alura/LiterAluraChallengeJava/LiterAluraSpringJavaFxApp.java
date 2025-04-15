package com.alura.LiterAluraChallengeJava;

import com.alura.LiterAluraChallengeJava.ui.MenuPrincipalView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class LiterAluraSpringJavaFxApp extends Application {
    private ConfigurableApplicationContext context;

    @Override
    public void init() {
        // Obtiene el contexto del launcher
        context = LiterAluraAppLauncher.springContext;
    }

    @Override
    public void start(Stage primaryStage) {
        MenuPrincipalView menu = context.getBean(MenuPrincipalView.class);
        menu.mostrar(primaryStage);
    }
}

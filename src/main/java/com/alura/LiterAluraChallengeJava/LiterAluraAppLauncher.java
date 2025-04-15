package com.alura.LiterAluraChallengeJava;

import javafx.application.Application;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class LiterAluraAppLauncher {
    public static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        springContext = new SpringApplicationBuilder(LiterAluraChallengeJavaApplication.class).run(args);
        Application.launch(LiterAluraSpringJavaFxApp.class, args);
    }
}

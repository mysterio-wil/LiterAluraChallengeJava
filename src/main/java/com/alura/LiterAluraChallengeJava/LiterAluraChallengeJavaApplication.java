package com.alura.LiterAluraChallengeJava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class LiterAluraChallengeJavaApplication {
    private static ConfigurableApplicationContext appContext;

    public static void main(String[] args) {
        appContext = SpringApplication.run(LiterAluraChallengeJavaApplication.class, args);
    }

    public static ConfigurableApplicationContext getAppContext() {
        return appContext;
    }
}

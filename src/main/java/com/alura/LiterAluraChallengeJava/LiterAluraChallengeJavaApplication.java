package com.alura.LiterAluraChallengeJava;

import com.alura.LiterAluraChallengeJava.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * Implementa CommandLineRunner para ejecutar la lógica principal al iniciar
 */
@SpringBootApplication
public class LiterAluraChallengeJavaApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		principal.muestraElMenu();
	}
}

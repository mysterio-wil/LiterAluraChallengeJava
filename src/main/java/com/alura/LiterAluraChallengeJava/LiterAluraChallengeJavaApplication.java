package com.alura.LiterAluraChallengeJava;

import com.alura.LiterAluraChallengeJava.principal.Principal;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de la aplicación Spring Boot
 * Implementa CommandLineRunner para ejecutar la lógica principal al iniciar
 */
@SpringBootApplication
public class LiterAluraChallengeJavaApplication implements CommandLineRunner {

	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private LibroRepository libroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeJavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(autorRepository, libroRepository);
		principal.muestraElMenu();
	}
}

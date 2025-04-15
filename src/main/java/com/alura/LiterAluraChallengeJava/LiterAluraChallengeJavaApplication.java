package com.alura.LiterAluraChallengeJava;

import com.alura.LiterAluraChallengeJava.principal.Principal;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import com.alura.LiterAluraChallengeJava.repository.FavoritoLibroRepository;
import com.alura.LiterAluraChallengeJava.service.EstadisticasService;
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

	@Autowired
	private EstadisticasService estadisticasService;

	@Autowired
	private FavoritoLibroRepository favoritoLibroRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraChallengeJavaApplication.class, args);
	}

	@Override
	public void run(String... args) {
		Principal principal = new Principal(autorRepository, libroRepository, estadisticasService, favoritoLibroRepository);
		principal.muestraElMenu();
	}
}

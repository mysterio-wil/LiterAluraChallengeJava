package com.alura.LiterAluraChallengeJava.ui;

import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BusquedaLibrosController {
    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> buscarLibros(String titulo, String autor, String idioma) {
        if ((titulo == null || titulo.isEmpty()) &&
            (autor == null || autor.isEmpty()) &&
            (idioma == null || idioma.isEmpty())) {
            return libroRepository.findAll();
        }
        return libroRepository.busquedaAvanzada(
            (titulo == null || titulo.isEmpty()) ? null : titulo,
            (autor == null || autor.isEmpty()) ? null : autor,
            (idioma == null || idioma.isEmpty()) ? null : idioma
        );
    }
}

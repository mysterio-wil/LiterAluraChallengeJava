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
        String t = (titulo == null || titulo.trim().isEmpty()) ? null : titulo.trim();
        String a = (autor == null || autor.trim().isEmpty()) ? null : autor.trim();
        String i = (idioma == null || idioma.trim().isEmpty()) ? null : idioma.trim();
        List<Libro> resultados;
        if (t == null && a == null && i == null) {
            resultados = libroRepository.findAll();
        } else {
            resultados = libroRepository.busquedaAvanzada(t, a, i);
        }
        return resultados;
    }
}

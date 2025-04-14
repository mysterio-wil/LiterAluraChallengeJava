package com.alura.LiterAluraChallengeJava.service;

import com.alura.LiterAluraChallengeJava.model.Autor;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public Optional<Autor> buscarAutorPorNombre(String nombre) {
        return autorRepository.findByNombre(nombre);
    }

    public List<Autor> listarAutores() {
        return autorRepository.findAll();
    }

    public List<Autor> buscarAutoresVivosEnAnio(String anioStr) {
        try {
            Integer anio = Integer.parseInt(anioStr);
            if (anio < 0) {
                throw new IllegalArgumentException("El año no puede ser negativo");
            }
            return autorRepository.findAutoresVivosEnAnio(anio);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("El año debe ser un número válido");
        }
    }
} 
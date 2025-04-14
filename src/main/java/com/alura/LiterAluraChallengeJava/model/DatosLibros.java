package com.alura.LiterAluraChallengeJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Clase que representa los datos de un libro en la respuesta JSON
 * Incluye información sobre el título, autores, idiomas y número de descargas
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosLibros {
    @JsonAlias("title")
    private String titulo;
    
    @JsonAlias("authors")
    private List<DatosAutor> autores;
    
    @JsonAlias("languages")
    private List<String> idiomas;
    
    @JsonAlias("download_count")
    private Integer numeroDescargas;

    // Constructor vacío necesario para Jackson
    public DatosLibros() {}

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<DatosAutor> getAutores() {
        return autores;
    }

    public void setAutores(List<DatosAutor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", idiomas=" + idiomas +
                ", numeroDescargas=" + numeroDescargas +
                '}';
    }
} 
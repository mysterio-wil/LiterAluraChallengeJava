package com.alura.LiterAluraChallengeJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Clase que representa la respuesta general de la API
 * Contiene el total de resultados y la lista de libros encontrados
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Datos {
    @JsonAlias("count")
    private Integer total;
    
    @JsonAlias("results")
    private List<DatosLibros> libros;

    // Constructor vacío necesario para Jackson
    public Datos() {}

    // Getters y Setters
    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<DatosLibros> getLibros() {
        return libros;
    }

    public void setLibros(List<DatosLibros> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "total=" + total +
                ", libros=" + libros +
                '}';
    }
} 
package com.alura.LiterAluraChallengeJava.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que representa los datos de un autor en la respuesta JSON
 * Utiliza anotaciones de Jackson para mapear los campos del JSON
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosAutor {
    @JsonAlias("name")
    private String nombre;
    
    @JsonAlias("birth_year")
    private Integer fechaNacimiento;
    
    @JsonAlias("death_year")
    private Integer fechaFallecimiento;

    // Constructor vacío necesario para Jackson
    public DatosAutor() {}

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaFallecimiento=" + fechaFallecimiento +
                '}';
    }
} 
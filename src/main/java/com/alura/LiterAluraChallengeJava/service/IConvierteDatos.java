package com.alura.LiterAluraChallengeJava.service;

import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * Interfaz que define el contrato para convertir datos JSON a objetos Java
 * Utiliza genéricos para permitir la conversión a cualquier tipo de objeto
 */
public interface IConvierteDatos {
    /**
     * Convierte una cadena JSON a un objeto del tipo especificado
     * @param json Cadena JSON a convertir
     * @param clase Clase del objeto al que se convertirá el JSON
     * @return Objeto del tipo especificado
     * @throws JsonProcessingException Si hay un error al procesar el JSON
     */
    <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException;
} 
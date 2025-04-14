package com.alura.LiterAluraChallengeJava.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implementación de la interfaz IConvierteDatos
 * Utiliza la biblioteca Jackson para realizar la conversión de JSON a objetos Java
 */
public class ConvierteDatos implements IConvierteDatos {
    // Instancia de ObjectMapper para realizar la conversión de JSON
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * Implementación del método para convertir JSON a objetos Java
     * @param json Cadena JSON a convertir
     * @param clase Clase del objeto al que se convertirá el JSON
     * @return Objeto del tipo especificado
     * @throws JsonProcessingException Si hay un error al procesar el JSON
     */
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) throws JsonProcessingException {
        return mapper.readValue(json, clase);
    }
} 
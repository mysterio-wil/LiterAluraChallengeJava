package com.alura.LiterAluraChallengeJava.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Clase encargada de realizar las peticiones HTTP a la API de Gutendex
 * Utiliza HttpClient de Java para realizar las solicitudes
 */
public class ConsumoAPI {
    // URL base de la API de Gutendex
    private final String URL_BASE = "https://gutendex.com/books/";

    /**
     * Realiza una petición HTTP GET a la API de Gutendex
     * @param endpoint Ruta específica del endpoint a consultar
     * @return Respuesta de la API en formato String (JSON)
     * @throws RuntimeException Si ocurre un error durante la petición
     */
    public String obtenerDatos(String endpoint) {
        // Crear un nuevo cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        
        // Construir la petición HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + endpoint))
                .build();
        
        try {
            // Enviar la petición y obtener la respuesta
            HttpResponse<String> response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            // Lanzar una excepción con un mensaje descriptivo
            throw new RuntimeException("Error al consumir la API: " + e.getMessage());
        }
    }
} 
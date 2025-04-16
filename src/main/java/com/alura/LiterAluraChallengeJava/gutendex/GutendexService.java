package com.alura.LiterAluraChallengeJava.gutendex;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class GutendexService {
    private static final String API_URL = "https://gutendex.com/books";

    // Solo un campo de búsqueda (título o autor) y el idioma
    public List<LibroGutendexDTO> buscarLibros(String busqueda, String idioma) {
        RestTemplate restTemplate = new RestTemplate();
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL);
        if (busqueda != null && !busqueda.trim().isEmpty()) {
            builder.queryParam("search", busqueda.trim());
        }
        if (idioma != null && !idioma.trim().isEmpty()) {
            builder.queryParam("languages", idioma.trim());
        }
        String url = builder.toUriString();
        Map response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<LibroGutendexDTO> libros = new ArrayList<>();
        if (results != null) {
            for (Map<String, Object> item : results) {
                String title = (String) item.getOrDefault("title", "");
                List<Map<String, Object>> authorsList = (List<Map<String, Object>>) item.get("authors");
                String autores = "";
                if (authorsList != null && !authorsList.isEmpty()) {
                    List<String> nombres = new ArrayList<>();
                    for (Map<String, Object> autorMap : authorsList) {
                        nombres.add((String) autorMap.getOrDefault("name", ""));
                    }
                    autores = String.join(", ", nombres);
                }
                List<String> idiomasList = (List<String>) item.get("languages");
                String idiomaStr = (idiomasList != null && !idiomasList.isEmpty()) ? idiomasList.get(0) : "";
                int descargas = item.get("download_count") != null ? ((Number) item.get("download_count")).intValue() : 0;
                libros.add(new LibroGutendexDTO(title, autores, idiomaStr, descargas));
            }
        }
        return libros;
    }
}

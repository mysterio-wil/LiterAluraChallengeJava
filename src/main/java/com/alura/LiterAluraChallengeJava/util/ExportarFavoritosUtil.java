package com.alura.LiterAluraChallengeJava.util;

import com.alura.LiterAluraChallengeJava.model.FavoritoLibro;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportarFavoritosUtil {
    public static void exportarCSV(List<FavoritoLibro> favoritos, File archivo) throws IOException {
        try (FileWriter writer = new FileWriter(archivo)) {
            writer.write("Título,Autor,Idioma,Descargas\n");
            for (FavoritoLibro fav : favoritos) {
                var libro = fav.getLibro();
                writer.write(String.format("\"%s\",\"%s\",%s,%s\n",
                        libro.getTitulo() != null ? libro.getTitulo().replace("\"", "\"\"") : "N/A",
                        (libro.getAutor() != null && libro.getAutor().getNombre() != null) ? libro.getAutor().getNombre().replace("\"", "\"\"") : "N/A",
                        libro.getIdioma() != null ? libro.getIdioma() : "N/A",
                        libro.getNumeroDescargas() != null ? libro.getNumeroDescargas() : ""));
            }
        }
    }
}

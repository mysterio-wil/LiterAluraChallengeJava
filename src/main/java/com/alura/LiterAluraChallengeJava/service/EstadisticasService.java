package com.alura.LiterAluraChallengeJava.service;

import com.alura.LiterAluraChallengeJava.model.Autor;
import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EstadisticasService {
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public EstadisticasService(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    // Estadísticas de Libros
    public long obtenerTotalLibros() {
        return libroRepository.count();
    }

    public Map<String, Long> obtenerDistribucionPorIdioma() {
        return libroRepository.findAll().stream()
                .collect(Collectors.groupingBy(Libro::getIdioma, Collectors.counting()));
    }

    public DoubleSummaryStatistics obtenerEstadisticasDescargas() {
        return libroRepository.findAll().stream()
                .mapToDouble(libro -> libro.getNumeroDescargas().doubleValue())
                .summaryStatistics();
    }

    // Estadísticas de Autores
    public long obtenerTotalAutores() {
        return autorRepository.count();
    }

    public double obtenerPromedioLibrosPorAutor() {
        long totalAutores = obtenerTotalAutores();
        if (totalAutores == 0) return 0;
        return (double) obtenerTotalLibros() / totalAutores;
    }

    public DoubleSummaryStatistics obtenerEstadisticasEdadAutores() {
        return autorRepository.findAll().stream()
                .filter(autor -> autor.getFechaNacimiento() != null && autor.getFechaFallecimiento() != null)
                .mapToDouble(autor -> autor.getFechaFallecimiento() - autor.getFechaNacimiento())
                .summaryStatistics();
    }

    // Estadísticas Combinadas
    public DoubleSummaryStatistics obtenerEstadisticasDescargasPorAutor() {
        return autorRepository.findAll().stream()
                .mapToDouble(autor -> autor.getLibros().stream()
                        .mapToDouble(libro -> libro.getNumeroDescargas().doubleValue())
                        .sum())
                .summaryStatistics();
    }

    public Map<String, Long> obtenerDistribucionLibrosPorSiglo() {
        return autorRepository.findAll().stream()
                .filter(autor -> autor.getFechaNacimiento() != null)
                .collect(Collectors.groupingBy(
                        autor -> String.format("Siglo %d", ((autor.getFechaNacimiento() / 100) + 1)),
                        Collectors.counting()));
    }

    public String formatearEstadisticas(DoubleSummaryStatistics stats) {
        return String.format("""
                Estadísticas:
                - Total: %d elementos
                - Promedio: %.2f
                - Mínimo: %.2f
                - Máximo: %.2f
                - Suma total: %.2f
                """,
                stats.getCount(),
                stats.getAverage(),
                stats.getMin(),
                stats.getMax(),
                stats.getSum());
    }
}

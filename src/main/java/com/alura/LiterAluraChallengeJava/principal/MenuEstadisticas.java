package com.alura.LiterAluraChallengeJava.principal;

import com.alura.LiterAluraChallengeJava.service.EstadisticasService;

import java.util.Map;
import java.util.Scanner;

public class MenuEstadisticas {
    private final Scanner scanner;
    private final EstadisticasService estadisticasService;

    public MenuEstadisticas(Scanner scanner, EstadisticasService estadisticasService) {
        this.scanner = scanner;
        this.estadisticasService = estadisticasService;
    }

    public void mostrarMenuPrincipal() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    
                    === MENÚ DE ESTADÍSTICAS ===
                    1 - Estadísticas de Libros
                    2 - Estadísticas de Autores
                    3 - Estadísticas Combinadas
                    
                    0 - Volver al menú principal
                    """);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> mostrarEstadisticasLibros();
                    case 2 -> mostrarEstadisticasAutores();
                    case 3 -> mostrarEstadisticasCombinadas();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
    }

    private void mostrarEstadisticasLibros() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    
                    === ESTADÍSTICAS DE LIBROS ===
                    1 - Total de libros
                    2 - Distribución por idioma
                    3 - Estadísticas de descargas
                    
                    0 - Volver
                    """);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> System.out.printf("Total de libros en la base de datos: %d%n",
                            estadisticasService.obtenerTotalLibros());
                    case 2 -> mostrarDistribucionPorIdioma();
                    case 3 -> System.out.println(estadisticasService.formatearEstadisticas(
                            estadisticasService.obtenerEstadisticasDescargas()));
                    case 0 -> System.out.println("Volviendo al menú de estadísticas...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
    }

    private void mostrarEstadisticasAutores() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    
                    === ESTADÍSTICAS DE AUTORES ===
                    1 - Total de autores
                    2 - Promedio de libros por autor
                    3 - Estadísticas de edad de autores
                    
                    0 - Volver
                    """);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> System.out.printf("Total de autores en la base de datos: %d%n",
                            estadisticasService.obtenerTotalAutores());
                    case 2 -> System.out.printf("Promedio de libros por autor: %.2f%n",
                            estadisticasService.obtenerPromedioLibrosPorAutor());
                    case 3 -> System.out.println(estadisticasService.formatearEstadisticas(
                            estadisticasService.obtenerEstadisticasEdadAutores()));
                    case 0 -> System.out.println("Volviendo al menú de estadísticas...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
    }

    private void mostrarEstadisticasCombinadas() {
        var opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    
                    === ESTADÍSTICAS COMBINADAS ===
                    1 - Estadísticas de descargas por autor
                    2 - Distribución de libros por siglo
                    3 - Top 10 Libros Más Descargados
                    
                    0 - Volver
                    """);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1 -> System.out.println(estadisticasService.formatearEstadisticas(
                            estadisticasService.obtenerEstadisticasDescargasPorAutor()));
                    case 2 -> mostrarDistribucionPorSiglo();
                    case 3 -> mostrarTop10LibrosMasDescargados();
                    case 0 -> System.out.println("Volviendo al menú de estadísticas...");
                    default -> System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            }
        }
    }

    public void mostrarTop10LibrosMasDescargados() {
        System.out.println("\nTop 10 Libros Más Descargados:");
        System.out.println("--------------------------------");

        var top10 = estadisticasService.obtenerTop10LibrosMasDescargados();
        if (top10.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        for (int i = 0; i < top10.size(); i++) {
            var libro = top10.get(i);
            System.out.printf("%d. %s - Descargas: %d%n",
                    i + 1,
                    libro.getTitulo(),
                    libro.getNumeroDescargas());
        }
        System.out.println();
    }

    private void mostrarDistribucionPorIdioma() {
        System.out.println("\nDistribución de libros por idioma:");
        Map<String, Long> distribucion = estadisticasService.obtenerDistribucionPorIdioma();
        distribucion.forEach((idioma, cantidad) ->
                System.out.printf("- %s: %d libros%n", idioma, cantidad));
    }

    private void mostrarDistribucionPorSiglo() {
        System.out.println("\nDistribución de libros por siglo:");
        Map<String, Long> distribucion = estadisticasService.obtenerDistribucionLibrosPorSiglo();
        distribucion.forEach((siglo, cantidad) ->
                System.out.printf("- %s: %d libros%n", siglo, cantidad));
    }
}

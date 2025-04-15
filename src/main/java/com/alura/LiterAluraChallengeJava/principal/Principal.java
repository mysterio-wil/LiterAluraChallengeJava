package com.alura.LiterAluraChallengeJava.principal;

import com.alura.LiterAluraChallengeJava.model.Autor;
import com.alura.LiterAluraChallengeJava.model.Datos;
import com.alura.LiterAluraChallengeJava.model.DatosAutor;
import com.alura.LiterAluraChallengeJava.model.DatosLibros;
import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import com.alura.LiterAluraChallengeJava.service.ConsumoAPI;
import com.alura.LiterAluraChallengeJava.service.ConvierteDatos;
import com.alura.LiterAluraChallengeJava.service.EstadisticasService;
import com.alura.LiterAluraChallengeJava.service.IConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final IConvierteDatos conversor = new ConvierteDatos();
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final MenuEstadisticas menuEstadisticas;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository, EstadisticasService estadisticasService) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.menuEstadisticas = new MenuEstadisticas(teclado, estadisticasService);
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    \n
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    6 - Mostrar estadísticas
                    7 - Top 10 libros más descargados
                    8 - Buscar autor por nombre
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            try {
                opcion = Integer.parseInt(teclado.nextLine());
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:
                        listarLibrosRegistrados();
                        break;
                    case 3:
                        listarAutoresRegistrados();
                        break;
                    case 4:
                        listarAutoresVivosEnAnio();
                        break;
                    case 5:
                        listarLibrosPorIdioma();
                        break;
                    case 6:
                        menuEstadisticas.mostrarMenuPrincipal();
                        break;
                    case 7:
                        menuEstadisticas.mostrarTop10LibrosMasDescargados();
                        break;
                    case 8:
                        buscarAutorPorNombre();
                        break;
                    case 0:
                        System.out.println("Cerrando la aplicación...");
                        teclado.close();
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido");
            } catch (Exception e) {
                System.out.println("Ocurrió un error: " + e.getMessage());
            }
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        var tituloLibro = teclado.nextLine().trim();

        // Primera verificación: buscar libros similares
        List<Libro> librosSimilares = libroRepository.findByTituloSimilar(tituloLibro);
        if (!librosSimilares.isEmpty()) {
            System.out.println("\nSe encontraron libros similares en la base de datos:");
            librosSimilares.forEach(libro -> {
                System.out.println("\nTítulo: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("-------------------");
            });

            System.out.println("\n¿Desea buscar otro libro? (s/n)");
            String respuesta = teclado.nextLine().toLowerCase();
            if (respuesta.equals("s")) {
                buscarLibroPorTitulo();
            }
            return;
        }

        System.out.println("\nBuscando libro en la API de Gutendex...");
        var json = consumoAPI.obtenerDatos("?search=" + tituloLibro.replace(" ", "+"));
        try {
            Datos datos = conversor.obtenerDatos(json, Datos.class);
            if (datos.getLibros().isEmpty()) {
                System.out.println("No se encontraron libros con ese título.");
                System.out.println("\n¿Desea intentar con otro título? (s/n)");
                String respuesta = teclado.nextLine().toLowerCase();
                if (respuesta.equals("s")) {
                    buscarLibroPorTitulo();
                }
            } else {
                DatosLibros libroBuscado = datos.getLibros().getFirst();
                DatosAutor autorLibro = libroBuscado.getAutores().getFirst();

                // Verificación final: buscar por título y autor exactos
                Optional<Libro> libroExistente = libroRepository.findByTituloYAutor(
                        libroBuscado.getTitulo(),
                        autorLibro.getNombre()
                );

                if (libroExistente.isPresent()) {
                    System.out.println("\nEl libro ya existe en la base de datos:");
                    Libro libro = libroExistente.get();
                    System.out.println("Título: " + libro.getTitulo());
                    System.out.println("Autor: " + libro.getAutor().getNombre());
                    System.out.println("Idioma: " + libro.getIdioma());
                    System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                    return;
                }

                // Buscar si el autor ya existe
                Optional<Autor> autorOptional = autorRepository.findByNombre(autorLibro.getNombre());
                Autor autor;

                if (autorOptional.isPresent()) {
                    autor = autorOptional.get();
                    System.out.println("\nAutor encontrado en la base de datos.");
                } else {
                    autor = new Autor(
                            autorLibro.getNombre(),
                            autorLibro.getFechaNacimiento(),
                            autorLibro.getFechaFallecimiento()
                    );
                    autorRepository.save(autor);
                    System.out.println("\nNuevo autor registrado.");
                }

                // Crear y guardar el libro
                Libro libro = new Libro(
                        libroBuscado.getTitulo(),
                        libroBuscado.getIdiomas().getFirst(),
                        libroBuscado.getNumeroDescargas(),
                        autor
                );
                libroRepository.save(libro);

                System.out.println("\nLibro guardado con éxito:");
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());

                System.out.println("\n¿Desea buscar otro libro? (s/n)");
                String respuesta = teclado.nextLine().toLowerCase();
                if (respuesta.equals("s")) {
                    buscarLibroPorTitulo();
                }
            }
        } catch (Exception e) {
            System.out.println("Error al procesar los datos: " + e.getMessage());
            System.out.println("\n¿Desea intentar de nuevo? (s/n)");
            String respuesta = teclado.nextLine().toLowerCase();
            if (respuesta.equals("s")) {
                buscarLibroPorTitulo();
            }
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
        } else {
            System.out.println("\nLibros registrados:");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("-------------------");
            });
        }
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados.");
        } else {
            System.out.println("\nAutores registrados:");
            autores.forEach(autor -> {
                System.out.println("Nombre: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                System.out.println("-------------------");
            });
        }
    }

    private void listarAutoresVivosEnAnio() {
        System.out.println("Ingrese el año para buscar autores vivos:");
        try {
            Integer anio = Integer.parseInt(teclado.nextLine());
            List<Autor> autores = autorRepository.findAutoresVivosEnAnio(anio);
            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año " + anio);
            } else {
                System.out.println("\nAutores vivos en el año " + anio + ":");
                autores.forEach(autor -> {
                    System.out.println("Nombre: " + autor.getNombre());
                    System.out.println("Fecha de nacimiento: " + autor.getFechaNacimiento());
                    System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimiento());
                    System.out.println("-------------------");
                });
            }
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese un año válido");
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println("Ingrese el idioma para buscar libros (ej: es, en, fr, pt):");
        String idioma = teclado.nextLine();
        List<Libro> libros = libroRepository.findLibrosPorIdioma(idioma);
        if (libros.isEmpty()) {
            System.out.println("No se encontraron libros en el idioma " + idioma);
        } else {
            System.out.println("\nLibros en " + idioma + ":");
            libros.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("-------------------");
            });
        }
    }

    private void buscarAutorPorNombre() {
        System.out.println("\nIngrese el nombre del autor que desea buscar:");
        var nombreAutor = teclado.nextLine().trim();

        var autoresEncontrados = autorRepository.findByNombreSimilar(nombreAutor);
        if (autoresEncontrados.isEmpty()) {
            System.out.println("No se encontraron autores con ese nombre.");
            return;
        }

        System.out.println("\nAutores encontrados:");
        System.out.println("-------------------");
        autoresEncontrados.forEach(autor -> {
            System.out.println("\nNombre: " + autor.getNombre());
            System.out.println("Año de nacimiento: " +
                    (autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocido"));
            System.out.println("Año de fallecimiento: " +
                    (autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "Desconocido o aún vivo"));

            var libros = autor.getLibros();
            if (!libros.isEmpty()) {
                System.out.println("Libros:");
                libros.forEach(libro ->
                        System.out.printf("- %s (Descargas: %d)%n",
                                libro.getTitulo(),
                                libro.getNumeroDescargas()));
            } else {
                System.out.println("No hay libros registrados para este autor.");
            }
        });
        System.out.println();
    }
}
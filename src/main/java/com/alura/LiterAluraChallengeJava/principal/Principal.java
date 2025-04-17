package com.alura.LiterAluraChallengeJava.principal;

import com.alura.LiterAluraChallengeJava.model.Autor;
import com.alura.LiterAluraChallengeJava.model.Datos;
import com.alura.LiterAluraChallengeJava.model.DatosAutor;
import com.alura.LiterAluraChallengeJava.model.DatosLibros;
import com.alura.LiterAluraChallengeJava.model.FavoritoLibro;
import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.model.Usuario;
import com.alura.LiterAluraChallengeJava.repository.AutorRepository;
import com.alura.LiterAluraChallengeJava.repository.FavoritoLibroRepository;
import com.alura.LiterAluraChallengeJava.repository.LibroRepository;
import com.alura.LiterAluraChallengeJava.repository.UsuarioRepository;
import com.alura.LiterAluraChallengeJava.service.ConsumoAPI;
import com.alura.LiterAluraChallengeJava.service.ConvierteDatos;
import com.alura.LiterAluraChallengeJava.service.EstadisticasService;
import com.alura.LiterAluraChallengeJava.service.IConvierteDatos;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String DESKTOP_PATH = System.getProperty("user.home") + "/Desktop/";
    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI = new ConsumoAPI();
    private final IConvierteDatos conversor = new ConvierteDatos();
    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private final FavoritoLibroRepository favoritoLibroRepository;
    private final MenuEstadisticas menuEstadisticas;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository, EstadisticasService estadisticasService, FavoritoLibroRepository favoritoLibroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.menuEstadisticas = new MenuEstadisticas(teclado, estadisticasService);
        this.favoritoLibroRepository = favoritoLibroRepository;
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
                    9 - Listar autores nacidos en un rango de años
                    10 - Marcar o desmarcar libro como favorito
                    11 - Listar libros favoritos
                    12 - Exportar todos los libros
                    13 - Exportar libros favoritos
                    14 - Búsqueda avanzada de libros
                    
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
                    case 9:
                        listarAutoresPorRangoNacimiento();
                        break;
                    case 10:
                        marcarODesmarcarLibroFavorito();
                        break;
                    case 11:
                        listarLibrosFavoritos();
                        break;
                    case 12:
                        exportarLibros();
                        break;
                    case 13:
                        exportarFavoritos();
                        break;
                    case 14:
                        busquedaAvanzadaLibros();
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

    private void listarAutoresPorRangoNacimiento() {
        System.out.println("\nListar autores nacidos en un rango de años");
        System.out.print("Ingrese el año de inicio: ");
        Integer inicio = null;
        Integer fin = null;
        try {
            inicio = Integer.parseInt(teclado.nextLine().trim());
            System.out.print("Ingrese el año de fin: ");
            fin = Integer.parseInt(teclado.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Por favor, ingrese años válidos.");
            return;
        }
        if (inicio > fin) {
            System.out.println("El año de inicio no puede ser mayor al año de fin.");
            return;
        }
        var autores = autorRepository.findByRangoNacimiento(inicio, fin);
        if (autores.isEmpty()) {
            System.out.printf("No se encontraron autores nacidos entre %d y %d.%n", inicio, fin);
            return;
        }
        System.out.printf("\nAutores nacidos entre %d y %d:%n", inicio, fin);
        autores.forEach(autor -> {
            System.out.println("-------------------");
            System.out.printf("Nombre: %s%n", autor.getNombre());
            System.out.printf("Año de nacimiento: %s%n", autor.getFechaNacimiento() != null ? autor.getFechaNacimiento() : "Desconocido");
            System.out.printf("Año de fallecimiento: %s%n", autor.getFechaFallecimiento() != null ? autor.getFechaFallecimiento() : "Desconocido o aún vivo");
        });
        System.out.println();
    }

    private void marcarODesmarcarLibroFavorito() {
        System.out.println("\nIngrese el título del libro para marcar o desmarcar como favorito:");
        String titulo = teclado.nextLine().trim();
        Optional<Libro> libroOpt = libroRepository.findByTitulo(titulo);
        if (libroOpt.isEmpty()) {
            System.out.println("No se encontró el libro con ese título.");
            return;
        }
        Libro libro = libroOpt.get();
        UsuarioRepository usuarioRepo = com.alura.LiterAluraChallengeJava.LiterAluraChallengeJavaApplication.getAppContext().getBean(UsuarioRepository.class);
        Usuario usuarioDefault = usuarioRepo.findByNombre("default");
        if (usuarioDefault == null) {
            usuarioDefault = usuarioRepo.save(new Usuario("default"));
        }
        Optional<FavoritoLibro> favOpt = favoritoLibroRepository.findByLibro(libro);
        if (favOpt.isPresent()) {
            favoritoLibroRepository.deleteByLibro(libro);
            System.out.println("Libro desmarcado como favorito.");
        } else {
            favoritoLibroRepository.save(new FavoritoLibro(libro, usuarioDefault));
            System.out.println("Libro marcado como favorito.");
        }
    }

    private void listarLibrosFavoritos() {
        List<FavoritoLibro> favoritos = favoritoLibroRepository.findAll();
        if (favoritos.isEmpty()) {
            System.out.println("No hay libros favoritos registrados.");
            return;
        }
        System.out.println("\nLibros favoritos:");
        favoritos.forEach(fav -> {
            Libro libro = fav.getLibro();
            System.out.println("Título: " + libro.getTitulo());
            System.out.println("Autor: " + libro.getAutor().getNombre());
            System.out.println("Idioma: " + libro.getIdioma());
            System.out.println("Número de descargas: " + libro.getNumeroDescargas());
            System.out.println("-------------------");
        });
    }

    private void exportarLibros() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados.");
            return;
        }
        System.out.println("Elige el formato de exportación: 1) CSV  2) JSON");
        String formato = teclado.nextLine().trim();
        switch (formato) {
            case "1":
                exportarLibrosCSV(libros);
                break;
            case "2":
                exportarLibrosJSON(libros);
                break;
            default:
                System.out.println("Opción inválida. Exportación cancelada.");
        }
    }

    private void exportarFavoritos() {
        List<FavoritoLibro> favoritos = favoritoLibroRepository.findAll();
        if (favoritos.isEmpty()) {
            System.out.println("No hay libros favoritos registrados.");
            return;
        }
        System.out.println("Elige el formato de exportación: 1) CSV  2) JSON");
        String formato = teclado.nextLine().trim();
        switch (formato) {
            case "1":
                exportarFavoritosCSV(favoritos);
                break;
            case "2":
                exportarFavoritosJSON(favoritos);
                break;
            default:
                System.out.println("Opción inválida. Exportación cancelada.");
        }
    }

    private void busquedaAvanzadaLibros() {
        System.out.println("\n--- Búsqueda avanzada de libros ---");
        System.out.print("Título (puede ser parcial, dejar vacío para omitir): ");
        String titulo = teclado.nextLine().trim();
        System.out.print("Autor (dejar vacío para omitir): ");
        String autor = teclado.nextLine().trim();
        System.out.print("Idioma (dejar vacío para omitir): ");
        String idioma = teclado.nextLine().trim();

        List<Libro> resultados = libroRepository.busquedaAvanzada(
            titulo.isEmpty() ? null : titulo,
            autor.isEmpty() ? null : autor,
            idioma.isEmpty() ? null : idioma
        );
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron libros con los criterios proporcionados.");
        } else {
            System.out.println("\nResultados:");
            resultados.forEach(libro -> {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getAutor().getNombre());
                System.out.println("Idioma: " + libro.getIdioma());
                System.out.println("Número de descargas: " + libro.getNumeroDescargas());
                System.out.println("-------------------");
            });
        }
    }

    private void exportarLibrosCSV(List<Libro> libros) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DESKTOP_PATH + "libros.csv"))) {
            writer.println("Titulo,Autor,Idioma,NumeroDescargas");
            for (Libro libro : libros) {
                writer.printf("\"%s\",\"%s\",%s,%d\n",
                        libro.getTitulo().replace("\"", "'"),
                        libro.getAutor().getNombre().replace("\"", "'"),
                        libro.getIdioma(),
                        libro.getNumeroDescargas()
                );
            }
            System.out.println("Libros exportados en formato CSV a " + DESKTOP_PATH + "libros.csv");
        } catch (IOException e) {
            System.out.println("Error al exportar libros en CSV: " + e.getMessage());
        }
    }

    private void exportarLibrosJSON(List<Libro> libros) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DESKTOP_PATH + "libros.json"))) {
            writer.println("[");
            for (int i = 0; i < libros.size(); i++) {
                Libro libro = libros.get(i);
                writer.printf("  {\"titulo\": \"%s\", \"autor\": \"%s\", \"idioma\": \"%s\", \"numeroDescargas\": %d}%s\n",
                        libro.getTitulo().replace("\"", "'"),
                        libro.getAutor().getNombre().replace("\"", "'"),
                        libro.getIdioma(),
                        libro.getNumeroDescargas(),
                        (i < libros.size() - 1) ? "," : ""
                );
            }
            writer.println("]");
            System.out.println("Libros exportados en formato JSON a " + DESKTOP_PATH + "libros.json");
        } catch (IOException e) {
            System.out.println("Error al exportar libros en JSON: " + e.getMessage());
        }
    }

    private void exportarFavoritosCSV(List<FavoritoLibro> favoritos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DESKTOP_PATH + "favoritos.csv"))) {
            writer.println("Titulo,Autor,Idioma,NumeroDescargas");
            for (FavoritoLibro fav : favoritos) {
                Libro libro = fav.getLibro();
                writer.printf("\"%s\",\"%s\",%s,%d\n",
                        libro.getTitulo().replace("\"", "'"),
                        libro.getAutor().getNombre().replace("\"", "'"),
                        libro.getIdioma(),
                        libro.getNumeroDescargas()
                );
            }
            System.out.println("Favoritos exportados en formato CSV a " + DESKTOP_PATH + "favoritos.csv");
        } catch (IOException e) {
            System.out.println("Error al exportar favoritos en CSV: " + e.getMessage());
        }
    }

    private void exportarFavoritosJSON(List<FavoritoLibro> favoritos) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DESKTOP_PATH + "favoritos.json"))) {
            writer.println("[");
            for (int i = 0; i < favoritos.size(); i++) {
                Libro libro = favoritos.get(i).getLibro();
                writer.printf("  {\"titulo\": \"%s\", \"autor\": \"%s\", \"idioma\": \"%s\", \"numeroDescargas\": %d}%s\n",
                        libro.getTitulo().replace("\"", "'"),
                        libro.getAutor().getNombre().replace("\"", "'"),
                        libro.getIdioma(),
                        libro.getNumeroDescargas(),
                        (i < favoritos.size() - 1) ? "," : ""
                );
            }
            writer.println("]");
            System.out.println("Favoritos exportados en formato JSON a " + DESKTOP_PATH + "favoritos.json");
        } catch (IOException e) {
            System.out.println("Error al exportar favoritos en JSON: " + e.getMessage());
        }
    }
}
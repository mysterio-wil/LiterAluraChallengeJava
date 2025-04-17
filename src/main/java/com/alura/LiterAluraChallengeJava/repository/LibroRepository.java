package com.alura.LiterAluraChallengeJava.repository;

import com.alura.LiterAluraChallengeJava.model.Libro;
import com.alura.LiterAluraChallengeJava.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> findLibrosPorIdioma(String idioma);

    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idioma = :idioma")
    Long countByIdioma(String idioma);

    @Query("SELECT l FROM Libro l WHERE LOWER(TRIM(l.titulo)) = LOWER(TRIM(:titulo))")
    Optional<Libro> findByTituloExacto(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE LOWER(TRIM(l.titulo)) LIKE LOWER(CONCAT('%', TRIM(:titulo), '%'))")
    List<Libro> findByTituloSimilar(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE LOWER(TRIM(l.titulo)) = LOWER(TRIM(:titulo)) AND l.autor.nombre = :autor")
    Optional<Libro> findByTituloYAutor(@Param("titulo") String titulo, @Param("autor") String autor);

    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Libro l WHERE " +
           "(:titulo IS NULL OR LOWER(TRIM(l.titulo)) LIKE LOWER(CONCAT('%', TRIM(:titulo), '%'))) AND " +
           "(:autor IS NULL OR LOWER(TRIM(l.autor.nombre)) LIKE LOWER(CONCAT('%', TRIM(:autor), '%'))) AND " +
           "(:idioma IS NULL OR LOWER(l.idioma) = LOWER(:idioma))")
    List<Libro> busquedaAvanzada(@Param("titulo") String titulo, @Param("autor") String autor, @Param("idioma") String idioma);

    @Query("SELECT l FROM Libro l WHERE LOWER(TRIM(l.titulo)) = LOWER(TRIM(:titulo)) AND l.autor.nombre = :autor AND LOWER(TRIM(l.idioma)) = LOWER(TRIM(:idioma))")
    Optional<Libro> findByTituloAutorYIdioma(@Param("titulo") String titulo, @Param("autor") String autor, @Param("idioma") String idioma);

    // Método para evitar duplicados solo por título, autor e idioma
    Optional<Libro> findByTituloAndAutorNombreAndIdioma(String titulo, String autorNombre, String idioma);

    // Nueva consulta única por todos los campos relevantes
    Optional<Libro> findByTituloAndAutorNombreAndIdiomaAndNumeroDescargas(String titulo, String autorNombre, String idioma, Integer numeroDescargas);

    // Método para evitar duplicados por título, autor (entidad) e idioma
    Optional<Libro> findByTituloAndAutorAndIdioma(String titulo, Autor autor, String idioma);
} 
package com.alura.LiterAluraChallengeJava.repository;

import com.alura.LiterAluraChallengeJava.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    @Query("SELECT l FROM Libro l WHERE l.idioma = :idioma")
    List<Libro> findLibrosPorIdioma(String idioma);

    @Query("SELECT l FROM Libro l WHERE LOWER(TRIM(l.titulo)) LIKE LOWER(CONCAT('%', TRIM(:titulo), '%'))")
    List<Libro> findByTituloSimilar(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE LOWER(TRIM(l.titulo)) = LOWER(TRIM(:titulo)) AND l.autor.nombre = :autor")
    Optional<Libro> findByTituloYAutor(@Param("titulo") String titulo, @Param("autor") String autor);

    Optional<Libro> findByTitulo(String titulo);
} 
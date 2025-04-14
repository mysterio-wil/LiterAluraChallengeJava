package com.alura.LiterAluraChallengeJava.repository;

import com.alura.LiterAluraChallengeJava.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento >= :anio OR a.fechaFallecimiento IS NULL)")
    List<Autor> findAutoresVivosEnAnio(Integer anio);

    Optional<Autor> findByNombre(String nombre);
} 
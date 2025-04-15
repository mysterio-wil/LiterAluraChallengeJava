package com.alura.LiterAluraChallengeJava.repository;

import com.alura.LiterAluraChallengeJava.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a WHERE " +
            "(:anio BETWEEN a.fechaNacimiento AND a.fechaFallecimiento) OR " +
            "(:anio >= a.fechaNacimiento AND a.fechaFallecimiento IS NULL)")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Integer anio);

    @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Autor> findByNombreSimilar(@Param("nombre") String nombre);

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento >= :inicio AND a.fechaNacimiento <= :fin")
    List<Autor> findByRangoNacimiento(@Param("inicio") Integer inicio, @Param("fin") Integer fin);
}
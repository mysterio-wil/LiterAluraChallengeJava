package com.alura.LiterAluraChallengeJava.repository;

import com.alura.LiterAluraChallengeJava.model.FavoritoLibro;
import com.alura.LiterAluraChallengeJava.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FavoritoLibroRepository extends JpaRepository<FavoritoLibro, Long> {
    Optional<FavoritoLibro> findByLibro(Libro libro);
    void deleteByLibro(Libro libro);
}

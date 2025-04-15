package com.alura.LiterAluraChallengeJava.model;

import jakarta.persistence.*;

@Entity
@Table(name = "favoritos_libros")
public class FavoritoLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "libro_id", unique = true, nullable = false)
    private Libro libro;

    public FavoritoLibro() {}

    public FavoritoLibro(Libro libro) {
        this.libro = libro;
    }

    public Long getId() {
        return id;
    }
}

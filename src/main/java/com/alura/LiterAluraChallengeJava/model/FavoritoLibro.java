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

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public FavoritoLibro() {}

    public FavoritoLibro(Libro libro, Usuario usuario) {
        this.libro = libro;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

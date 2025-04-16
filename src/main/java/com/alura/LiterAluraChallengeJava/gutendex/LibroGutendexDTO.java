package com.alura.LiterAluraChallengeJava.gutendex;

public class LibroGutendexDTO {
    private String titulo;
    private String autores;
    private String idioma;
    private int descargas;

    public LibroGutendexDTO(String titulo, String autores, String idioma, int descargas) {
        this.titulo = titulo;
        this.autores = autores;
        this.idioma = idioma;
        this.descargas = descargas;
    }

    public String getTitulo() { return titulo; }
    public String getAutores() { return autores; }
    public String getIdioma() { return idioma; }
    public int getDescargas() { return descargas; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setAutores(String autores) { this.autores = autores; }
    public void setIdioma(String idioma) { this.idioma = idioma; }
    public void setDescargas(int descargas) { this.descargas = descargas; }
}

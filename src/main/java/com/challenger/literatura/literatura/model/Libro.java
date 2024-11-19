package com.challenger.literatura.literatura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    private String idiomas;
    private Integer descargas;

    @ManyToMany
    @JoinTable(
            name = "libros_autores", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "libro_id"), // Columna para el id del libro
            inverseJoinColumns = @JoinColumn(name = "autor_id") // Columna para el id del autor
    )
    private List<Autor> autores; // Relación con los autores

    public Libro(){}
    // Constructor, getters y setters

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = String.join(", ", datosLibro.idiomas());
        this.descargas = datosLibro.descargas();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
}

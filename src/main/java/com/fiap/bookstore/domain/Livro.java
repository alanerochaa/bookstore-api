package com.fiap.bookstore.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;


@Data
@Entity
@Table(name = "livros")
public class Livro {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String titulo;

    @Setter
    @Column(nullable = false, unique = true)
    private String isbn;

    @Setter
    @ManyToOne(optional = false)
    @JoinColumn(name = "autor_id")
    private Autor autor;

    public Livro() {}
    public Livro(Long id, String titulo, String isbn, Autor autor) {
        this.id = id; this.titulo = titulo; this.isbn = isbn; this.autor = autor;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }

    public String getIsbn() { return isbn; }

    public Autor getAutor() { return autor; }
}
















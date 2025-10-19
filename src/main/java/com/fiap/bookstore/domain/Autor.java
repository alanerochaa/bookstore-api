package com.fiap.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "livros")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros = new ArrayList<>();

    // Construtor prático para testes e instâncias manuais
    public Autor(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    // Construtor adicional para criação rápida
    public Autor(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }
}

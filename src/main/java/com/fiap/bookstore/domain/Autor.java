package com.fiap.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
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

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @Size(max = 1000)
    @Column(length = 1000)
    private String biografia;

    @Past
    private LocalDate dataNascimento;

    @Size(max = 60)
    @Column(length = 60)
    private String nacionalidade;

    @JsonManagedReference
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros = new ArrayList<>();

    public Autor(String nome, String email, String biografia, LocalDate dataNascimento, String nacionalidade) {
        this.nome = nome;
        this.email = email;
        this.biografia = biografia;
        this.dataNascimento = dataNascimento;
        this.nacionalidade = nacionalidade;
    }
}

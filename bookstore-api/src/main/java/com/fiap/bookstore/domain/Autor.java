package com.fiap.bookstore.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@Entity
@Table(name = "autores")
public class Autor {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String nome;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}
    public Autor(Long id, String nome, String email) {
        this.id = id; this.nome = nome; this.email = email;
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public List<Livro> getLivros() { return livros; }
}

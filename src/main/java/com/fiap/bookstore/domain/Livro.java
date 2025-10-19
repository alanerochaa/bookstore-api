package com.fiap.bookstore.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "autor")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "livros")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false, length = 50)
    private String genero;

    @Column(length = 100)
    private String editora;

    @Min(value = 1, message = "O número de páginas deve ser maior que zero")
    private int paginas;

    @Min(value = 1500, message = "O ano de publicação deve ser válido")
    @Max(value = 2025, message = "O ano de publicação não pode ser maior que o atual")
    private int anoPublicacao;

    @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @Size(max = 1000)
    @Column(length = 1000)
    private String descricao;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    // 🔹 Construtor prático (útil em testes ou criação rápida)
    public Livro(String titulo, String isbn, String genero, String editora, int paginas,
                 int anoPublicacao, BigDecimal preco, String descricao, Autor autor) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.genero = genero;
        this.editora = editora;
        this.paginas = paginas;
        this.anoPublicacao = anoPublicacao;
        this.preco = preco;
        this.descricao = descricao;
        this.autor = autor;
    }
}

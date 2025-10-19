package com.fiap.bookstore;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.domain.Livro;
import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.dto.LivroDTO;
import com.fiap.bookstore.mapper.BookstoreMapperImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookstoreMapperImplTest {

    private final BookstoreMapperImpl mapper = new BookstoreMapperImpl();

    @Test
    void deveConverterAutorParaDTO() {
        Autor autor = new Autor(1L, "Machado", "m@teste.com", "Bio", null, "Brasileiro", null);

        AutorDTO dto = mapper.toAutorDTO(autor);

        assertEquals(1L, dto.id());
        assertEquals("Machado", dto.nome());
        assertEquals("m@teste.com", dto.email());
    }

    @Test
    void deveConverterAutorDTOParaEntity() {
        AutorDTO dto = new AutorDTO(1L, "Machado", "m@teste.com", "Bio", null, "Brasileiro");

        Autor autor = mapper.toAutorEntity(dto);

        assertEquals(dto.id(), autor.getId());
        assertEquals(dto.nome(), autor.getNome());
        assertEquals(dto.email(), autor.getEmail());
        assertEquals(dto.biografia(), autor.getBiografia());
    }

    @Test
    void deveConverterLivroParaDTO() {
        Autor autor = new Autor(1L, "Machado", "m@teste.com", null, null, null, null);
        Livro livro = new Livro(1L, "Dom Casmurro", "123", "Romance", "Editora X",
                200, 1899, BigDecimal.TEN, "Descrição", autor);

        LivroDTO dto = mapper.toLivroDTO(livro);

        assertEquals("Dom Casmurro", dto.titulo());
        assertEquals(1L, dto.autorId());
        assertEquals(BigDecimal.TEN, dto.preco());
    }

    @Test
    void deveConverterLivroDTOParaEntity() {
        Autor autor = new Autor(1L, "Machado", "m@teste.com", null, null, null, null);
        LivroDTO dto = new LivroDTO(1L, "Dom Casmurro", "123", 1L,
                "Romance", "Editora X", 200, 1899, BigDecimal.TEN, "Descrição");

        Livro livro = mapper.toLivroEntity(dto, autor);

        assertEquals(dto.id(), livro.getId());
        assertEquals(dto.titulo(), livro.getTitulo());
        assertEquals(dto.isbn(), livro.getIsbn());
        assertEquals(dto.preco(), livro.getPreco());
        assertEquals(autor, livro.getAutor());
    }
}

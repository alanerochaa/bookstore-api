package com.fiap.bookstore.service;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.domain.Livro;
import com.fiap.bookstore.dto.LivroDTO;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import com.fiap.bookstore.mapper.BookstoreMapper;
import com.fiap.bookstore.repository.AutorRepository;
import com.fiap.bookstore.repository.LivroRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ✅ Testes unitários de LivroServiceImpl.
 * Garante o comportamento correto dos métodos CRUD do serviço de livros.
 */
class LivroServiceImplTest {

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;
    private BookstoreMapper mapper;
    private LivroServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        livroRepository = mock(LivroRepository.class);
        autorRepository = mock(AutorRepository.class);
        mapper = mock(BookstoreMapper.class);
        service = new LivroServiceImpl(livroRepository, autorRepository, mapper);
    }

    @Test
    @DisplayName("📖 Deve buscar livro por ID com sucesso")
    void deveBuscarLivroPorId() {
        Autor autor = new Autor(1L, "Machado", "machado@teste.com", null, null, null, null);
        Livro livro = new Livro(1L, "Dom Casmurro", "123", "Romance", "Editora A", 200, 1899,
                BigDecimal.valueOf(39.9), "Descrição clássica", autor);
        LivroDTO dto = new LivroDTO(1L, "Dom Casmurro", "123", 1L,
                "Romance", "Editora A", 200, 1899, BigDecimal.valueOf(39.9), "Descrição clássica");

        when(livroRepository.findById(1L)).thenReturn(Optional.of(livro));
        when(mapper.toLivroDTO(livro)).thenReturn(dto);

        LivroDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Dom Casmurro", resultado.titulo());
        verify(livroRepository, times(1)).findById(1L);
        verify(mapper, times(1)).toLivroDTO(livro);
    }

    @Test
    @DisplayName("🚫 Deve lançar exceção ao buscar livro inexistente")
    void deveLancarErroQuandoLivroNaoExiste() {
        when(livroRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));

        verify(livroRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("✨ Deve criar um novo livro com sucesso")
    void deveCriarLivroComSucesso() {
        Autor autor = new Autor(1L, "Machado", "machado@teste.com", null, null, null, null);
        LivroDTO dto = new LivroDTO(
                null, "Novo Livro", "321", 1L,
                "Ficção", "Editora X", 300, 2020,
                BigDecimal.valueOf(59.9), "Descrição legal"
        );

        Livro livro = new Livro(1L, "Novo Livro", "321", "Ficção", "Editora X",
                300, 2020, BigDecimal.valueOf(59.9), "Descrição legal", autor);

        LivroDTO dtoSalvo = new LivroDTO(1L, "Novo Livro", "321", 1L,
                "Ficção", "Editora X", 300, 2020, BigDecimal.valueOf(59.9), "Descrição legal");

        when(autorRepository.findById(1L)).thenReturn(Optional.of(autor));
        when(mapper.toLivroEntity(dto, autor)).thenReturn(livro);
        when(livroRepository.save(livro)).thenReturn(livro);
        when(mapper.toLivroDTO(livro)).thenReturn(dtoSalvo);

        LivroDTO resultado = service.criar(dto);

        assertNotNull(resultado);
        assertEquals("Novo Livro", resultado.titulo());
        assertEquals("Ficção", resultado.genero());
        verify(autorRepository, times(1)).findById(1L);
        verify(livroRepository, times(1)).save(livro);
        verify(mapper, times(1)).toLivroDTO(livro);
    }

    @Test
    @DisplayName("❌ Deve lançar erro ao tentar criar livro com autor inexistente")
    void deveLancarErroAoCriarLivroComAutorInexistente() {
        LivroDTO dto = new LivroDTO(
                null, "Sem Autor", "999", 10L,
                "Drama", "Editora Y", 150, 2021,
                BigDecimal.valueOf(29.9), "Sem autor válido"
        );

        when(autorRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.criar(dto));

        verify(autorRepository, times(1)).findById(10L);
        verifyNoInteractions(mapper);
    }
}

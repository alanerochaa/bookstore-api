package com.fiap.bookstore.service;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import com.fiap.bookstore.mapper.BookstoreMapper;
import com.fiap.bookstore.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ✅ Testes unitários de AutorServiceImpl
 * Garante o comportamento correto dos métodos CRUD do serviço de autores.
 */
class AutorServiceImplTest {

    private AutorRepository repository;
    private BookstoreMapper mapper;
    private AutorServiceImpl service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        repository = mock(AutorRepository.class);
        mapper = mock(BookstoreMapper.class);
        service = new AutorServiceImpl(repository, mapper);
    }

    @Test
    @DisplayName("✅ Deve buscar autor por ID com sucesso")
    void deveBuscarAutorPorId() {
        Autor autor = new Autor(1L, "Machado de Assis", "machado@teste.com", null, null, null, null);
        AutorDTO dto = new AutorDTO(1L, "Machado de Assis", "machado@teste.com", null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.of(autor));
        when(mapper.toAutorDTO(autor)).thenReturn(dto);

        AutorDTO result = service.buscarPorId(1L);

        assertNotNull(result);
        assertEquals("Machado de Assis", result.nome());
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toAutorDTO(autor);
    }

    @Test
    @DisplayName("❌ Deve lançar exceção ao buscar autor inexistente")
    void deveLancarExcecaoSeAutorNaoExistir() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.buscarPorId(99L));

        verify(repository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("📋 Deve listar autores corretamente")
    void deveListarAutores() {
        when(repository.findAll()).thenReturn(List.of(
                new Autor(1L, "Autor A", "a@a.com", null, null, null, null),
                new Autor(2L, "Autor B", "b@b.com", null, null, null, null)
        ));

        when(mapper.toAutorDTO(any())).thenAnswer(invocation -> {
            Autor a = invocation.getArgument(0);
            return new AutorDTO(a.getId(), a.getNome(), a.getEmail(), null, null, null);
        });

        List<AutorDTO> lista = service.listar();

        assertNotNull(lista);
        assertEquals(2, lista.size());
        assertEquals("Autor A", lista.get(0).nome());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("💾 Deve salvar um novo autor com sucesso")
    void deveSalvarAutor() {
        AutorDTO dto = new AutorDTO(null, "Novo Autor", "novo@teste.com", null, null, null);
        Autor autor = new Autor(1L, "Novo Autor", "novo@teste.com", null, null, null, null);

        when(mapper.toAutorEntity(dto)).thenReturn(autor);
        when(repository.save(autor)).thenReturn(autor);
        when(mapper.toAutorDTO(autor)).thenReturn(
                new AutorDTO(1L, "Novo Autor", "novo@teste.com", null, null, null)
        );

        AutorDTO salvo = service.criar(dto);

        assertNotNull(salvo);
        assertEquals("Novo Autor", salvo.nome());
        assertEquals(1L, salvo.id());
        verify(repository, times(1)).save(autor);
    }

    @Test
    @DisplayName("✏️ Deve atualizar autor existente com sucesso")
    void deveAtualizarAutor() {
        Autor existente = new Autor(1L, "Antigo", "antigo@teste.com", null, null, null, null);
        Autor atualizado = new Autor(1L, "Atualizado", "novo@teste.com", null, null, null, null);
        AutorDTO dto = new AutorDTO(1L, "Atualizado", "novo@teste.com", null, null, null);

        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(mapper.toAutorEntity(dto)).thenReturn(atualizado);
        when(repository.save(atualizado)).thenReturn(atualizado);
        when(mapper.toAutorDTO(atualizado)).thenReturn(dto);

        AutorDTO result = service.atualizar(1L, dto);

        assertNotNull(result);
        assertEquals("Atualizado", result.nome());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(atualizado);
    }

    @Test
    @DisplayName("🗑️ Deve deletar autor existente com sucesso")
    void deveDeletarAutor() {
        Autor autor = new Autor(1L, "Deletar", "del@teste.com", null, null, null, null);
        when(repository.findById(1L)).thenReturn(Optional.of(autor));

        service.deletar(1L);

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(autor);
    }

    @Test
    @DisplayName("🚫 Deve lançar erro ao tentar deletar autor inexistente")
    void deveLancarErroAoDeletarAutorInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deletar(99L));

        verify(repository, times(1)).findById(99L);
    }
}

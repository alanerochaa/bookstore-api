package com.fiap.bookstore.controller;

import com.fiap.bookstore.dto.LivroDTO;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import com.fiap.bookstore.service.LivroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ✅ Testes unitários do LivroController com MockMvc.
 * Usa mocks do serviço e foca apenas no comportamento HTTP.
 */
@WebMvcTest(LivroController.class)
class LivroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private LivroService service;

    @Test
    @DisplayName("📚 Deve listar livros com sucesso")
    void deveListarLivros() throws Exception {
        when(service.listar()).thenReturn(List.of(
                new LivroDTO(1L, "Dom Casmurro", "123", 1L,
                        "Romance", "Editora X", 200, 1899,
                        BigDecimal.valueOf(39.9), "Descrição clássica")
        ));

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Dom Casmurro"))
                .andExpect(jsonPath("$[0].genero").value("Romance"));
    }

    @Test
    @DisplayName("✨ Deve criar livro com sucesso")
    void deveCriarLivroComSucesso() throws Exception {
        LivroDTO dto = new LivroDTO(
                null,
                "O Guarani",
                "978-85-01-12345-0",
                1L,
                "Romance",
                "Editora Nacional",
                320,
                1857,
                BigDecimal.valueOf(29.90),
                "Um clássico da literatura romântica brasileira."
        );

        LivroDTO salvo = new LivroDTO(
                1L,
                "O Guarani",
                "978-85-01-12345-0",
                1L,
                "Romance",
                "Editora Nacional",
                320,
                1857,
                BigDecimal.valueOf(29.90),
                "Um clássico da literatura romântica brasileira."
        );

        when(service.criar(any(LivroDTO.class))).thenReturn(salvo);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.titulo").value("O Guarani"));
    }

    @Test
    @DisplayName("🚫 Deve retornar 404 ao buscar livro inexistente")
    void deveRetornar404AoBuscarLivroInexistente() throws Exception {
        when(service.buscarPorId(9999L)).thenThrow(new ResourceNotFoundException("Livro não encontrado"));

        mockMvc.perform(get("/livros/9999"))
                .andExpect(status().isNotFound());
    }
}

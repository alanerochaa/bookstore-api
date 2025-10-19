package com.fiap.bookstore.controller;

import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import com.fiap.bookstore.service.AutorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ✅ Testes unitários do AutorController com MockMvc.
 * Foca apenas nas requisições HTTP e respostas, sem subir o contexto completo.
 */
@WebMvcTest(AutorController.class)
class AutorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AutorService service;

    @Test
    @DisplayName("📋 Deve listar autores com sucesso")
    void deveListarAutores() throws Exception {
        when(service.listar()).thenReturn(List.of(
                new AutorDTO(1L, "Machado de Assis", "machado@teste.com",
                        "Autor clássico brasileiro", LocalDate.of(1839, 6, 21), "Brasileiro")
        ));

        mockMvc.perform(get("/autores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Machado de Assis"))
                .andExpect(jsonPath("$[0].email").value("machado@teste.com"));
    }

    @Test
    @DisplayName("📝 Deve criar autor com sucesso")
    void deveCriarAutorComSucesso() throws Exception {
        AutorDTO dto = new AutorDTO(
                null,
                "Machado de Assis",
                "machado@teste.com",
                "Autor clássico brasileiro",
                LocalDate.of(1839, 6, 21),
                "Brasileiro"
        );

        AutorDTO salvo = new AutorDTO(
                1L,
                "Machado de Assis",
                "machado@teste.com",
                "Autor clássico brasileiro",
                LocalDate.of(1839, 6, 21),
                "Brasileiro"
        );

        when(service.criar(any(AutorDTO.class))).thenReturn(salvo);

        mockMvc.perform(post("/autores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Machado de Assis"));
    }

    @Test
    @DisplayName("🚫 Deve retornar 404 ao buscar autor inexistente")
    void deveRetornar404AoBuscarAutorInexistente() throws Exception {
        when(service.buscarPorId(9999L)).thenThrow(new ResourceNotFoundException("Autor não encontrado"));

        mockMvc.perform(get("/autores/9999"))
                .andExpect(status().isNotFound());
    }
}

package com.fiap.bookstore.controller;

import com.fiap.bookstore.dto.LivroDTO;
import com.fiap.bookstore.service.LivroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livros", description = "Gerenciamento de livros e seus autores")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @Operation(
            summary = "Listar todos os livros",
            description = "Retorna a lista completa de livros cadastrados no sistema."
    )
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping
    public List<LivroDTO> listar() {
        return livroService.listar();
    }

    @Operation(
            summary = "Buscar livro por ID",
            description = "Retorna os dados de um livro específico com base no seu ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("/{id}")
    public LivroDTO buscar(
            @Parameter(description = "ID do livro a ser buscado", example = "1")
            @PathVariable("id") Long id) {
        return livroService.buscarPorId(id);
    }

    @Operation(
            summary = "Criar novo livro",
            description = "Cadastra um novo livro vinculado a um autor existente."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação no corpo da requisição")
    })
    @PostMapping
    public ResponseEntity<LivroDTO> criar(@RequestBody @Valid LivroDTO dto, UriComponentsBuilder uriBuilder) {
        LivroDTO created = livroService.criar(dto);
        URI uri = uriBuilder.path("/livros/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(
            summary = "Atualizar livro",
            description = "Atualiza as informações de um livro existente com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @PutMapping("/{id}")
    public LivroDTO atualizar(
            @Parameter(description = "ID do livro a ser atualizado", example = "1")
            @PathVariable("id") Long id,
            @RequestBody @Valid LivroDTO dto) {
        return livroService.atualizar(id, dto);
    }

    @Operation(
            summary = "Excluir livro",
            description = "Remove um livro do sistema com base no ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do livro a ser excluído", example = "1")
            @PathVariable("id") Long id) {
        livroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}

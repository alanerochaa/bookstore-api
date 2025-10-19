package com.fiap.bookstore.controller;

import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.dto.LivroDTO;
import com.fiap.bookstore.service.AutorService;
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
@RequestMapping("/autores")
@Tag(name = "Autores", description = "Gerenciamento completo de autores e seus livros cadastrados")
public class AutorController {

    private final AutorService autorService;
    private final LivroService livroService;

    public AutorController(AutorService autorService, LivroService livroService) {
        this.autorService = autorService;
        this.livroService = livroService;
    }

    @Operation(
            summary = "Cadastrar um novo autor",
            description = """
                    Cria um novo autor com as informações básicas, como nome, e-mail, biografia, 
                    nacionalidade e data de nascimento.
                    O campo **email** deve ser único.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados")
    })
    @PostMapping
    public ResponseEntity<AutorDTO> criar(
            @RequestBody @Valid AutorDTO dto,
            UriComponentsBuilder uriBuilder) {
        AutorDTO created = autorService.criar(dto);
        URI uri = uriBuilder.path("/autores/{id}").buildAndExpand(created.id()).toUri();
        return ResponseEntity.created(uri).body(created);
    }

    @Operation(
            summary = "Listar autores",
            description = """
                    Retorna uma lista com todos os autores cadastrados, incluindo nome, e-mail, 
                    nacionalidade, biografia e data de nascimento (quando informados).
                    """
    )
    @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso")
    @GetMapping
    public List<AutorDTO> listar() {
        return autorService.listar();
    }

    @Operation(
            summary = "Buscar autor por ID",
            description = "Retorna os dados detalhados de um autor específico, conforme o ID informado."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}")
    public AutorDTO buscar(
            @Parameter(description = "ID do autor a ser buscado", example = "1")
            @PathVariable("id") Long id) {
        return autorService.buscarPorId(id);
    }

    @Operation(
            summary = "Atualizar autor",
            description = """
                    Atualiza as informações de um autor existente, incluindo biografia, nacionalidade 
                    e data de nascimento.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @PutMapping("/{id}")
    public AutorDTO atualizar(
            @Parameter(description = "ID do autor a ser atualizado", example = "1")
            @PathVariable("id") Long id,
            @RequestBody @Valid AutorDTO dto) {
        return autorService.atualizar(id, dto);
    }

    @Operation(
            summary = "Excluir autor",
            description = """
                    Remove um autor do sistema com base no ID informado.
                    Todos os livros associados ao autor também serão excluídos.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(
            @Parameter(description = "ID do autor a ser removido", example = "1")
            @PathVariable("id") Long id) {
        autorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar livros de um autor",
            description = """
                    Retorna todos os livros cadastrados pertencentes a um autor específico.
                    É possível visualizar detalhes como título, gênero, preço e editora.
                    """
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livros do autor retornados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado")
    })
    @GetMapping("/{id}/livros")
    public List<LivroDTO> listarLivrosDoAutor(
            @Parameter(description = "ID do autor cujos livros serão listados", example = "1")
            @PathVariable("id") Long id) {
        return livroService.listarPorAutor(id);
    }
}

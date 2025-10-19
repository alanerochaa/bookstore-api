package com.fiap.bookstore.service;

import com.fiap.bookstore.dto.LivroDTO;
import java.util.List;

public interface LivroService {
    LivroDTO criar(LivroDTO dto);
    LivroDTO buscarPorId(Long id);
    List<LivroDTO> listar();
    LivroDTO atualizar(Long id, LivroDTO dto);
    void deletar(Long id);
    List<LivroDTO> listarPorAutor(Long autorId);
}

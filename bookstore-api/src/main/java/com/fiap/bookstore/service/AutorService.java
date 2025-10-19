package com.fiap.bookstore.service;

import com.fiap.bookstore.dto.AutorDTO;
import java.util.List;

public interface AutorService {
    AutorDTO criar(AutorDTO dto);
    AutorDTO buscarPorId(Long id);
    List<AutorDTO> listar();
    AutorDTO atualizar(Long id, AutorDTO dto);
    void deletar(Long id);
}

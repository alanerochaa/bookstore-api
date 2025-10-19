package com.fiap.bookstore.service.impl;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import com.fiap.bookstore.mapper.BookstoreMapper;
import com.fiap.bookstore.repository.AutorRepository;
import com.fiap.bookstore.service.AutorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AutorServiceImpl implements AutorService {

    private final AutorRepository autorRepository;
    private final BookstoreMapper mapper;

    public AutorServiceImpl(AutorRepository autorRepository, BookstoreMapper mapper) {
        this.autorRepository = autorRepository;
        this.mapper = mapper;
    }

    @Override
    public AutorDTO criar(AutorDTO dto) {
        Autor saved = autorRepository.save(mapper.toAutorEntity(dto));
        return mapper.toAutorDTO(saved);
    }

    @Override @Transactional(readOnly = true)
    public AutorDTO buscarPorId(Long id) {
        return mapper.toAutorDTO(buscarEntity(id));
    }

    @Override @Transactional(readOnly = true)
    public List<AutorDTO> listar() {
        return autorRepository.findAll().stream().map(mapper::toAutorDTO).toList();
    }

    @Override
    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor autor = buscarEntity(id);
        autor.setNome(dto.nome());
        autor.setEmail(dto.email());
        return mapper.toAutorDTO(autorRepository.save(autor));
    }

    @Override
    public void deletar(Long id) {
        Autor autor = buscarEntity(id);
        autorRepository.delete(autor);
    }

    private Autor buscarEntity(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor id %d não encontrado".formatted(id)));
    }
}

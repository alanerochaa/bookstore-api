package com.fiap.bookstore.service.impl;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.domain.Livro;
import com.fiap.bookstore.dto.LivroDTO;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import com.fiap.bookstore.mapper.BookstoreMapper;
import com.fiap.bookstore.repository.AutorRepository;
import com.fiap.bookstore.repository.LivroRepository;
import com.fiap.bookstore.service.LivroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final BookstoreMapper mapper;

    public LivroServiceImpl(LivroRepository livroRepository, AutorRepository autorRepository,
                            BookstoreMapper mapper) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
        this.mapper = mapper;
    }

    @Override
    public LivroDTO criar(LivroDTO dto) {
        Autor autor = autorRepository.findById(dto.autorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor id %d não encontrado".formatted(dto.autorId())));
        Livro saved = livroRepository.save(mapper.toLivroEntity(dto, autor));
        return mapper.toLivroDTO(saved);
    }

    @Override @Transactional(readOnly = true)
    public LivroDTO buscarPorId(Long id) {
        return mapper.toLivroDTO(buscarEntity(id));
    }

    @Override @Transactional(readOnly = true)
    public List<LivroDTO> listar() {
        return livroRepository.findAll().stream().map(mapper::toLivroDTO).toList();
    }

    @Override
    public LivroDTO atualizar(Long id, LivroDTO dto) {
        Livro livro = buscarEntity(id);
        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        if (dto.autorId() != null && (livro.getAutor() == null || !dto.autorId().equals(livro.getAutor().getId()))) {
            Autor autor = autorRepository.findById(dto.autorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Autor id %d não encontrado".formatted(dto.autorId())));
            livro.setAutor(autor);
        }
        return mapper.toLivroDTO(livroRepository.save(livro));
    }

    @Override
    public void deletar(Long id) {
        livroRepository.delete(buscarEntity(id));
    }

    @Override @Transactional(readOnly = true)
    public List<LivroDTO> listarPorAutor(Long autorId) {
        return livroRepository.findByAutorId(autorId).stream().map(mapper::toLivroDTO).toList();
    }

    private Livro buscarEntity(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro id %d não encontrado".formatted(id)));
    }
}

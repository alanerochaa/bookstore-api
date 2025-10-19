package com.fiap.bookstore.mapper;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.domain.Livro;
import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.dto.LivroDTO;
import org.springframework.stereotype.Component;

@Component
public class BookstoreMapperImpl implements BookstoreMapper {

    @Override
    public AutorDTO toAutorDTO(Autor entity) {
        if (entity == null) return null;
        return new AutorDTO(entity.getId(), entity.getNome(), entity.getEmail());
    }

    @Override
    public Autor toAutorEntity(AutorDTO dto) {
        if (dto == null) return null;
        return new Autor(dto.id(), dto.nome(), dto.email());
    }

    @Override
    public LivroDTO toLivroDTO(Livro entity) {
        if (entity == null) return null;
        return new LivroDTO(entity.getId(), entity.getTitulo(), entity.getIsbn(),
                entity.getAutor() != null ? entity.getAutor().getId() : null);
    }

    @Override
    public Livro toLivroEntity(LivroDTO dto, Autor autor) {
        if (dto == null) return null;
        return new Livro(dto.id(), dto.titulo(), dto.isbn(), autor);
    }
}

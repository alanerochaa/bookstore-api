package com.fiap.bookstore.mapper;

import com.fiap.bookstore.domain.Autor;
import com.fiap.bookstore.domain.Livro;
import com.fiap.bookstore.dto.AutorDTO;
import com.fiap.bookstore.dto.LivroDTO;

public interface BookstoreMapper {
    AutorDTO toAutorDTO(Autor entity);
    Autor toAutorEntity(AutorDTO dto);

    LivroDTO toLivroDTO(Livro entity);
    Livro toLivroEntity(LivroDTO dto, Autor autor);
}

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

        return new AutorDTO(
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getBiografia(),
                entity.getDataNascimento(),
                entity.getNacionalidade()
        );
    }

    @Override
    public Autor toAutorEntity(AutorDTO dto) {
        if (dto == null) return null;

        Autor autor = new Autor();
        autor.setId(dto.id());
        autor.setNome(dto.nome());
        autor.setEmail(dto.email());
        autor.setBiografia(dto.biografia());
        autor.setDataNascimento(dto.dataNascimento());
        autor.setNacionalidade(dto.nacionalidade());
        return autor;
    }

    @Override
    public LivroDTO toLivroDTO(Livro entity) {
        if (entity == null) return null;

        return new LivroDTO(
                entity.getId(),
                entity.getTitulo(),
                entity.getIsbn(),
                entity.getAutor() != null ? entity.getAutor().getId() : null,
                entity.getGenero(),
                entity.getEditora(),
                entity.getPaginas(),
                entity.getAnoPublicacao(),
                entity.getPreco(),
                entity.getDescricao()
        );
    }

    @Override
    public Livro toLivroEntity(LivroDTO dto, Autor autor) {
        if (dto == null) return null;

        Livro livro = new Livro();
        livro.setId(dto.id());
        livro.setTitulo(dto.titulo());
        livro.setIsbn(dto.isbn());
        livro.setAutor(autor);
        livro.setGenero(dto.genero());
        livro.setEditora(dto.editora());
        livro.setPaginas(dto.paginas());
        livro.setAnoPublicacao(dto.anoPublicacao());
        livro.setPreco(dto.preco());
        livro.setDescricao(dto.descricao());
        return livro;
    }
}

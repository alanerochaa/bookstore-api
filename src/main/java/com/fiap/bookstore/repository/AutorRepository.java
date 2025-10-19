package com.fiap.bookstore.repository;

import com.fiap.bookstore.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    boolean existsByEmail(String email);
}

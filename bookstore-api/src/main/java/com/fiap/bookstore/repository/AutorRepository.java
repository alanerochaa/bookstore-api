package com.fiap.bookstore.repository;

import com.fiap.bookstore.domain.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    boolean existsByEmail(String email);
}

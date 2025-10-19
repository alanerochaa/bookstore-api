package com.fiap.bookstore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LivroDTO(
        Long id,
        @NotBlank String titulo,
        @NotBlank String isbn,
        @NotNull Long autorId
) {}

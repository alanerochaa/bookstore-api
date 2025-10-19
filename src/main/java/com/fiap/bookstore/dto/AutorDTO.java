package com.fiap.bookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AutorDTO(
        Long id,
        @NotBlank String nome,
        @NotBlank @Email String email
) {}

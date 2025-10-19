package com.fiap.bookstore.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;


public record AutorDTO(

        Long id,

        @NotBlank(message = "O nome do autor é obrigatório")
        @Size(max = 100, message = "O nome pode ter no máximo 100 caracteres")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório")
        @Email(message = "Formato de e-mail inválido")
        String email,

        @Size(max = 1000, message = "A biografia pode ter no máximo 1000 caracteres")
        String biografia,

        @Past(message = "A data de nascimento deve estar no passado")
        LocalDate dataNascimento,

        @Size(max = 60, message = "A nacionalidade pode ter no máximo 60 caracteres")
        String nacionalidade
) {}

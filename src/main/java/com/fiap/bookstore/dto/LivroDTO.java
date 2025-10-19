package com.fiap.bookstore.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record LivroDTO(
        Long id,

        @NotBlank(message = "O título é obrigatório")
        @Size(max = 150, message = "O título pode ter no máximo 150 caracteres")
        String titulo,

        @NotBlank(message = "O ISBN é obrigatório")
        @Size(max = 20, message = "O ISBN pode ter no máximo 20 caracteres")
        String isbn,

        @NotNull(message = "O ID do autor é obrigatório")
        Long autorId,

        @NotBlank(message = "O gênero é obrigatório")
        @Size(max = 50, message = "O gênero pode ter no máximo 50 caracteres")
        String genero,

        @Size(max = 100, message = "A editora pode ter no máximo 100 caracteres")
        String editora,

        @Min(value = 1, message = "O número de páginas deve ser maior que zero")
        int paginas,

        @Min(value = 1500, message = "O ano de publicação deve ser válido")
        @Max(value = 2025, message = "O ano de publicação não pode ser maior que o atual")
        int anoPublicacao,

        @DecimalMin(value = "0.0", inclusive = false, message = "O preço deve ser maior que zero")
        BigDecimal preco,

        @Size(max = 1000, message = "A descrição pode ter no máximo 1000 caracteres")
        String descricao
) {}

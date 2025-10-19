package com.fiap.bookstore;

import com.fiap.bookstore.exception.ApiErrorResponse;
import com.fiap.bookstore.exception.GlobalExceptionHandler;
import com.fiap.bookstore.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.core.MethodParameter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private HttpServletRequest request;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
        request = mock(HttpServletRequest.class);
        when(request.getRequestURI()).thenReturn("/api/teste");
    }

    @Test
    @DisplayName("Deve tratar ResourceNotFoundException e retornar 404")
    void deveTratarResourceNotFoundException() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Autor não encontrado");

        ResponseEntity<ApiErrorResponse> response = handler.handleNotFound(ex, request);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals("Recurso não encontrado", response.getBody().error());
        assertEquals("Autor não encontrado", response.getBody().message());
        assertEquals("/api/teste", response.getBody().path());
    }

    @Test
    @DisplayName("Deve tratar erro de validação e retornar campos inválidos com status 400")
    void deveTratarErroDeValidacao() {
        // Simula erro de validação
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "autor");
        bindingResult.addError(new FieldError("autor", "email", "Email inválido"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(
                mock(MethodParameter.class),
                bindingResult
        );

        ResponseEntity<ApiErrorResponse> response = handler.handleValidation(ex, request);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Erro de validação", response.getBody().error());
        assertTrue(response.getBody().details().toString().contains("email"));
    }

    @Test
    @DisplayName("Deve tratar erro genérico e retornar status 500")
    void deveTratarErroGenerico() {
        Exception ex = new Exception("Falha inesperada");

        ResponseEntity<ApiErrorResponse> response = handler.handleGeneric(ex, request);

        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Erro interno no servidor", response.getBody().error());
        assertTrue(response.getBody().message().contains("Falha inesperada"));
    }
}

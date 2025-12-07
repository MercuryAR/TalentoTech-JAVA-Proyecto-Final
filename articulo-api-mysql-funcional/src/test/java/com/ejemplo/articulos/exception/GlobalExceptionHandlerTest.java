package com.ejemplo.articulos.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;
    private WebRequest request;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
        MockHttpServletRequest servletRequest = new MockHttpServletRequest();
        servletRequest.setRequestURI("/api/productos/1");
        request = new ServletWebRequest(servletRequest);
    }

    @Test
    void testHandleProductoNotFound() {
        // Arrange
        ProductoNotFoundException exception = new ProductoNotFoundException(1L);

        // Act
        ResponseEntity<Object> response = handler.handleProductoNotFound(exception, request);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(404, body.get("status"));
        assertEquals("Not Found", body.get("error"));
        assertTrue(body.get("message").toString().contains("Producto con ID 1 no encontrado"));
    }

    @Test
    void testHandleBadRequest() {
        // Arrange
        BadRequestException exception = new BadRequestException("Petición inválida");

        // Act
        ResponseEntity<Object> response = handler.handleBadRequest(exception, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Petición inválida", body.get("message"));
    }

    @Test
    void testHandleConflict() {
        // Arrange
        ConflictException exception = new ConflictException("Producto duplicado");

        // Act
        ResponseEntity<Object> response = handler.handleConflict(exception, request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(409, body.get("status"));
        assertEquals("Producto duplicado", body.get("message"));
    }

    @Test
    void testHandleInternalServerError() {
        // Arrange
        InternalServerErrorException exception = new InternalServerErrorException("Error del servidor");

        // Act
        ResponseEntity<Object> response = handler.handleInternalServerError(exception, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("Error del servidor", body.get("message"));
    }
}

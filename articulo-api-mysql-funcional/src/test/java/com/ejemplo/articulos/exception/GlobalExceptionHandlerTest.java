package com.ejemplo.articulos.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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

    @Test
    @SuppressWarnings("unchecked")
    void testHandleValidationException() {
        // Arrange
        Map<String, String> errores = new HashMap<>();
        errores.put("precio", "Debe ser mayor que 0");
        errores.put("nombre", "No puede estar vacío");
        ValidationException exception = new ValidationException("El precio no puede ser negativo", errores);

        // Act
        ResponseEntity<Object> response = handler.handleValidationException(exception, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(400, body.get("status"));
        assertEquals("Validation Error", body.get("error"));
        assertEquals("El precio no puede ser negativo", body.get("message"));
        assertTrue(body.containsKey("errors"));
        Map<String, String> bodyErrors = (Map<String, String>) body.get("errors");
        assertEquals("Debe ser mayor que 0", bodyErrors.get("precio"));
    }

    @Test
    @SuppressWarnings("unchecked")
    void testHandleGlobalException() {
        // Arrange
        Exception exception = new Exception("Error inesperado en el servidor");

        // Act
        ResponseEntity<Object> response = handler.handleGlobalException(exception, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals(500, body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertTrue(body.get("message").toString().contains("Ocurrió un error inesperado"));
        assertTrue(body.containsKey("timestamp"));
        assertTrue(body.containsKey("path"));
    }
}

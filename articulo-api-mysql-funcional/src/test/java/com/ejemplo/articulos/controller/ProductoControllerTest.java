package com.ejemplo.articulos.controller;

import com.ejemplo.articulos.exception.ProductoNotFoundException;
import com.ejemplo.articulos.model.Producto;
import com.ejemplo.articulos.model.Remera;
import com.ejemplo.articulos.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoControllerTest {

    @Mock
    private ProductoService service;

    @InjectMocks
    private ProductoController controller;

    private Remera remeraTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        remeraTest = new Remera();
        remeraTest.setId(1L);
        remeraTest.setNombre("Remera Test");
        remeraTest.setPrecio(1000.0);
        remeraTest.setMarca("Nike");
        remeraTest.setTalle(42);
        remeraTest.setMaterial("Algodón");
    }

    @Test
    void testListar() {
        // Arrange
        List<Producto> productos = Arrays.asList(remeraTest);
        when(service.listarTodos()).thenReturn(productos);

        // Act
        List<Map<String, Object>> resultado = controller.listar();

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Remera Test", resultado.getFirst().get("nombre"));
        verify(service, times(1)).listarTodos();
    }

    @Test
    void testObtenerProductoExistente() {
        // Arrange
        when(service.buscarPorId(1L)).thenReturn(Optional.of(remeraTest));

        // Act
        ResponseEntity<Map<String, Object>> response = controller.obtener(1L);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Remera Test", response.getBody().get("nombre"));
        verify(service, times(1)).buscarPorId(1L);
    }

    @Test
    void testObtenerProductoNoExistente() {
        // Arrange
        when(service.buscarPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductoNotFoundException.class, () -> {
            controller.obtener(999L);
        });
        verify(service, times(1)).buscarPorId(999L);
    }

    @Test
    void testCalculoPrecioFinalRemera() {
        // Act
        Double precioFinal = remeraTest.calcularPrecioFinal();

        // Assert
        assertEquals(900.0, precioFinal); // 10% descuento
    }

    @Test
    void testGetTipo() {
        // Act
        String tipo = remeraTest.getTipo();

        // Assert
        assertEquals("Remera", tipo);
    }
}

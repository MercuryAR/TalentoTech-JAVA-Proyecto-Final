package com.ejemplo.articulos.controller;

import com.ejemplo.articulos.exception.ProductoNotFoundException;
import com.ejemplo.articulos.model.Remera;
import com.ejemplo.articulos.service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Pruebas de excepciones en ProductoController")
class ProductoControllerExceptionTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ProductoService productoService;

    @Test
    @DisplayName("Debería retornar 404 cuando el producto no existe")
    void testObtenerProductoNoEncontrado() throws Exception {
        // Arrange
        Long productoId = 999L;
        when(productoService.buscarPorId(productoId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/productos/{id}", productoId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.error", is("Not Found")))
                .andExpect(jsonPath("$.message", containsString("Producto con ID 999 no encontrado")))
                .andExpect(jsonPath("$.timestamp", notNullValue()))
                .andExpect(jsonPath("$.path", containsString("/api/productos/999")));
    }

    @Test
    @DisplayName("Debería retornar 400 cuando falta el tipo de producto en creación")
    void testCrearProductoSinTipo() throws Exception {
        // Arrange
        Map<String, Object> productoSinTipo = new HashMap<>();
        productoSinTipo.put("nombre", "Remera Test");
        productoSinTipo.put("precio", 50.0);
        // No se incluye "tipo"

        // Act & Assert
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoSinTipo)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería retornar 400 cuando el tipo de producto es inválido")
    void testCrearProductoTipoInvalido() throws Exception {
        // Arrange
        Map<String, Object> productoInvalido = new HashMap<>();
        productoInvalido.put("tipo", "TIPO_INEXISTENTE");
        productoInvalido.put("nombre", "Producto Test");
        productoInvalido.put("precio", 50.0);

        // Act & Assert
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Debería retornar 404 cuando se intenta actualizar un producto inexistente")
    void testActualizarProductoNoEncontrado() throws Exception {
        // Arrange
        Long productoId = 999L;
        when(productoService.buscarPorId(productoId)).thenReturn(Optional.empty());

        Map<String, Object> datosActualizacion = new HashMap<>();
        datosActualizacion.put("tipo", "REMERA");
        datosActualizacion.put("nombre", "Remera Actualizada");
        datosActualizacion.put("precio", 75.0);
        datosActualizacion.put("marca", "Nike");
        datosActualizacion.put("talle", 42);
        datosActualizacion.put("material", "Algodón");

        // Act & Assert
        mockMvc.perform(put("/api/productos/{id}", productoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(datosActualizacion)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)))
                .andExpect(jsonPath("$.message", containsString("no encontrado")));
    }

    @Test
    @DisplayName("Debería crear un producto REMERA válido sin errores")
    void testCrearProductoRemeraValido() throws Exception {
        // Arrange
        Remera remeraEsperada = new Remera();
        remeraEsperada.setId(1L);
        remeraEsperada.setNombre("Remera Test");
        remeraEsperada.setPrecio(50.0);
        remeraEsperada.setMarca("Nike");
        remeraEsperada.setTalle(42);
        remeraEsperada.setMaterial("Algodón");

        when(productoService.guardar(any())).thenReturn(remeraEsperada);

        Map<String, Object> productoValido = new HashMap<>();
        productoValido.put("tipo", "REMERA");
        productoValido.put("nombre", "Remera Test");
        productoValido.put("precio", 50.0);
        productoValido.put("marca", "Nike");
        productoValido.put("talle", 42);
        productoValido.put("material", "Algodón");

        // Act & Assert
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productoValido)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.nombre", is("Remera Test")))
                .andExpect(jsonPath("$.tipo", is("REMERA")));
    }

    @Test
    @DisplayName("Debería retornar la respuesta con timestamp y path en toda excepción")
    void testRespuestaExcepcionTieneTimestampYPath() throws Exception {
        // Arrange
        Long productoId = 1L;
        when(productoService.buscarPorId(productoId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/productos/{id}", productoId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", hasKey("timestamp")))
                .andExpect(jsonPath("$", hasKey("status")))
                .andExpect(jsonPath("$", hasKey("error")))
                .andExpect(jsonPath("$", hasKey("message")))
                .andExpect(jsonPath("$", hasKey("path")))
                .andExpect(jsonPath("$.path", containsString("/api/productos/1")));
    }
}

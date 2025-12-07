package com.ejemplo.articulos.exception;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("Producto con ID " + id + " no encontrado");
    }
    
    public ProductoNotFoundException(String message) {
        super(message);
    }
}

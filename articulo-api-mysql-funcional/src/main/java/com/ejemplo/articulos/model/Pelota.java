
package com.ejemplo.articulos.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("PELOTA")
public class Pelota extends Producto {

    private String deporte;
    private String tamanio;

    // Constructor vacío
    public Pelota() {
    }

    // Constructor con parámetros
    public Pelota(Long id, String nombre, Double precio, String deporte, String tamanio) {
        super(id, nombre, precio);
        this.deporte = deporte;
        this.tamanio = tamanio;
    }

    @Override
    public String getTipo() {
        return "PELOTA";
    }

    @Override
    public Double calcularPrecioFinal() {
        // Sin descuento en pelotas
        return getPrecio();
    }

    @Override
    public String getDetalleEspecifico() {
        return deporte + " - Tamaño " + tamanio;
    }

    // Getters y Setters
    public String getDeporte() {
        return deporte;
    }

    public void setDeporte(String deporte) {
        this.deporte = deporte;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }
}

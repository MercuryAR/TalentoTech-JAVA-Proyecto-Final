
package com.ejemplo.articulos.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("ZAPATILLA")
public class Zapatilla extends Producto {

    private String marca;
    private Integer numeroCalzado;
    private String tipoDeporte;

    // Constructor vacío
    public Zapatilla() {
    }

    // Constructor con parámetros
    public Zapatilla(Long id, String nombre, Double precio, String marca, Integer numeroCalzado, String tipoDeporte) {
        super(id, nombre, precio);
        this.marca = marca;
        this.numeroCalzado = numeroCalzado;
        this.tipoDeporte = tipoDeporte;
    }

    @Override
    public String getTipo() {
        return "ZAPATILLA";
    }

    @Override
    public Double calcularPrecioFinal() {
        // 15% de descuento en zapatillas
        return getPrecio() * 0.85;
    }

    @Override
    public String getDetalleEspecifico() {
        return marca + " - Nº " + numeroCalzado + (tipoDeporte != null ? " - " + tipoDeporte : "");
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getNumeroCalzado() {
        return numeroCalzado;
    }

    public void setNumeroCalzado(Integer numeroCalzado) {
        this.numeroCalzado = numeroCalzado;
    }

    public String getTipoDeporte() {
        return tipoDeporte;
    }

    public void setTipoDeporte(String tipoDeporte) {
        this.tipoDeporte = tipoDeporte;
    }
}

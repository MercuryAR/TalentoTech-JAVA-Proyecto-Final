
package com.ejemplo.articulos.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("REMERA")
public class Remera extends Producto {

    private String marca;
    private Integer talle;
    private String material;

    // Constructor vacío
    public Remera() {
    }

    // Constructor con parámetros
    public Remera(Long id, String nombre, Double precio, String marca, Integer talle, String material) {
        super(id, nombre, precio);
        this.marca = marca;
        this.talle = talle;
        this.material = material;
    }

    @Override
    public String getTipo() {
        return "REMERA";
    }

    @Override
    public Double calcularPrecioFinal() {
        // 10% de descuento en remeras
        return getPrecio() * 0.9;
    }

    @Override
    public String getDetalleEspecifico() {
        return marca + " - Talle " + talle + (material != null ? " - " + material : "");
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getTalle() {
        return talle;
    }

    public void setTalle(Integer talle) {
        this.talle = talle;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}

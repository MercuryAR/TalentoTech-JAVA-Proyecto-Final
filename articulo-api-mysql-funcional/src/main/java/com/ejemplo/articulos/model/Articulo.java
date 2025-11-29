
package com.ejemplo.articulos.model;

import jakarta.persistence.*;

// Indica que esta clase es una entidad JPA
@Entity
@Table(name = "productos") // Mapea a la tabla "productos"
public class Articulo {

    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
    private Long id;

    private String nombre;
    private String marca;
    private Integer talle;
    private Double precio;
    

    public Articulo() {}

    public Articulo(Long id, String nombre, String marca, Integer talle, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.talle = talle;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public Integer getTalle() { return talle; }
    public void setTalle(Integer talle) { this.talle = talle; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
 
}

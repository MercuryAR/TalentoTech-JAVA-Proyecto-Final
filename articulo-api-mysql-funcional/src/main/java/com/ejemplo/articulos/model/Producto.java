
package com.ejemplo.articulos.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_producto", discriminatorType = DiscriminatorType.STRING)
@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Remera.class, name = "REMERA"),
    @JsonSubTypes.Type(value = Zapatilla.class, name = "ZAPATILLA"),
    @JsonSubTypes.Type(value = Pelota.class, name = "PELOTA")
})
public abstract class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private Double precio;
    
    // Métodos abstractos para POLIMORFISMO
    public abstract String getTipo();
    public abstract Double calcularPrecioFinal();
    public abstract String getDetalleEspecifico();
    
    // Constructores
    public Producto() {}
    
    public Producto(Long id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}

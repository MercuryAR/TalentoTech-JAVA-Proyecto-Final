
package com.ejemplo.articulos.repository;

import com.ejemplo.articulos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    // JPA maneja el polimorfismo automáticamente
}

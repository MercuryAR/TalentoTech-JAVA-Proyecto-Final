
package com.ejemplo.articulos.service;

import com.ejemplo.articulos.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoService {
    List<Producto> listarTodos();
    Optional<Producto> buscarPorId(Long id);
    Producto guardar(Producto producto);
    void eliminar(Long id);
}

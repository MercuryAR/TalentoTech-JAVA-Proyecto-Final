
package com.ejemplo.articulos.service;

import com.ejemplo.articulos.model.Producto;
import com.ejemplo.articulos.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoServiceImpl implements ProductoService {
    
    private final ProductoRepository repository;
    
    public ProductoServiceImpl(ProductoRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public List<Producto> listarTodos() {
        return repository.findAll();
    }
    
    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id);
    }
    
    @Override
    public Producto guardar(Producto producto) {
        return repository.save(producto);
    }
    
    @Override
    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}

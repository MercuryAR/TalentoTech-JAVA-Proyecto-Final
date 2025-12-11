
package com.ejemplo.articulos.controller;

import com.ejemplo.articulos.exception.ProductoNotFoundException;
import com.ejemplo.articulos.model.*;
import com.ejemplo.articulos.service.ProductoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    
    private final ProductoService service;
    
    public ProductoController(ProductoService service) {
        this.service = service;
    }
    
    @GetMapping
    public List<Map<String, Object>> listar() {
        // POLIMORFISMO: cada producto calcula su precio de forma diferente
        return service.listarTodos().stream()
                .map(this::productoToMap)
                .collect(Collectors.toList());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> obtener(@PathVariable Long id) {
        Producto producto = service.buscarPorId(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        return ResponseEntity.ok(productoToMap(producto));
    }
    
    @PostMapping
    public ResponseEntity<Map<String, Object>> crear(@RequestBody Map<String, Object> datos) {
        try {
            Producto producto = mapToProducto(datos);
            Producto guardado = service.guardar(producto);
            return ResponseEntity.ok(productoToMap(guardado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> actualizar(@PathVariable Long id, @RequestBody Map<String, Object> datos) {
        Producto existente = service.buscarPorId(id)
                .orElseThrow(() -> new ProductoNotFoundException(id));
        
        try {
            Producto producto = mapToProducto(datos);
            producto.setId(id);
            Producto actualizado = service.guardar(producto);
            return ResponseEntity.ok(productoToMap(actualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().<Map<String, Object>>body(Map.of("error", e.getMessage()));
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
    
    // Método auxiliar para convertir Map a Producto
    private Producto mapToProducto(Map<String, Object> datos) {
        String tipo = (String) datos.get("tipo");
        String nombre = (String) datos.get("nombre");
        Double precio = Double.valueOf(datos.get("precio").toString());
        
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo de producto es requerido");
        }
        
        switch (tipo.toUpperCase()) {
            case "REMERA":
                Remera remera = new Remera();
                remera.setNombre(nombre);
                remera.setPrecio(precio);
                remera.setMarca((String) datos.get("marca"));
                remera.setTalle(datos.get("talle") != null ? 
                    Integer.valueOf(datos.get("talle").toString()) : null);
                remera.setMaterial((String) datos.get("material"));
                return remera;
                
            case "ZAPATILLA":
                Zapatilla zapatilla = new Zapatilla();
                zapatilla.setNombre(nombre);
                zapatilla.setPrecio(precio);
                zapatilla.setMarca((String) datos.get("marca"));
                zapatilla.setNumeroCalzado(datos.get("numeroCalzado") != null ? 
                    Integer.valueOf(datos.get("numeroCalzado").toString()) : null);
                zapatilla.setTipoDeporte((String) datos.get("tipoDeporte"));
                return zapatilla;
                
            case "PELOTA":
                Pelota pelota = new Pelota();
                pelota.setNombre(nombre);
                pelota.setPrecio(precio);
                pelota.setDeporte((String) datos.get("deporte"));
                pelota.setTamanio((String) datos.get("tamanio"));
                return pelota;
                
            default:
                throw new IllegalArgumentException("Tipo de producto no válido: " + tipo);
        }
    }
    
    // Método auxiliar para convertir Producto a Map
    private Map<String, Object> productoToMap(Producto p) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", p.getId());
        map.put("tipo", p.getTipo());
        map.put("nombre", p.getNombre());
        map.put("precio", p.getPrecio());
        map.put("precioFinal", p.calcularPrecioFinal());
        map.put("detalle", p.getDetalleEspecifico());
        
        // Agregar campos específicos según el tipo
        if (p instanceof Remera r) {
            map.put("marca", r.getMarca());
            map.put("talle", r.getTalle());
            map.put("material", r.getMaterial());
        } else if (p instanceof Zapatilla z) {
            map.put("marca", z.getMarca());
            map.put("numeroCalzado", z.getNumeroCalzado());
            map.put("tipoDeporte", z.getTipoDeporte());
        } else if (p instanceof Pelota pel) {
            map.put("deporte", pel.getDeporte());
            map.put("tamanio", pel.getTamanio());
        }
        
        return map;
    }
}

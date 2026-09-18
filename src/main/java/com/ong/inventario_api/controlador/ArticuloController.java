package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.Articulo;
import com.ong.inventario_api.repositorio.ArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articulos")
@CrossOrigin(origins = "*")
public class ArticuloController {

    @Autowired
    private ArticuloRepository articuloRepository;

    @GetMapping
    public List<Articulo> listar() {
        return articuloRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Articulo> obtener(@PathVariable Integer id) {
        return articuloRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Articulo crear(@RequestBody Articulo articulo) {
        return articuloRepository.save(articulo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Articulo> actualizar(@PathVariable Integer id, @RequestBody Articulo datos) {
        return articuloRepository.findById(id)
                .map(art -> {
                    art.setNombre(datos.getNombre());
                    art.setDescripcion(datos.getDescripcion());
                    art.setCantidad(datos.getCantidad());
                    art.setEstadoActual(datos.getEstadoActual());
                    // ... puedes agregar más campos
                    return ResponseEntity.ok(articuloRepository.save(art));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (articuloRepository.existsById(id)) {
            articuloRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
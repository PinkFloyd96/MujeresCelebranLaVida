package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.Ubicacion;
import com.ong.inventario_api.repositorio.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ubicaciones")
@CrossOrigin(origins = "*")
public class UbicacionController {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @GetMapping
    public List<Ubicacion> listar() {
        return ubicacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ubicacion> obtener(@PathVariable Integer id) {
        return ubicacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Ubicacion crear(@RequestBody Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ubicacion> actualizar(@PathVariable Integer id, @RequestBody Ubicacion datos) {
        return ubicacionRepository.findById(id)
                .map(ub -> {
                    ub.setNombre(datos.getNombre());
                    ub.setDescripcion(datos.getDescripcion());
                    return ResponseEntity.ok(ubicacionRepository.save(ub));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (ubicacionRepository.existsById(id)) {
            ubicacionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
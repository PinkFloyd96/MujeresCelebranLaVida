package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.Entidad;
import com.ong.inventario_api.repositorio.EntidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entidades")
@CrossOrigin(origins = "*")
public class EntidadController {

    @Autowired
    private EntidadRepository entidadRepository;

    @GetMapping
    public List<Entidad> listar() {
        return entidadRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entidad> obtener(@PathVariable Integer id) {
        return entidadRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Entidad crear(@RequestBody Entidad entidad) {
        return entidadRepository.save(entidad);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Entidad> actualizar(@PathVariable Integer id, @RequestBody Entidad datos) {
        return entidadRepository.findById(id)
                .map(e -> {
                    e.setTipo(datos.getTipo());
                    e.setNombreCompleto(datos.getNombreCompleto());
                    e.setDocumentoIdentidad(datos.getDocumentoIdentidad());
                    e.setTelefono(datos.getTelefono());
                    e.setEmail(datos.getEmail());
                    e.setDireccion(datos.getDireccion());
                    e.setObservaciones(datos.getObservaciones());
                    return ResponseEntity.ok(entidadRepository.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (entidadRepository.existsById(id)) {
            entidadRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.EntregaDefinitiva;
import com.ong.inventario_api.repositorio.EntregaDefinitivaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entregas")
@CrossOrigin(origins = "*")
public class EntregaDefinitivaController {

    @Autowired
    private EntregaDefinitivaRepository entregaRepository;

    @GetMapping
    public List<EntregaDefinitiva> listar() {
        return entregaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregaDefinitiva> obtener(@PathVariable Integer id) {
        return entregaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public EntregaDefinitiva crear(@RequestBody EntregaDefinitiva entrega) {
        return entregaRepository.save(entrega);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntregaDefinitiva> actualizar(@PathVariable Integer id, @RequestBody EntregaDefinitiva datos) {
        return entregaRepository.findById(id)
                .map(e -> {
                    e.setReceptor(datos.getReceptor());
                    e.setArticulo(datos.getArticulo());
                    e.setCantidad(datos.getCantidad());
                    e.setResponsable(datos.getResponsable());
                    e.setMotivoCampana(datos.getMotivoCampana());
                    e.setFechaEntrega(datos.getFechaEntrega());
                    e.setObservaciones(datos.getObservaciones());
                    return ResponseEntity.ok(entregaRepository.save(e));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (entregaRepository.existsById(id)) {
            entregaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
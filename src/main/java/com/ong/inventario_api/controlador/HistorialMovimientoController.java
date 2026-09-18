package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.HistorialMovimiento;
import com.ong.inventario_api.repositorio.HistorialMovimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial")
@CrossOrigin(origins = "*")
public class HistorialMovimientoController {

    @Autowired
    private HistorialMovimientoRepository historialRepository;

    @GetMapping
    public List<HistorialMovimiento> listar() {
        return historialRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HistorialMovimiento> obtener(@PathVariable Integer id) {
        return historialRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HistorialMovimiento crear(@RequestBody HistorialMovimiento movimiento) {
        return historialRepository.save(movimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HistorialMovimiento> actualizar(@PathVariable Integer id, @RequestBody HistorialMovimiento datos) {
        return historialRepository.findById(id)
                .map(h -> {
                    h.setArticulo(datos.getArticulo());
                    h.setUsuario(datos.getUsuario());
                    h.setTipoOperacion(datos.getTipoOperacion());
                    h.setEstadoAnterior(datos.getEstadoAnterior());
                    h.setEstadoNuevo(datos.getEstadoNuevo());
                    h.setDescripcionMovimiento(datos.getDescripcionMovimiento());
                    h.setFechaRegistro(datos.getFechaRegistro());
                    return ResponseEntity.ok(historialRepository.save(h));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (historialRepository.existsById(id)) {
            historialRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.Prestamo;
import com.ong.inventario_api.repositorio.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @GetMapping
    public List<Prestamo> listar() {
        return prestamoRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> obtener(@PathVariable Integer id) {
        return prestamoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prestamo crear(@RequestBody Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prestamo> actualizar(@PathVariable Integer id, @RequestBody Prestamo datos) {
        return prestamoRepository.findById(id)
                .map(p -> {
                    p.setReceptor(datos.getReceptor());
                    p.setArticulo(datos.getArticulo());
                    p.setCantidad(datos.getCantidad());
                    p.setResponsable(datos.getResponsable());
                    p.setFechaPrestamo(datos.getFechaPrestamo());
                    p.setFechaPrevistaDevolucion(datos.getFechaPrevistaDevolucion());
                    p.setFechaRealDevolucion(datos.getFechaRealDevolucion());
                    p.setEstadoDevolucion(datos.getEstadoDevolucion());
                    p.setResponsableRecepcionDevolucion(datos.getResponsableRecepcionDevolucion());
                    p.setObservaciones(datos.getObservaciones());
                    return ResponseEntity.ok(prestamoRepository.save(p));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (prestamoRepository.existsById(id)) {
            prestamoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
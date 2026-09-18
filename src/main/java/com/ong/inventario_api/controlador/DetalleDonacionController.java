package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.DetalleDonacion;
import com.ong.inventario_api.repositorio.DetalleDonacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-donaciones")
@CrossOrigin(origins = "*")
public class DetalleDonacionController {

    @Autowired
    private DetalleDonacionRepository detalleRepository;

    @GetMapping
    public List<DetalleDonacion> listar() {
        return detalleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleDonacion> obtener(@PathVariable Integer id) {
        return detalleRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DetalleDonacion crear(@RequestBody DetalleDonacion detalle) {
        return detalleRepository.save(detalle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleDonacion> actualizar(@PathVariable Integer id, @RequestBody DetalleDonacion datos) {
        return detalleRepository.findById(id)
                .map(d -> {
                    d.setDonacion(datos.getDonacion());
                    d.setArticulo(datos.getArticulo());
                    d.setCantidad(datos.getCantidad());
                    d.setEstadoConservacionInicial(datos.getEstadoConservacionInicial());
                    return ResponseEntity.ok(detalleRepository.save(d));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (detalleRepository.existsById(id)) {
            detalleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
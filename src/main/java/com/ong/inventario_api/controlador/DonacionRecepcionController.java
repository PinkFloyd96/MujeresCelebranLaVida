package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.DonacionRecepcion;
import com.ong.inventario_api.repositorio.DonacionRecepcionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donaciones")
@CrossOrigin(origins = "*")
public class DonacionRecepcionController {

    @Autowired
    private DonacionRecepcionRepository donacionRepository;

    @GetMapping
    public List<DonacionRecepcion> listar() {
        return donacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonacionRecepcion> obtener(@PathVariable Integer id) {
        return donacionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DonacionRecepcion crear(@RequestBody DonacionRecepcion donacion) {
        return donacionRepository.save(donacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonacionRecepcion> actualizar(@PathVariable Integer id, @RequestBody DonacionRecepcion datos) {
        return donacionRepository.findById(id)
                .map(d -> {
                    d.setDonante(datos.getDonante());
                    d.setResponsableRecepcion(datos.getResponsableRecepcion());
                    d.setFechaRecepcion(datos.getFechaRecepcion());
                    d.setObservaciones(datos.getObservaciones());
                    return ResponseEntity.ok(donacionRepository.save(d));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (donacionRepository.existsById(id)) {
            donacionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
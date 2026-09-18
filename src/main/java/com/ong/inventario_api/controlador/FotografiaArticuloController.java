package com.ong.inventario_api.controlador;

import com.ong.inventario_api.modelo.FotografiaArticulo;
import com.ong.inventario_api.repositorio.FotografiaArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fotografias")
@CrossOrigin(origins = "*")
public class FotografiaArticuloController {

    @Autowired
    private FotografiaArticuloRepository fotografiaRepository;

    @GetMapping
    public List<FotografiaArticulo> listar() {
        return fotografiaRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FotografiaArticulo> obtener(@PathVariable Integer id) {
        return fotografiaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public FotografiaArticulo crear(@RequestBody FotografiaArticulo fotografia) {
        return fotografiaRepository.save(fotografia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FotografiaArticulo> actualizar(@PathVariable Integer id, @RequestBody FotografiaArticulo datos) {
        return fotografiaRepository.findById(id)
                .map(f -> {
                    f.setArticulo(datos.getArticulo());
                    f.setUrlFotografia(datos.getUrlFotografia());
                    f.setEsPrincipal(datos.getEsPrincipal());
                    return ResponseEntity.ok(fotografiaRepository.save(f));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Integer id) {
        if (fotografiaRepository.existsById(id)) {
            fotografiaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
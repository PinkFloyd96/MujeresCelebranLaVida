package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.Articulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer> {
    // Spring ya te da: findAll(), findById(), save(), deleteById()
    // No hace falta escribir nada más.
}
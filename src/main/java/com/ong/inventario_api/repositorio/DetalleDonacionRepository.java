package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.DetalleDonacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleDonacionRepository extends JpaRepository<DetalleDonacion, Integer> {
}
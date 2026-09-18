package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.HistorialMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialMovimientoRepository extends JpaRepository<HistorialMovimiento, Integer> {
}
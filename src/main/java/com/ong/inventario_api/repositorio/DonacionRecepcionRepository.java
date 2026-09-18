package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.DonacionRecepcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonacionRecepcionRepository extends JpaRepository<DonacionRecepcion, Integer> {
}
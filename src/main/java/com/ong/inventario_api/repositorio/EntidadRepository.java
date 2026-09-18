package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntidadRepository extends JpaRepository<Entidad, Integer> {
}
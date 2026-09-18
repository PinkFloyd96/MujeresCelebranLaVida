package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.EntregaDefinitiva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntregaDefinitivaRepository extends JpaRepository<EntregaDefinitiva, Integer> {
}
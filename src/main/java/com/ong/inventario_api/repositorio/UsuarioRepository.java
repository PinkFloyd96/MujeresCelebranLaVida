package com.ong.inventario_api.repositorio;

import com.ong.inventario_api.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Aquí podrías agregar búsquedas personalizadas después, como:
    // Usuario findByEmail(String email);
}
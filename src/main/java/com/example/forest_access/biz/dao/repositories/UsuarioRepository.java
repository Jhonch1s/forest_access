package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /*
     * Busca un usuario por su nombre de usuario.
     * Útil para el proceso de login y validación de tokens.
     */
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.repositories.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {



    private UsuarioRepository repo;

    @Transactional(readOnly = true)
    public Usuario findById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Transactional
    public List<Usuario> mostrarUsuarios() {
        return repo.findAll();
    }

    @Transactional
    public Usuario createUsuario(Usuario usuario) {
        return repo.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(Integer id,Usuario usuario) {
        Usuario existente =  findById(id);

        existente.setNombreUsuario(usuario.getNombreUsuario());
        existente.setPassword(usuario.getPassword());
        return repo.save(existente);
    }

    @Transactional
    public Usuario deleteUsuario(Integer id) {
        Usuario existente = findById(id);
        Usuario mostrar = new Usuario();
        BeanUtils.copyProperties(existente, mostrar);
        repo.delete(existente);
        return mostrar;
    }


}

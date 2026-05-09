package com.example.forest_access.biz.dao.services;

import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.repositories.UsuarioRepository;
import com.example.forest_access.dto.UsuarioDTO;
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
    public List<UsuarioDTO> mostrarUsuarios() {

        return repo.findAll().stream().map(u ->{
            UsuarioDTO dto = new UsuarioDTO();
            BeanUtils.copyProperties(u, dto);
            return dto;
        }).toList();
    }

    @Transactional
    public Usuario createUsuario(UsuarioDTO usuario) {
        Usuario usu = new Usuario();
        usu.setNombreUsuario(usuario.getNombreUsuario());
        usu.setPassword(usuario.getPassword());
        return repo.save(usu);
    }

    @Transactional
    public UsuarioDTO updateUsuario(Integer id,UsuarioDTO usuario) {
        Usuario existente =  findById(id);
        existente.setNombreUsuario(usuario.getNombreUsuario());
        existente.setPassword(usuario.getPassword());
        repo.save(existente);
        return usuario;
    }

    @Transactional
    public UsuarioDTO deleteUsuario(Integer id) {
        Usuario existente = findById(id);
        UsuarioDTO mostrar = new UsuarioDTO();
        BeanUtils.copyProperties(existente, mostrar);
        repo.delete(existente);
        return mostrar;
    }


}

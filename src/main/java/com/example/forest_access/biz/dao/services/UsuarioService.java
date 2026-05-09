package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.UsuarioResponse;
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
    public List<UsuarioResponse> mostrarUsuarios() {
        return repo.findAll().stream().map(u ->{
            UsuarioResponse dto = new UsuarioResponse();
            BeanUtils.copyProperties(u, dto);
            return dto;
        }).toList();
    }

    @Transactional
    public UsuarioResponse createUsuario(UsuarioDTO usuario) {
        Usuario usu = new Usuario();
        usu.setNombreUsuario(usuario.getNombreUsuario());
        usu.setPassword(usuario.getPassword());

        UsuarioResponse usu1= new UsuarioResponse();
        usu1.setId(usu.getId());
        usu1.setNombreUsuario(usuario.getNombreUsuario());
        repo.save(usu);
        return usu1;
    }

    @Transactional
    public UsuarioResponse updateUsuario(Integer id,UsuarioDTO usuario) {
        Usuario existente =  findById(id);
        UsuarioResponse response = new UsuarioResponse();

        response.setNombreUsuario(usuario.getNombreUsuario());
        response.setId(existente.getId());

        existente.setNombreUsuario(usuario.getNombreUsuario());
        existente.setPassword(usuario.getPassword());

        repo.save(existente);

        return response;
    }

    @Transactional
    public void deleteUsuario(Integer id) {
        Usuario existente = findById(id);
        repo.delete(existente);
        return;
    }


}

package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.PerfilResponse;
import com.example.forest_access.api.controllers.response.PunteroUsuarioResponse;
import com.example.forest_access.api.controllers.response.UsuarioResponse;
import com.example.forest_access.biz.dao.entities.Campo;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Perfil;
import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.PerfilRepository;
import com.example.forest_access.biz.dao.repositories.UsuarioRepository;
import com.example.forest_access.dto.PunteroUsuarioRequest;
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
    private EmpleadoRepository empleadoRepository;
    private PerfilRepository perfilRepository;

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

    /* ── Puntero-specific methods ── */

    @Transactional(readOnly = true)
    public List<PunteroUsuarioResponse> mostrarPunteroUsuarios() {
        Perfil perfilPuntero = perfilRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Perfil PUNTERO (id=2) no encontrado"));

        return repo.findAll().stream()
                .filter(u -> u.getPerfiles() != null && u.getPerfiles().contains(perfilPuntero))
                .map(this::toPunteroResponse)
                .toList();
    }

    @Transactional
    public PunteroUsuarioResponse createPunteroUsuario(PunteroUsuarioRequest request) {
        Usuario usu = new Usuario();
        usu.setNombreUsuario(request.getNombreUsuario());
        usu.setPassword(request.getPassword());

        if (request.getIdEmpleado() != null) {
            Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                    .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getIdEmpleado()));
            usu.setEmpleado(empleado);
        }

        Perfil perfilPuntero = perfilRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException("Perfil PUNTERO (id=2) no encontrado"));
        usu.setPerfiles(List.of(perfilPuntero));

        usu = repo.save(usu);
        return toPunteroResponse(usu);
    }

    @Transactional
    public PunteroUsuarioResponse updatePunteroUsuario(Integer id, PunteroUsuarioRequest request) {
        Usuario existente = findById(id);

        existente.setNombreUsuario(request.getNombreUsuario());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            existente.setPassword(request.getPassword());
        }

        if (request.getIdEmpleado() != null) {
            Empleado empleado = empleadoRepository.findById(request.getIdEmpleado())
                    .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + request.getIdEmpleado()));
            existente.setEmpleado(empleado);
        }

        existente = repo.save(existente);
        return toPunteroResponse(existente);
    }

    @Transactional
    public void deletePunteroUsuario(Integer id) {
        Usuario existente = findById(id);
        repo.delete(existente);
    }

    private PunteroUsuarioResponse toPunteroResponse(Usuario usuario) {
        PunteroUsuarioResponse res = new PunteroUsuarioResponse();
        res.setId(usuario.getId());
        res.setNombreUsuario(usuario.getNombreUsuario());

        if (usuario.getPerfiles() != null) {
            List<PerfilResponse> perfilResponses = usuario.getPerfiles().stream()
                    .map(p -> {
                        PerfilResponse pr = new PerfilResponse();
                        pr.setId(p.getId());
                        pr.setNombre(p.getNombre());
                        return pr;
                    }).toList();
            res.setPerfiles(perfilResponses);
        }

        if (usuario.getEmpleado() != null) {
            res.setIdEmpleado(usuario.getEmpleado().getIdEmpleado());
            res.setNombreEmpleado(usuario.getEmpleado().getNombre());
        }

        return res;
    }
}

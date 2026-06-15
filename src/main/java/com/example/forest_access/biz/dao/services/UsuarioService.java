package com.example.forest_access.biz.dao.services;

import com.example.forest_access.api.controllers.response.PerfilResponse;
import com.example.forest_access.api.controllers.response.PunteroUsuarioResponse;
import com.example.forest_access.api.controllers.response.UsuarioResponse;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.entities.Perfil;
import com.example.forest_access.biz.dao.entities.Usuario;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.PerfilRepository;
import com.example.forest_access.biz.dao.repositories.UsuarioRepository;
import com.example.forest_access.dto.PunteroUsuarioRequest;
import com.example.forest_access.dto.UsuarioDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository repo;
    private final EmpleadoRepository empleadoRepository;
    private final PerfilRepository perfilRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository repo,
                          EmpleadoRepository empleadoRepository,
                          PerfilRepository perfilRepository,
                          PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.empleadoRepository = empleadoRepository;
        this.perfilRepository = perfilRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Usuario findById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + id));
    }

    @Transactional
    public List<UsuarioResponse> mostrarUsuarios() {
        return repo.findAll().stream().map(u -> {
            UsuarioResponse dto = new UsuarioResponse();
            dto.setId(u.getId());
            dto.setNombreUsuario(u.getNombreUsuario());
            return dto;
        }).toList();
    }

    @Transactional
    public UsuarioResponse createUsuario(UsuarioDTO usuario) {
        Usuario usu = new Usuario();
        usu.setNombreUsuario(usuario.getNombreUsuario());
        usu.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario saved = repo.save(usu);

        UsuarioResponse usu1 = new UsuarioResponse();
        usu1.setId(saved.getId());
        usu1.setNombreUsuario(saved.getNombreUsuario());
        return usu1;
    }

    @Transactional
    public UsuarioResponse updateUsuario(Integer id, UsuarioDTO usuario) {
        Usuario existente = findById(id);

        existente.setNombreUsuario(usuario.getNombreUsuario());
        existente.setPassword(passwordEncoder.encode(usuario.getPassword()));

        Usuario saved = repo.save(existente);

        UsuarioResponse response = new UsuarioResponse();
        response.setId(saved.getId());
        response.setNombreUsuario(saved.getNombreUsuario());
        return response;
    }

    @Transactional
    public void deleteUsuario(Integer id) {
        Usuario existente = findById(id);
        repo.delete(existente);
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
        usu.setPassword(passwordEncoder.encode(request.getPassword()));

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
            existente.setPassword(passwordEncoder.encode(request.getPassword()));
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

    /* ── Password change for own account ── */

    @Transactional
    public PunteroUsuarioResponse cambiarPasswordPropio(Integer idUsuario, String currentPassword, String nuevaPassword) {
        Usuario existente = findById(idUsuario);

        if (!passwordEncoder.matches(currentPassword, existente.getPassword())) {
            throw new IllegalArgumentException("La contraseña actual es incorrecta");
        }

        existente.setPassword(passwordEncoder.encode(nuevaPassword));
        existente = repo.save(existente);
        return toPunteroResponse(existente);
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

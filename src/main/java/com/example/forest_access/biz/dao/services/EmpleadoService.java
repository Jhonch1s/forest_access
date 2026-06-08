package com.example.forest_access.biz.dao.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.forest_access.api.controllers.response.EmpleadoResponse;
import com.example.forest_access.api.controllers.response.PaginadoEmpleado;
import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import com.example.forest_access.biz.dao.repositories.CategoriaEmpleadoRepository;
import com.example.forest_access.biz.dao.repositories.EmpleadoRepository;
import com.example.forest_access.dto.EmpleadoDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class EmpleadoService {

    private final String cloud_name = System.getenv("CLOUD_NAME");
    private final String api_key = System.getenv("API_KEY");
    private final String api_secret = System.getenv("API_SECRET");
    private final EmpleadoRepository repository;
    private final CategoriaEmpleadoRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<EmpleadoResponse> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PaginadoEmpleado obtenerEmpleadosPaginados(Integer offset, Integer limite,Boolean filtro){
        PaginadoEmpleado pe = new PaginadoEmpleado();
        Integer total = repository.findAll().size();
        pe.setTotal(total);
        pe.setPagina(offset);
        pe.setLimite(limite);
        List<Object[]> resultados = repository.findAllEmpleadosWithDiasRestantes();
        Stream<EmpleadoResponse> stream = resultados.stream()
                .map( row ->{
            EmpleadoResponse response = new EmpleadoResponse();

            // En lugar de (Integer) row[0], usa Number
            response.setIdEmpleado(((Number) row[0]).intValue());
            response.setNombre((String) row[1]);
            response.setCedula((String) row[2]);
            response.setEmail((String) row[3]);
            response.setTelefono((String) row[4]);

            // Activo: puede ser Boolean o Integer, pero también podría ser Long (0/1)
            Object activoObj = row[5];
            if (activoObj instanceof Boolean) {
                response.setActivo((Boolean) activoObj);
            } else if (activoObj instanceof Number) {
                response.setActivo(((Number) activoObj).intValue() == 1);
            }

            // Fecha ingreso
            java.sql.Date sqlDate = (java.sql.Date) row[6];
            response.setFechaIngreso(sqlDate.toLocalDate());

            // id_categoria: puede ser null o Long
            response.setIdCategoria(row[7] != null ? ((Number) row[7]).intValue() : null);
            response.setNombreCategoria((String) row[8]);

            // dias_restantes: puede ser null o Long
            response.setDiasRestantes(row[9] != null ? ((Number) row[9]).intValue() : null);

            return response;
        });
                if(filtro != null && filtro ){
                    stream = stream.sorted(
                            Comparator.comparing(EmpleadoResponse::getDiasRestantes,
                                            Comparator.nullsLast(Comparator.reverseOrder()))
                                    .thenComparing(EmpleadoResponse::getIdEmpleado)
                    );
                }
        List<EmpleadoResponse> empleados1 = stream
                .skip(offset)
                .limit(limite)
                .collect(Collectors.toList());



        pe.setEmpleados(empleados1);
        return pe;
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse findById(Integer id) {
        Empleado empleado = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id: " + id));
        return mapToResponse(empleado);
    }

    // AHORA USA EL DTO GENERAL
    @Transactional
    public EmpleadoResponse create(EmpleadoDTO dto) {
        if (repository.findByCedula(dto.getCedula()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un empleado con la cédula: " + dto.getCedula());
        }

        Empleado nuevo = new Empleado();
        updateEntityFromDTO(nuevo, dto);

        if(dto.getImagenUrl() != null){
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", cloud_name,
                    "api_key", api_key,
                    "api_secret", api_secret,
                    "secure", true));

            Map params = ObjectUtils.asMap(
                    "public_id", "empleado",
                    "overwrite", false,
                    "notification_url", "https://mysite.com/notify_endpoint",
                    "resource_type", "image"
            );
            try{
                Map uploadResult = cloudinary.uploader().upload(new File("doc.mp4"), params);
            }catch(Exception e){
                throw new RuntimeException(e);
            }

        }

        return mapToResponse(repository.save(nuevo));
    }

    // AHORA USA EL DTO GENERAL
    @Transactional
    public EmpleadoResponse update(Integer id, EmpleadoDTO dto) {
        Empleado existente = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        if (!existente.getCedula().equals(dto.getCedula())) {
            if (repository.findByCedula(dto.getCedula()).isPresent()) {
                throw new IllegalArgumentException("La nueva cédula ya está registrada.");
            }
        }

        updateEntityFromDTO(existente, dto);
        return mapToResponse(repository.save(existente));
    }

    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("No se puede eliminar: Empleado no encontrado");
        }
        repository.deleteById(id);
    }

    // MAPPER: De DTO a Entidad
    private void updateEntityFromDTO(Empleado entidad, EmpleadoDTO dto) {
        entidad.setNombre(dto.getNombre());
        entidad.setCedula(dto.getCedula());
        entidad.setTelefono(dto.getTelefono());
        entidad.setEmail(dto.getEmail());
        entidad.setFechaIngreso(dto.getFechaIngreso());
        entidad.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        // Como el DTO tiene un CategoriaEmpleadoDTO anidado, sacamos el ID de ahí
        if (dto.getIdCategoria() != null) {
            CategoriaEmpleado cat = categoriaRepository.findById(dto.getIdCategoria())
                    .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
            entidad.setCategoria(cat);
        } else {
            entidad.setCategoria(null);
        }
    }

    // MAPPER: De Entidad a Response
    private EmpleadoResponse mapToResponse(Empleado entidad) {
        EmpleadoResponse res = new EmpleadoResponse();
        res.setIdEmpleado(entidad.getIdEmpleado());
        res.setNombre(entidad.getNombre());
        res.setCedula(entidad.getCedula());
        res.setTelefono(entidad.getTelefono());
        res.setEmail(entidad.getEmail());
        res.setFechaIngreso(entidad.getFechaIngreso());
        res.setActivo(entidad.getActivo());

        if (entidad.getCategoria() != null) {
            res.setIdCategoria(entidad.getCategoria().getIdCategoria());
            res.setNombreCategoria(entidad.getCategoria().getNombre());
        }
        return res;
    }

    public List<EmpleadoResponse> getAllEmpleadosConDias(){
        List<Object[]> resultados = repository.findAllEmpleadosWithDiasRestantes();
        return resultados.stream().map( row ->{
            EmpleadoResponse response = new EmpleadoResponse();

            // En lugar de (Integer) row[0], usa Number
            response.setIdEmpleado(((Number) row[0]).intValue());
            response.setNombre((String) row[1]);
            response.setCedula((String) row[2]);
            response.setEmail((String) row[3]);
            response.setTelefono((String) row[4]);

            // Activo: puede ser Boolean o Integer, pero también podría ser Long (0/1)
            Object activoObj = row[5];
            if (activoObj instanceof Boolean) {
                response.setActivo((Boolean) activoObj);
            } else if (activoObj instanceof Number) {
                response.setActivo(((Number) activoObj).intValue() == 1);
            }

            // Fecha ingreso
            java.sql.Date sqlDate = (java.sql.Date) row[6];
            response.setFechaIngreso(sqlDate.toLocalDate());

            // id_categoria: puede ser null o Long
            response.setIdCategoria(row[7] != null ? ((Number) row[7]).intValue() : null);
            response.setNombreCategoria((String) row[8]);

            // dias_restantes: puede ser null o Long
            response.setDiasRestantes(row[9] != null ? ((Number) row[9]).intValue() : null);

            return response;
        }).collect(Collectors.toList());
    }
}
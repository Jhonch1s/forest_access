package com.example.forest_access.biz.dao.repositories;

import com.example.forest_access.biz.dao.entities.CategoriaEmpleado;
import com.example.forest_access.biz.dao.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Optional<Empleado> findByCedula(String cedula);
    Optional<Empleado> findByEmail(String email);
    List<Empleado> findByActivo(Boolean activo);
    List<Empleado> findByCategoria(CategoriaEmpleado categoria);

    @Query(value = "SELECT e.id_empleado, e.nombre, e.cedula, e.email, e.telefono, e.activo, e.fecha_ingreso, e.id_categoria,c.nombre, " +
            "MIN(DATEDIFF(eh.fecha_vencimiento, CURDATE())) AS dias_restantes " +
            "FROM empleado e " +
            "LEFT JOIN categoria_empleado c ON e.id_categoria = c.id_categoria "+
            "LEFT JOIN empleado_habilitacion eh ON e.id_empleado = eh.id_empleado " +
            "GROUP BY e.id_empleado", nativeQuery = true)
    List<Object[]> findAllEmpleadosWithDiasRestantes();
}
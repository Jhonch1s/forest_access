package com.example.forest_access.configurations;

import com.example.forest_access.biz.dao.entities.Producto;
import com.example.forest_access.biz.dao.entities.ProductoTratamiento;
import com.example.forest_access.biz.dao.entities.Tratamiento;
import com.example.forest_access.biz.dao.repositories.ProductoRepository;
import com.example.forest_access.biz.dao.repositories.ProductoTratamientoRepository;
import com.example.forest_access.biz.dao.repositories.TratamientoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final ProductoRepository productoRepository;
    private final TratamientoRepository tratamientoRepository;
    private final ProductoTratamientoRepository productoTratamientoRepository;

    public DataInitializer(ProductoRepository productoRepository,
                           TratamientoRepository tratamientoRepository,
                           ProductoTratamientoRepository productoTratamientoRepository) {
        this.productoRepository = productoRepository;
        this.tratamientoRepository = tratamientoRepository;
        this.productoTratamientoRepository = productoTratamientoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() > 0 || tratamientoRepository.count() > 0) {
            System.out.println("Base de datos ya contiene información. Seeder omitido.");
            return;
        }

        System.out.println("🚀 Iniciando seeding de datos para control de deforestación...");

        // ========== 1. CREAR PRODUCTOS (10 productos) ==========
        List<Producto> productos = new ArrayList<>();

        // Herbicidas
        productos.add(createProducto("Glifosato 48%", "Herbicida sistémico", "48%", "litro"));
        productos.add(createProducto("2,4-D Amina", "Herbicida hormonal", "72%", "litro"));
        productos.add(createProducto("Picloram", "Herbicida para control de árboles", "24%", "litro"));
        productos.add(createProducto("Triclopir", "Herbicida para maleza leñosa", "60%", "litro"));

        // Insecticidas
        productos.add(createProducto("Lambdacialotrina", "Insecticida piretroide", "25%", "litro"));
        productos.add(createProducto("Imidacloprid", "Insecticida sistémico", "35%", "kg"));

        // Adyuvantes y surfactantes
        productos.add(createProducto("Surfactante No Iónico", "Mejorador de cobertura", "100%", "litro"));
        productos.add(createProducto("Aceite Vegetal", "Adyuvante penetrante", "85%", "litro"));

        // Fertilizantes y otros
        productos.add(createProducto("Urea", "Fertilizante nitrogenado", "46%", "kg"));
        productos.add(createProducto("Cal Agrícola", "Corrector de pH", "98%", "kg"));

        productoRepository.saveAll(productos);
        System.out.println("✅ Creados {} productos "+ productos.size());

        // ========== 2. CREAR TRATAMIENTOS (5 tratamientos) ==========
        List<Tratamiento> tratamientos = new ArrayList<>();

        tratamientos.add(createTratamiento(
                "Control Químico Pre-Corta",
                "Aplicación terrestre de herbicidas 30 días antes de la tala para eliminar vegetación competidora"
        ));

        tratamientos.add(createTratamiento(
                "Manejo de Malezas Post-Corta",
                "Control de rebrotes y maleza herbácea después de la cosecha forestal"
        ));

        tratamientos.add(createTratamiento(
                "Control de Plagas Forestales",
                "Aplicación focalizada para control de insectos descortezadores y defoliadores"
        ));

        tratamientos.add(createTratamiento(
                "Preparación de Suelo para Reforestación",
                "Tratamiento integral de suelo incluyendo fertilización y enmiendas"
        ));

        tratamientos.add(createTratamiento(
                "Mantenimiento de Áreas de Acceso",
                "Control de vegetación en caminos, patios de acopio y áreas de maniobra"
        ));

        tratamientoRepository.saveAll(tratamientos);
        System.out.println("✅ Creados {} tratamientos "+ tratamientos.size());

        // ========== 3. CREAR PRODUCTO_TRATAMIENTOS (20 relaciones dosis) ==========
        List<ProductoTratamiento> relaciones = new ArrayList<>();

        // Relación: Tratamiento 1 (Control Químico Pre-Corta) con varios herbicidas
        relaciones.add(createRelacion(tratamientos.get(0), productos.get(0), new BigDecimal("3.5"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(0), productos.get(1), new BigDecimal("2.0"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(0), productos.get(2), new BigDecimal("1.5"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(0), productos.get(6), new BigDecimal("0.5"), "litro/ha")); // surfactante

        // Relación: Tratamiento 2 (Manejo de Malezas Post-Corta)
        relaciones.add(createRelacion(tratamientos.get(1), productos.get(0), new BigDecimal("2.5"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(1), productos.get(3), new BigDecimal("1.8"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(1), productos.get(7), new BigDecimal("1.0"), "litro/ha")); // aceite vegetal

        // Relación: Tratamiento 3 (Control de Plagas Forestales)
        relaciones.add(createRelacion(tratamientos.get(2), productos.get(4), new BigDecimal("0.3"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(2), productos.get(5), new BigDecimal("0.2"), "kg/ha"));
        relaciones.add(createRelacion(tratamientos.get(2), productos.get(6), new BigDecimal("0.2"), "litro/ha"));

        // Relación: Tratamiento 4 (Preparación de Suelo)
        relaciones.add(createRelacion(tratamientos.get(3), productos.get(0), new BigDecimal("1.5"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(3), productos.get(8), new BigDecimal("120.0"), "kg/ha"));
        relaciones.add(createRelacion(tratamientos.get(3), productos.get(9), new BigDecimal("500.0"), "kg/ha"));

        // Relación: Tratamiento 5 (Mantenimiento de Áreas de Acceso)
        relaciones.add(createRelacion(tratamientos.get(4), productos.get(0), new BigDecimal("4.0"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(4), productos.get(1), new BigDecimal("2.5"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(4), productos.get(2), new BigDecimal("2.0"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(4), productos.get(4), new BigDecimal("0.4"), "litro/ha"));

        // Relaciones adicionales para completar 20
        relaciones.add(createRelacion(tratamientos.get(0), productos.get(4), new BigDecimal("0.25"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(2), productos.get(7), new BigDecimal("0.8"), "litro/ha"));
        relaciones.add(createRelacion(tratamientos.get(3), productos.get(6), new BigDecimal("0.3"), "litro/ha"));

        productoTratamientoRepository.saveAll(relaciones);
        System.out.println("✅ Creadas {} relaciones producto-tratamiento "+relaciones.size());

        // ========== RESUMEN FINAL ==========
        System.out.println("🎉 Seeding completado exitosamente!");
        System.out.println("📊 Resumen:");
        System.out.println("   - {} productos registrados"+ productos.size());
        System.out.println("   - {} tratamientos registrados"+ tratamientos.size());
        System.out.println("   - {} dosis asignadas"+ relaciones.size());

    }

    private Producto createProducto(String nombre, String contenido, String concentracion, String unidadBase) {
        Producto producto = new Producto();
        producto.setNombre(nombre);
        producto.setContenido(contenido);
        producto.setConcentracion(concentracion);
        producto.setUnidadBase(unidadBase);
        return producto;
    }

    private Tratamiento createTratamiento(String nombre, String descripcion) {
        Tratamiento tratamiento = new Tratamiento();
        tratamiento.setNombre(nombre);
        tratamiento.setDescripcion(descripcion);
        return tratamiento;
    }

    private ProductoTratamiento createRelacion(Tratamiento tratamiento, Producto producto, BigDecimal dosis, String unidad) {
        ProductoTratamiento relacion = new ProductoTratamiento();
        relacion.setTratamiento(tratamiento);
        relacion.setProducto(producto);
        relacion.setDosis(dosis);
        relacion.setUnidad(unidad);
        return relacion;
    }

}

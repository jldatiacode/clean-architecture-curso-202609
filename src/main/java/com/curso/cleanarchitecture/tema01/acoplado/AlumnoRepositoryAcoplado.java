package com.curso.cleanarchitecture.tema01.acoplado;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema01.modelo.AlumnoSimple;

/**
 * Repositorio de alumnos SIMULADO: "tabla ALUMNOS" en un mapa estático en
 * memoria, sin base de datos real.
 *
 * <p><b>Capa que representa:</b> persistencia (acceso a datos).</p>
 *
 * <p><b>Qué enseña:</b> junto con los otros dos repositorios acoplados,
 * evidencia que el controlador del tema necesita conocer TRES clases de
 * persistencia distintas para completar una sola operación de negocio.
 * Cada dependencia directa es un hilo más del acoplamiento: si mañana los
 * alumnos se consultan en un servicio externo en lugar de una tabla, el
 * controlador tendría que cambiar, cuando su trabajo es solo atender HTTP.</p>
 *
 * <p><b>Dependencias:</b> el modelo anémico del tema y Spring
 * ({@code @Repository}).</p>
 *
 * <p><b>Nota docente:</b> clase anotada solo como material de lectura; la
 * aplicación arrancable únicamente escanea
 * {@code com.curso.cleanarchitecture.proyecto}, por lo que este bean nunca
 * existe en tiempo de ejecución.</p>
 */
@Repository
public class AlumnoRepositoryAcoplado {

    // "Tabla ALUMNOS" compartida en memoria, en el papel de la base de datos.
    private static final Map<Long, AlumnoSimple> TABLA_ALUMNOS = new HashMap<>();

    static {
        // Alumnos de ejemplo. "Ana" viene del test de la sección 26 de la teoría.
        TABLA_ALUMNOS.put(1L, new AlumnoSimple(1L, "Ana", "ana@example.com"));
        TABLA_ALUMNOS.put(2L, new AlumnoSimple(2L, "Luis", "luis@example.com"));
    }

    /**
     * Busca un alumno por su identificador.
     *
     * <p>Imagina aquí una consulta SQL/JDBC:
     * {@code SELECT id, nombre, email FROM alumnos WHERE id = ?}.</p>
     */
    public Optional<AlumnoSimple> findById(Long alumnoId) {
        return Optional.ofNullable(TABLA_ALUMNOS.get(alumnoId));
    }

    /**
     * Guarda (inserta o actualiza) un alumno.
     *
     * <p>Imagina aquí un {@code INSERT INTO alumnos ...} o su
     * {@code UPDATE} equivalente.</p>
     */
    public AlumnoSimple save(AlumnoSimple alumno) {
        TABLA_ALUMNOS.put(alumno.getId(), alumno);
        return alumno;
    }
}

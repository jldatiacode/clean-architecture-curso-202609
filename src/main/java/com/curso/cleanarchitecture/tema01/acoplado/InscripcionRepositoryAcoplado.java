package com.curso.cleanarchitecture.tema01.acoplado;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema01.modelo.InscripcionSimple;

/**
 * Repositorio de inscripciones SIMULADO: "tabla INSCRIPCIONES" en un mapa
 * estático en memoria.
 *
 * <p><b>Capa que representa:</b> persistencia (acceso a datos).</p>
 *
 * <p><b>Qué enseña:</b> el método {@link #existsByCursoIdAndAlumnoId(Long, Long)}
 * es especialmente interesante para el debate del tema. Comprueba un dato
 * ("¿existe ya esta fila?"), pero la DECISIÓN de negocio asociada ("si existe,
 * rechaza la inscripción") la toma el controlador. Cuando la comprobación y la
 * decisión viven en clases distintas y externas al dominio, la regla "un alumno
 * no puede inscribirse dos veces" (Actividad 2 de la teoría) queda repartida y
 * es fácil olvidarla en un nuevo punto de entrada.</p>
 *
 * <p><b>Dependencias:</b> el modelo anémico del tema y Spring
 * ({@code @Repository}).</p>
 *
 * <p><b>Nota docente:</b> clase anotada solo como material de lectura; nunca se
 * escanea ni se registra como bean porque la aplicación arrancable solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}.</p>
 */
@Repository
public class InscripcionRepositoryAcoplado {

    // "Tabla INSCRIPCIONES" en memoria. La clave compuesta "cursoId-alumnoId"
    // simula la clave única que en SQL sería un UNIQUE(curso_id, alumno_id).
    private static final Map<String, InscripcionSimple> TABLA_INSCRIPCIONES = new HashMap<>();

    // Simula la secuencia/autoincremento de la base de datos para el id.
    private static final AtomicLong SECUENCIA = new AtomicLong(1);

    /**
     * Comprueba si un alumno ya está inscrito en un curso.
     *
     * <p>Imagina aquí una consulta SQL/JDBC:
     * {@code SELECT COUNT(*) FROM inscripciones WHERE curso_id = ? AND alumno_id = ?}.
     * El nombre sigue la convención de Spring Data para que resulte familiar.</p>
     */
    public boolean existsByCursoIdAndAlumnoId(Long cursoId, Long alumnoId) {
        return TABLA_INSCRIPCIONES.containsKey(clave(cursoId, alumnoId));
    }

    /**
     * Guarda una nueva inscripción.
     *
     * <p>Imagina aquí un
     * {@code INSERT INTO inscripciones (id, curso_id, alumno_id) VALUES (?, ?, ?)}.</p>
     */
    public InscripcionSimple save(InscripcionSimple inscripcion) {
        // Asignamos id como haría la base de datos con su secuencia.
        inscripcion.setId(SECUENCIA.getAndIncrement());
        TABLA_INSCRIPCIONES.put(
                clave(inscripcion.getCurso().getId(), inscripcion.getAlumno().getId()),
                inscripcion);
        return inscripcion;
    }

    // La construcción de la clave compuesta es un detalle puramente técnico de
    // este almacén en memoria; por eso queda encapsulada aquí y no se filtra
    // hacia fuera.
    private String clave(Long cursoId, Long alumnoId) {
        return cursoId + "-" + alumnoId;
    }
}

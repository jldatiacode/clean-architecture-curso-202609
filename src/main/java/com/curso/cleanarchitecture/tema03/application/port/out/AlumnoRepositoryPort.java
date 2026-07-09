package com.curso.cleanarchitecture.tema03.application.port.out;

import java.util.Optional;

import com.curso.cleanarchitecture.tema03.model.Alumno;

/**
 * Puerto de salida para acceder a alumnos (sección 8 de la teoría).
 *
 * <p>Misma filosofía que {@link CursoRepositoryPort}: expresa lo que la
 * aplicación necesita, sin mencionar ninguna tecnología de persistencia.</p>
 */
public interface AlumnoRepositoryPort {

    /**
     * Busca un alumno por su identificador.
     *
     * @param alumnoId identificador del alumno
     * @return el alumno, o vacío si no existe
     */
    Optional<Alumno> buscarPorId(Long alumnoId);

    /**
     * Persiste el alumno.
     *
     * @param alumno alumno a guardar
     */
    void guardar(Alumno alumno);
}

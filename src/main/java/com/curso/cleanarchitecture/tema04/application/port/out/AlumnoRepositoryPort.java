package com.curso.cleanarchitecture.tema04.application.port.out;

import java.util.Optional;

import com.curso.cleanarchitecture.tema04.model.Alumno;

/**
 * Puerto de salida: persistencia de alumnos.
 *
 * <p>Contrato de la sección 7 de la teoría. La aplicación declara aquí lo que
 * necesita saber de los alumnos; cómo se almacenen (memoria, JPA, fichero,
 * API externa de un ERP...) es decisión de la infraestructura.</p>
 */
public interface AlumnoRepositoryPort {

    /**
     * Busca un alumno por su identificador.
     *
     * @param alumnoId identificador del alumno
     * @return el alumno si existe; vacío en caso contrario
     */
    Optional<Alumno> buscarPorId(Long alumnoId);

    /**
     * Busca un alumno por su email. Útil, por ejemplo, para evitar registros
     * duplicados. Observa que el criterio de búsqueda es un concepto de
     * negocio (el email del alumno), no una columna SQL.
     *
     * @param email email del alumno
     * @return el alumno si existe; vacío en caso contrario
     */
    Optional<Alumno> buscarPorEmail(String email);

    /**
     * Persiste el estado actual del alumno.
     *
     * @param alumno alumno a guardar
     */
    void guardar(Alumno alumno);
}

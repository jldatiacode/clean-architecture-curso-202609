package com.curso.cleanarchitecture.tema03.application.port.out;

import com.curso.cleanarchitecture.tema03.model.Inscripcion;

/**
 * Puerto de salida para acceder a inscripciones (sección 8 de la teoría).
 *
 * <p>Fíjate en {@link #existeInscripcionActiva(Long, Long)}: es una consulta
 * diseñada a medida de la necesidad del caso de uso ("¿este alumno ya está
 * inscrito en este curso?"), no un método CRUD genérico. Los puertos se
 * diseñan desde la aplicación hacia fuera, no desde la base de datos hacia
 * dentro.</p>
 */
public interface InscripcionRepositoryPort {

    /**
     * Indica si ya existe una inscripción ACTIVA de un alumno en un curso.
     * Las inscripciones canceladas no cuentan: un alumno que canceló puede
     * volver a inscribirse.
     *
     * @param cursoId  identificador del curso
     * @param alumnoId identificador del alumno
     * @return true si existe una inscripción activa
     */
    boolean existeInscripcionActiva(Long cursoId, Long alumnoId);

    /**
     * Persiste la inscripción.
     *
     * @param inscripcion inscripción a guardar
     */
    void guardar(Inscripcion inscripcion);
}

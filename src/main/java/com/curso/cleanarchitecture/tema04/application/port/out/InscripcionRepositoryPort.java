package com.curso.cleanarchitecture.tema04.application.port.out;

import java.util.List;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.model.Inscripcion;

/**
 * Puerto de salida: persistencia de inscripciones.
 *
 * <p>Contrato de la sección 7 de la teoría. Incluye una consulta de negocio
 * muy expresiva: {@link #existeInscripcionActiva(Long, Long)}. Compárala con
 * un método técnico tipo {@code countByCursoIdAndAlumnoIdAndEstado(...)}:
 * el puerto habla el lenguaje del dominio, no el de la base de datos
 * (buena práctica de la sección 11).</p>
 */
public interface InscripcionRepositoryPort {

    /**
     * Busca una inscripción por su identificador.
     *
     * @param inscripcionId identificador de la inscripción
     * @return la inscripción si existe; vacío en caso contrario
     */
    Optional<Inscripcion> buscarPorId(Long inscripcionId);

    /**
     * Indica si el alumno ya tiene una inscripción ACTIVA en ese curso.
     * Es la consulta que impide inscripciones duplicadas.
     *
     * @param cursoId  identificador del curso
     * @param alumnoId identificador del alumno
     * @return true si existe inscripción activa para esa pareja curso-alumno
     */
    boolean existeInscripcionActiva(Long cursoId, Long alumnoId);

    /**
     * Recupera todas las inscripciones de un alumno (activas y canceladas).
     *
     * @param alumnoId identificador del alumno
     * @return lista (posiblemente vacía) de inscripciones del alumno
     */
    List<Inscripcion> buscarPorAlumnoId(Long alumnoId);

    /**
     * Persiste la inscripción. Si es nueva (sin id), el adaptador le asignará
     * un identificador: la generación de identidad es responsabilidad de la
     * persistencia, no del caso de uso.
     *
     * @param inscripcion inscripción a guardar
     */
    void guardar(Inscripcion inscripcion);
}

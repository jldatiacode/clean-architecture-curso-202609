package com.curso.cleanarchitecture.tema06.base;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para inscripciones — visto en el tema 3.
 *
 * <p>Fíjate en {@link #existeInscripcionActiva(Long, Long)}: el puerto habla
 * en términos de negocio ("¿este alumno ya está inscrito y activo en este
 * curso?"). Será el adaptador JPA quien lo traduzca a la consulta técnica
 * {@code existsByCursoIdAndAlumnoIdAndEstado(...)} de Spring Data. La
 * pregunta de negocio y la consulta técnica quedan a los dos lados de la
 * frontera, cada una en su idioma.</p>
 */
public interface InscripcionRepositoryPort {

    /** Busca una inscripción por id. {@code Optional} vacío si no existe. */
    Optional<Inscripcion> buscarPorId(Long inscripcionId);

    /**
     * ¿Existe ya una inscripción ACTIVA de este alumno en este curso?
     * La usa el caso de uso para impedir inscripciones duplicadas.
     */
    boolean existeInscripcionActiva(Long cursoId, Long alumnoId);

    /** Todas las inscripciones de un alumno (activas y canceladas). */
    List<Inscripcion> buscarPorAlumnoId(Long alumnoId);

    /** Persiste la inscripción (nueva o modificada). */
    void guardar(Inscripcion inscripcion);
}

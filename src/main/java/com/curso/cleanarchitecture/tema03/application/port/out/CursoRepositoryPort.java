package com.curso.cleanarchitecture.tema03.application.port.out;

import java.util.Optional;

import com.curso.cleanarchitecture.tema03.model.Curso;

/**
 * Puerto de salida para acceder a cursos (sección 8 de la teoría).
 *
 * <p>Devuelve {@link Optional} en la búsqueda: la ausencia de curso es un
 * resultado válido que el caso de uso decidirá cómo tratar (en nuestro flujo,
 * lanzando {@code IllegalArgumentException} con "Curso no encontrado").</p>
 */
public interface CursoRepositoryPort {

    /**
     * Busca un curso por su identificador.
     *
     * @param cursoId identificador del curso
     * @return el curso, o vacío si no existe
     */
    Optional<Curso> buscarPorId(Long cursoId);

    /**
     * Persiste el estado actual del curso (por ejemplo, tras ocupar una plaza).
     *
     * @param curso curso a guardar
     */
    void guardar(Curso curso);
}

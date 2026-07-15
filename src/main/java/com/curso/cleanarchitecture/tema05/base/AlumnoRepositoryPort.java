package com.curso.cleanarchitecture.tema05.base;

import java.util.Optional;

/**
 * Puerto de SALIDA para la persistencia de alumnos (definido en el tema 4).
 *
 * <p>Contrato mínimo: el caso de uso de inscripción solo necesita comprobar
 * que el alumno existe y recuperar sus datos. No añadimos métodos "por si
 * acaso": los puertos crecen cuando un caso de uso los necesita.</p>
 */
public interface AlumnoRepositoryPort {

    Optional<Alumno> buscarPorId(Long alumnoId);

    void guardar(Alumno alumno);
}

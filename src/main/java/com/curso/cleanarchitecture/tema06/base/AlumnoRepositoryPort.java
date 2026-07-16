package com.curso.cleanarchitecture.tema06.base;

import java.util.Optional;

/**
 * Puerto de salida para alumnos — visto en el tema 3.
 *
 * <p>El caso de uso de inscripción solo necesita comprobar que el alumno
 * existe, así que el puerto solo declara esa operación. Regla práctica:
 * los puertos se diseñan desde las necesidades del caso de uso, no copiando
 * el CRUD completo que "regala" JpaRepository.</p>
 */
public interface AlumnoRepositoryPort {

    /** Busca un alumno por id. {@code Optional} vacío si no existe. */
    Optional<Alumno> buscarPorId(Long alumnoId);
}

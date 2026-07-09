package com.curso.cleanarchitecture.tema04.model;

/**
 * Estados posibles de una inscripción.
 *
 * <p>Usamos un enum del dominio (y no un String ni un código numérico de base
 * de datos) para que el estado tenga significado de negocio y el compilador
 * nos proteja de valores inválidos. Cómo se persista este enum (varchar,
 * ordinal, etc.) es una decisión del adaptador de persistencia, no del dominio.</p>
 */
public enum EstadoInscripcion {

    /** La inscripción está vigente: el alumno ocupa una plaza del curso. */
    ACTIVA,

    /** La inscripción fue cancelada: ya no ocupa plaza. */
    CANCELADA
}

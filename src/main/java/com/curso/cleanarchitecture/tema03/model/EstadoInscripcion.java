package com.curso.cleanarchitecture.tema03.model;

/**
 * Estados posibles de una {@link Inscripcion}.
 *
 * <p>Usamos un enum del dominio (no un String suelto) para que el compilador
 * nos proteja: no existen estados "inventados" ni errores de escritura.
 * El caso de uso convertirá este enum a String con {@code name()} solo en el
 * momento de construir la response de aplicación (sección 9 de la teoría).</p>
 */
public enum EstadoInscripcion {

    /** La inscripción está vigente: el alumno ocupa plaza en el curso. */
    ACTIVA,

    /** La inscripción fue cancelada: ya no ocupa plaza. */
    CANCELADA
}

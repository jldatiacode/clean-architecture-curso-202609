package com.curso.cleanarchitecture.tema05.base;

/**
 * Estados posibles de una inscripción (visto en temas 3 y 4).
 *
 * <p>Es un concepto de NEGOCIO. En la respuesta HTTP viajará como texto
 * ({@code "ACTIVA"}), pero esa conversión enum → String la hace el adaptador,
 * no el dominio: el exterior no tiene por qué conocer nuestros tipos internos.</p>
 */
public enum EstadoInscripcion {

    /** Inscripción vigente: el alumno ocupa plaza en el curso. */
    ACTIVA,

    /** El alumno espera a que se libere una plaza. */
    EN_ESPERA,

    /** Inscripción anulada por el alumno o por administración. */
    CANCELADA
}

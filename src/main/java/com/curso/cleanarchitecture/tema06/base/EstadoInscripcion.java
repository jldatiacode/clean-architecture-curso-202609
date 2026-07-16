package com.curso.cleanarchitecture.tema06.base;

/**
 * Estado de una inscripción — visto en el tema 2.
 *
 * <p>Detalle importante para el tema 6: en el dominio el estado es un
 * {@code enum} (tipado, seguro, expresivo), pero en la tabla H2 se guarda
 * como texto ({@code VARCHAR}). Esa traducción {@code enum <-> String} es
 * responsabilidad del adaptador JPA, que usa {@code name()} para escribir
 * y compara con {@code name()} para leer. El dominio nunca se entera de que
 * en la base de datos vive como cadena.</p>
 */
public enum EstadoInscripcion {
    ACTIVA,
    CANCELADA
}

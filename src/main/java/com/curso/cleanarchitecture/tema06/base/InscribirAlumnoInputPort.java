package com.curso.cleanarchitecture.tema06.base;

/**
 * Puerto de entrada del caso de uso — visto en el tema 4.
 *
 * <p>Es el contrato que los adaptadores de entrada (el controlador REST del
 * tema 5) usan para invocar la aplicación. En el tema 6 cobra un papel extra:
 * es el <b>tipo del bean</b> que declara {@code ApplicationConfigEjemplo}.
 * Spring inyecta el puerto (interfaz), no el interactor (clase concreta):
 * quien lo consume no sabe qué implementación hay detrás.</p>
 */
public interface InscribirAlumnoInputPort {

    /** Ejecuta la inscripción y devuelve el resultado como DTO plano. */
    InscripcionResponse inscribir(InscribirAlumnoCommand command);
}

package com.curso.cleanarchitecture.tema05.base;

/**
 * Puerto de SALIDA para la persistencia de inscripciones (definido en el tema 4).
 *
 * <p>Detalle de diseño: {@code guardar} devuelve la inscripción CON el id que
 * le haya asignado la infraestructura (contador en memoria, secuencia de base
 * de datos...). Así el caso de uso obtiene el id sin saber cómo se genera.</p>
 */
public interface InscripcionRepositoryPort {

    Inscripcion guardar(Inscripcion inscripcion);
}

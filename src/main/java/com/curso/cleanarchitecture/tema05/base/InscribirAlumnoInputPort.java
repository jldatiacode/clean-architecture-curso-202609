package com.curso.cleanarchitecture.tema05.base;

/**
 * Puerto de ENTRADA del caso de uso "inscribir alumno" (definido en el tema 4).
 *
 * <p>Es el contrato que el mundo exterior usa para invocar la aplicación.
 * En este tema lo invocará un controlador REST, pero podría invocarlo
 * igualmente un comando de consola, un consumidor de eventos o un test.</p>
 *
 * <p>Fíjate en la firma: recibe un command interno y devuelve un response
 * interno. Ni rastro de HTTP: el caso de uso NUNCA devuelve
 * {@code ResponseEntity} ni códigos de estado. Traducir a HTTP es trabajo
 * del adaptador de entrada.</p>
 */
public interface InscribirAlumnoInputPort {

    InscripcionResponse ejecutar(InscribirAlumnoCommand command);
}

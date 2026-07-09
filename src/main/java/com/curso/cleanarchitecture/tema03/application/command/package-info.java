/**
 * Objetos de entrada de los casos de uso (sección 7 de la teoría).
 *
 * <p>Un {@code Command} agrupa los datos que necesita un caso de uso para
 * ejecutarse, evitando métodos con muchos parámetros sueltos.</p>
 *
 * <p><b>Advertencia de la teoría</b>: no confundas el DTO HTTP con el command
 * del caso de uso. En proyectos pequeños pueden parecerse (incluso tener los
 * mismos campos), pero pertenecen a capas distintas: el DTO HTTP vive en la
 * capa de entrega y puede cambiar con la API; el command pertenece a la capa
 * de aplicación y solo cambia si cambia la acción del sistema.</p>
 */
package com.curso.cleanarchitecture.tema03.application.command;

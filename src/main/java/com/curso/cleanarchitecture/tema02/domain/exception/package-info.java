/**
 * Excepciones de dominio del tema 2.
 *
 * <p>Una <b>excepción de dominio</b> representa una situación inválida desde el
 * punto de vista del negocio, no un fallo técnico. Ejemplos de nuestro proyecto:</p>
 * <ul>
 *   <li>"No se puede inscribir en un curso cerrado".</li>
 *   <li>"No hay plazas disponibles en el curso".</li>
 *   <li>"La inscripción ya está cancelada".</li>
 * </ul>
 *
 * <h2>Por qué no usamos {@code IllegalStateException} directamente</h2>
 * <p>Podríamos, pero una excepción propia como {@code ReglaNegocioException}
 * aporta:</p>
 * <ul>
 *   <li><b>Claridad</b>: se ve de inmediato que el error pertenece al negocio.</li>
 *   <li><b>Manejabilidad</b>: en temas posteriores, los adaptadores podrán
 *       capturarla y transformarla en una respuesta HTTP adecuada (p. ej. 400)
 *       sin confundirla con errores técnicos.</li>
 *   <li><b>Testabilidad</b>: los tests comprueban reglas de negocio de forma
 *       explícita con {@code assertThrows(ReglaNegocioException.class, ...)}.</li>
 * </ul>
 *
 * <p>Este paquete solo depende de {@code java.lang}. Nada de Spring ni JPA.</p>
 */
package com.curso.cleanarchitecture.tema02.domain.exception;

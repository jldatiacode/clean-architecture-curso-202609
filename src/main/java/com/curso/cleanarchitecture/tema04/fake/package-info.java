/**
 * Implementaciones FAKE (en memoria) de los puertos de salida del Tema 4.
 *
 * <p>Estas clases son adaptadores de salida simplificados: implementan los
 * contratos de {@code application.port.out} usando colecciones en memoria.</p>
 *
 * <p>¿Para qué sirven?</p>
 * <ul>
 *   <li>Para probar los casos de uso sin base de datos ni frameworks.</li>
 *   <li>Para DEMOSTRAR la inversión de dependencias (sección 9 de la teoría):
 *       podemos sustituir un fake por otro, o por un adaptador JPA en el
 *       Tema 5, SIN TOCAR una sola línea del caso de uso. El caso de uso solo
 *       conoce la interfaz; cualquier implementación que cumpla el contrato
 *       le vale.</li>
 * </ul>
 *
 * <p>Ojo al detalle: estos fakes guardan y devuelven objetos del DOMINIO
 * ({@code Curso}, {@code Alumno}, {@code Inscripcion}), igual que hará el
 * adaptador JPA cuando traduzca sus entidades técnicas a dominio antes de
 * cruzar el puerto. El contrato es el mismo para todos.</p>
 */
package com.curso.cleanarchitecture.tema04.fake;

/**
 * Puertos de ENTRADA (input ports) del Tema 4.
 *
 * <p>Un puerto de entrada declara una ACCIÓN que la aplicación ofrece al
 * exterior. Quién lo usa y quién lo implementa (sección 8 de la teoría):</p>
 *
 * <pre>
 *   REST Controller ──usa──▶ InputPort ◀──implementa── UseCase
 * </pre>
 *
 * <ul>
 *   <li>Lo CONSUME un adaptador de entrada: un controlador REST, un test,
 *       un comando de consola... Cualquiera que quiera disparar la acción.</li>
 *   <li>Lo IMPLEMENTA el caso de uso correspondiente en
 *       {@code application.usecase}.</li>
 * </ul>
 *
 * <p>Ventaja: el controlador REST del Tema 5 dependerá de estas interfaces y no
 * de las clases concretas. Podremos cambiar la implementación del caso de uso
 * (o sustituirla por un doble en tests) sin tocar el adaptador.</p>
 *
 * <p>Convención del curso: todos los puertos de entrada exponen un único método
 * {@code ejecutar(...)}, siguiendo el principio de responsabilidad única a
 * nivel de contrato: una acción, un puerto.</p>
 */
package com.curso.cleanarchitecture.tema04.application.port.in;

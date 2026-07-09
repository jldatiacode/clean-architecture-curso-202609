/**
 * Puertos de la aplicación (Tema 4).
 *
 * <p>Un PUERTO es una interfaz que permite comunicar capas sin acoplarlas a una
 * tecnología concreta. Metáfora de la teoría (sección 5): el núcleo de la
 * aplicación es una ciudad amurallada con puertas controladas; fuera quedan
 * REST, la base de datos, el email, Kafka... El núcleo define las puertas y
 * el exterior se adapta a ellas.</p>
 *
 * <p>Dos subpaquetes, dos direcciones:</p>
 * <ul>
 *   <li>{@code in}  → puertos de ENTRADA: acciones que la aplicación OFRECE.</li>
 *   <li>{@code out} → puertos de SALIDA: lo que la aplicación NECESITA del exterior.</li>
 * </ul>
 *
 * <p>Ambos tipos se definen AQUÍ, en la capa de aplicación, nunca en
 * infraestructura. Quien tiene la necesidad es quien dicta el contrato.</p>
 */
package com.curso.cleanarchitecture.tema04.application.port;

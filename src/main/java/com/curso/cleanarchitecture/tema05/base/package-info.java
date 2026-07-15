/**
 * <h2>Tema 5 — Soporte mínimo del núcleo (ya visto en los temas 3 y 4)</h2>
 *
 * <p>Este paquete NO es el protagonista del tema. Contiene el modelo, los puertos
 * y el caso de uso que construimos en los temas anteriores, repetidos aquí para que
 * el Tema 5 sea autocontenido y compile de forma independiente.</p>
 *
 * <p>Lo relevante para este tema es la frontera que dibujan estas piezas:</p>
 * <ul>
 *   <li>{@link com.curso.cleanarchitecture.tema05.base.InscribirAlumnoInputPort}
 *       es el puerto de ENTRADA: lo que el mundo exterior puede pedirle a la
 *       aplicación. El controlador REST (adaptador de entrada) lo invocará.</li>
 *   <li>{@link com.curso.cleanarchitecture.tema05.base.CursoRepositoryPort},
 *       {@link com.curso.cleanarchitecture.tema05.base.AlumnoRepositoryPort},
 *       {@link com.curso.cleanarchitecture.tema05.base.InscripcionRepositoryPort} y
 *       {@link com.curso.cleanarchitecture.tema05.base.NotificacionPort}
 *       son puertos de SALIDA: lo que la aplicación necesita del exterior.
 *       Los adaptadores de persistencia y notificación los implementarán.</li>
 * </ul>
 *
 * <p>Observa que en este paquete no hay ni una sola referencia a Spring, HTTP o JPA.
 * El núcleo no sabe (ni debe saber) qué tecnología lo invoca ni dónde se guardan
 * sus datos. Esa es la promesa de Clean Architecture que los adaptadores cumplen.</p>
 */
package com.curso.cleanarchitecture.tema05.base;

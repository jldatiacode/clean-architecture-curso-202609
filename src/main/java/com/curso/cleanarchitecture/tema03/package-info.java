/**
 * Tema 3. Casos de uso e interactores.
 *
 * <p>En este tema damos el salto del dominio (Tema 2) a la <b>capa de aplicación</b>.
 * La pregunta clave es: ¿qué acciones permite realizar el sistema? Esas acciones
 * son los <b>casos de uso</b>, y las clases que los implementan se denominan
 * <b>interactores</b> en la terminología de Clean Architecture.</p>
 *
 * <p>Estructura del tema (refleja la organización recomendada en la sección 12
 * de la teoría):</p>
 * <ul>
 *   <li>{@code model}: entidades del dominio ({@code Curso}, {@code Alumno},
 *       {@code Inscripcion}, {@code EstadoInscripcion}) con sus reglas de negocio.</li>
 *   <li>{@code application.command}: objetos de entrada del caso de uso (sección 7).</li>
 *   <li>{@code application.response}: objetos de salida del caso de uso (sección 7).</li>
 *   <li>{@code application.port.out}: puertos de salida, interfaces que expresan
 *       necesidades de la aplicación sin hablar de tecnología (sección 8).</li>
 *   <li>{@code application.usecase}: el interactor {@code InscribirAlumnoUseCase}
 *       (sección 9).</li>
 *   <li>{@code fake}: repositorios en memoria para probar sin infraestructura
 *       (sección 10).</li>
 * </ul>
 *
 * <p>Todo el tema es <b>Java puro</b>: sin Spring, sin JPA, sin HTTP, sin JSON.
 * Esa es precisamente la demostración central del tema: un caso de uso bien
 * diseñado se puede construir y probar sin levantar ningún framework.</p>
 */
package com.curso.cleanarchitecture.tema03;

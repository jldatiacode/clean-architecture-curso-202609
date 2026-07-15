/**
 * <h2>Tema 5. Adaptadores de entrada y salida</h2>
 *
 * <p>En el Tema 4 definimos <b>puertos</b>: contratos que el núcleo de la aplicación
 * expone (puertos de entrada) o necesita (puertos de salida). En este tema conectamos
 * esos contratos con tecnologías concretas mediante <b>adaptadores</b>.</p>
 *
 * <p>Un adaptador es una pieza de traducción: convierte el lenguaje del mundo exterior
 * (HTTP/JSON, SQL, consola, email...) al lenguaje interno de la aplicación
 * (commands, modelos de dominio, responses) y viceversa. El adaptador
 * <b>no decide reglas de negocio</b>: convierte, delega y devuelve.</p>
 *
 * <p>Organización del tema:</p>
 * <ul>
 *   <li>{@code base}: el mínimo núcleo (modelo + puertos + caso de uso) ya visto en
 *       los temas 3 y 4, repetido aquí para que el tema sea autocontenido.</li>
 *   <li>{@code adapter.in.web}: adaptador de ENTRADA — controlador REST, DTOs HTTP
 *       y gestión de errores (secciones 5, 6 y 10 de la teoría).</li>
 *   <li>{@code adapter.out.persistence}: adaptadores de SALIDA en memoria
 *       (sección 7 de la teoría).</li>
 *   <li>{@code adapter.out.persistence.jpa}: esbozo del adaptador JPA con entidad,
 *       mapper y Spring Data (secciones 8 y 9; se desarrolla del todo en el Tema 6).</li>
 * </ul>
 *
 * <p><b>IMPORTANTE (material de lectura):</b> la única aplicación Spring arrancable
 * de este proyecto vive en {@code com.curso.cleanarchitecture.proyecto} y SOLO
 * escanea ese paquete. Las clases anotadas de este tema
 * ({@code @RestController}, {@code @Repository}, {@code @Entity},
 * {@code @RestControllerAdvice}) compilan pero nunca se registran como beans:
 * son material didáctico. La versión funcional equivalente, que sí se puede
 * probar con Postman, está en el paquete {@code proyecto}.</p>
 */
package com.curso.cleanarchitecture.tema05;

/**
 * Tema 2. Entidades, dominio y reglas de negocio.
 *
 * <p>Este paquete contiene el material del tema 2 del curso de Clean Architecture:
 * la construcción del <b>dominio</b> del proyecto transversal de gestión de cursos,
 * alumnos e inscripciones.</p>
 *
 * <h2>Idea central del tema</h2>
 * <p>El dominio representa lo que la aplicación hace, no cómo lo hace técnicamente.
 * Por eso todo el código de este paquete es <b>Java puro</b>: se puede compilar,
 * ejecutar y probar sin Spring Boot, sin JPA, sin base de datos y sin servidor web.</p>
 *
 * <h2>Qué encontrarás aquí</h2>
 * <ul>
 *   <li>{@code domain.model}: las entidades ({@code Curso}, {@code Alumno},
 *       {@code Inscripcion}), el objeto de valor {@code Email} y el enum
 *       {@code EstadoInscripcion}.</li>
 *   <li>{@code domain.exception}: la excepción de dominio
 *       {@code ReglaNegocioException}, que expresa errores de negocio
 *       (no errores técnicos).</li>
 * </ul>
 *
 * <h2>Qué NO encontrarás aquí (y por qué)</h2>
 * <ul>
 *   <li>Anotaciones como {@code @Entity}, {@code @Service} o {@code @Autowired}:
 *       acoplarían el dominio a frameworks concretos.</li>
 *   <li>Repositorios, controladores o DTOs: pertenecen a otras capas que
 *       veremos en temas posteriores.</li>
 *   <li>Setters genéricos: una entidad de dominio expone métodos con nombre de
 *       negocio ({@code cerrar()}, {@code ocuparPlaza()}, {@code cancelar()}),
 *       no manipulación libre de sus atributos.</li>
 * </ul>
 *
 * <p>Pregunta de control que debe poder responderse con un sí:
 * <i>¿podría ejecutar y probar estas clases sin arrancar Spring Boot?</i></p>
 */
package com.curso.cleanarchitecture.tema02;

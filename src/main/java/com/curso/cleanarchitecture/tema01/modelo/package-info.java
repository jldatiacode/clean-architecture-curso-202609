/**
 * <h2>Tema 1 — Modelos anémicos del ejemplo acoplado</h2>
 *
 * <p>Este subpaquete contiene los tres conceptos del proyecto transversal
 * ({@code Curso}, {@code Alumno}, {@code Inscripcion}) modelados de la peor
 * forma posible desde el punto de vista del dominio: como <b>modelos anémicos</b>,
 * es decir, clases que solo tienen atributos, getters y setters, sin ninguna
 * regla de negocio dentro.</p>
 *
 * <h3>Por qué el modelo anémico es un problema</h3>
 * <ul>
 *   <li>Si la clase {@code Curso} no sabe validar "no aceptes inscripciones si
 *       estás cerrado o sin plazas", alguien tendrá que validarlo desde fuera.
 *       Ese "alguien" acaba siendo un controlador o un servicio, y la regla
 *       queda desprotegida y duplicable (síntoma de la sección 4.2 de la teoría:
 *       "entidades llenas de datos y sin comportamiento").</li>
 *   <li>Los setters públicos permiten que cualquier clase deje el objeto en un
 *       estado inválido (por ejemplo, plazas negativas) sin que nadie lo impida.</li>
 *   <li>El modelo expresa la tecnología (una fila de tabla) en lugar del negocio
 *       (un curso que puede cerrarse y ocupar plazas). Es el error de "diseñar
 *       desde la base de datos" de la sección 4.3 de la teoría.</li>
 * </ul>
 *
 * <p>En el Tema 1 estas clases se usan a propósito para construir el ejemplo
 * acoplado de {@code tema01.acoplado}. La versión con comportamiento (la entidad
 * {@code Curso} de la sección 15 de la teoría, con {@code validarPuedeRecibirInscripcion()}
 * y {@code ocuparPlaza()}) se esboza en {@code tema01.separado} y se construirá
 * en serio en los temas de dominio.</p>
 */
package com.curso.cleanarchitecture.tema01.modelo;

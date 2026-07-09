/**
 * Responses: objetos de SALIDA de los casos de uso.
 *
 * <p>Un response es la otra mitad del contrato del puerto de entrada: lo que la
 * aplicación devuelve al adaptador que la invocó.</p>
 *
 * <p>¿Por qué no devolver directamente las entidades de dominio ({@code Curso},
 * {@code Inscripcion})? Dos motivos:</p>
 * <ul>
 *   <li>Protegemos el dominio: el adaptador de entrada no puede invocar
 *       {@code ocuparPlaza()} ni mutar estado de negocio por accidente.</li>
 *   <li>Controlamos qué se expone: el response publica exactamente los datos
 *       que el exterior necesita, ni uno más.</li>
 * </ul>
 *
 * <p>Y por supuesto, JAMÁS se devuelve una entidad JPA por un puerto: eso
 * filtraría la infraestructura hacia dentro (secciones 9 y 11 de la teoría).</p>
 */
package com.curso.cleanarchitecture.tema04.application.response;

/**
 * Modelo de dominio del tema 2: entidades, objeto de valor y estados.
 *
 * <p>Aquí vive el <b>modelo rico</b> del proyecto transversal. Cada clase
 * ilustra un concepto del tema:</p>
 * <ul>
 *   <li>{@code Curso}: entidad con identidad, estado (abierto/cerrado, plazas)
 *       y reglas protegidas ({@code ocuparPlaza()} valida antes de actuar).</li>
 *   <li>{@code Alumno}: entidad que delega la validez del email en un objeto
 *       de valor en lugar de manejar un {@code String} cualquiera.</li>
 *   <li>{@code Email}: objeto de valor. Sin identidad propia; dos emails con
 *       el mismo texto son equivalentes ({@code equals}/{@code hashCode} por valor).</li>
 *   <li>{@code EstadoInscripcion}: enum con los estados válidos de una
 *       inscripción. Un estado no es una entidad.</li>
 *   <li>{@code Inscripcion}: entidad que representa la relación alumno-curso
 *       y coordina el ciclo ocupar/liberar plaza.</li>
 * </ul>
 *
 * <h2>Decisiones de diseño que se repiten en todas las clases</h2>
 * <ul>
 *   <li><b>{@code id} final</b>: la identidad no cambia una vez creada la entidad.</li>
 *   <li><b>Sin setters genéricos</b>: el estado se modifica con métodos de
 *       negocio ({@code cerrar()}, {@code cambiarNombre(...)}, {@code cancelar()}).</li>
 *   <li><b>Constructor que valida</b>: es imposible crear un objeto en estado
 *       inválido (invariantes protegidas desde el nacimiento del objeto).</li>
 *   <li><b>Excepción de dominio</b>: las reglas incumplidas lanzan
 *       {@code ReglaNegocioException}, nunca errores técnicos.</li>
 * </ul>
 *
 * <p><b>Dependencias permitidas:</b> {@code java.*} y el paquete hermano
 * {@code domain.exception}. Nada más. Esto es lo que hace al dominio testeable
 * sin infraestructura.</p>
 */
package com.curso.cleanarchitecture.tema02.domain.model;

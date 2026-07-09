/**
 * Modelo de dominio del Tema 3: {@code Curso}, {@code Alumno},
 * {@code Inscripcion} y {@code EstadoInscripcion}.
 *
 * <p><b>NOTA DE DISEÑO IMPORTANTE — evolución respecto al Tema 2:</b></p>
 *
 * <p>En el Tema 2, {@code Inscripcion} referenciaba objetos completos
 * ({@code Curso} y {@code Alumno}). En este tema, siguiendo la teoría
 * (secciones 9 y 10), {@code Inscripcion} pasa a referenciar únicamente
 * <b>identificadores</b>: {@code cursoId} y {@code alumnoId}.</p>
 *
 * <p>¿Por qué este cambio?</p>
 * <ul>
 *   <li><b>Simplifica la persistencia:</b> guardar una inscripción se reduce a
 *       guardar dos identificadores, sin necesidad de serializar (ni mantener
 *       sincronizados) los objetos {@code Curso} y {@code Alumno} completos.</li>
 *   <li><b>Evita relaciones cargadas:</b> si la inscripción arrastrara el objeto
 *       {@code Curso} entero, cargar una inscripción implicaría cargar también el
 *       curso, sus plazas, su estado... y potencialmente una cadena de objetos
 *       relacionados. Con identificadores, cada agregado se carga solo cuando
 *       de verdad se necesita.</li>
 *   <li><b>Delimita agregados:</b> {@code Curso}, {@code Alumno} e
 *       {@code Inscripcion} evolucionan como unidades de consistencia
 *       independientes; la relación entre ellos se expresa por id, que es como
 *       se relacionan agregados distintos en un diseño limpio.</li>
 * </ul>
 *
 * <p>Las clases de este paquete contienen las <b>reglas de negocio</b>
 * (sección 5 de la teoría): normas del dominio que existen aunque cambie el
 * framework, la base de datos o la interfaz. Por ejemplo: "un curso cerrado
 * no acepta inscripciones" o "una inscripción cancelada no puede volver a
 * cancelarse". Estas reglas se protegen aquí, no en el caso de uso.</p>
 */
package com.curso.cleanarchitecture.tema03.model;

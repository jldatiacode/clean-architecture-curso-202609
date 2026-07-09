/**
 * Casos de uso (interactores) del Tema 3.
 *
 * <p>Aquí viven las clases que implementan los casos de uso identificados en
 * la sección 4 de la teoría. En este tema implementamos
 * {@code InscribirAlumnoUseCase}; el resto ({@code CrearCursoUseCase},
 * {@code RegistrarAlumnoUseCase}, {@code CancelarInscripcionUseCase},
 * {@code ConsultarCursosDisponiblesUseCase}) seguirán el mismo patrón en
 * próximos temas.</p>
 *
 * <p>Un interactor coordina el flujo de aplicación, pero NO debe saber de
 * HTTP, JSON, SQL, {@code ResponseEntity}, controladores ni entidades JPA
 * (sección 6 de la teoría). Es Java puro y se prueba sin framework.</p>
 */
package com.curso.cleanarchitecture.tema03.application.usecase;

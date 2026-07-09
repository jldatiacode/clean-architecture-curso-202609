/**
 * Repositorios fake en memoria (sección 10 de la teoría).
 *
 * <p>Un <b>fake</b> es una implementación real pero simplificada de un puerto:
 * funciona de verdad (guarda y busca), solo que en memoria en lugar de en una
 * base de datos. Permiten probar los casos de uso sin H2, sin PostgreSQL y
 * sin Spring.</p>
 *
 * <p>Diferencia con un <b>mock</b> (ver {@code InscribirAlumnoUseCaseMockitoTest}):
 * el fake tiene comportamiento propio y se usa como colaborador normal; el
 * mock es un objeto programado respuesta a respuesta ({@code when(...)}) que
 * además registra las llamadas recibidas ({@code verify(...)}).</p>
 *
 * <p>Nota docente: estos fakes están en {@code src/main} para poder mostrarlos
 * como parte del material del tema, pero en un proyecto real vivirían en
 * {@code src/test}, porque solo sirven para pruebas.</p>
 */
package com.curso.cleanarchitecture.tema03.fake;

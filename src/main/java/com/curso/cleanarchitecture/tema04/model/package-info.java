/**
 * Modelo de dominio del Tema 4.
 *
 * <p>Estas clases representan los conceptos de negocio del proyecto transversal
 * (sistema de gestión de cursos e inscripciones). No conocen JPA, ni SQL, ni Spring:
 * son Java puro con reglas de negocio dentro.</p>
 *
 * <p>Punto importante para este tema: los puertos (interfaces de la aplicación)
 * hablan SIEMPRE en términos de estas clases de dominio, nunca en términos de
 * entidades JPA. Si un puerto devolviera {@code CursoJpaEntity}, la capa de
 * aplicación quedaría contaminada por infraestructura (secciones 9 y 11 de la teoría).</p>
 */
package com.curso.cleanarchitecture.tema04.model;

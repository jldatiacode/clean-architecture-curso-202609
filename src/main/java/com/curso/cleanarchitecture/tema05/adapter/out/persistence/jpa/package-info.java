/**
 * <h2>Tema 5 — Esbozo del adaptador JPA (secciones 8 y 9 de la teoría)</h2>
 *
 * <p>Mismo puerto ({@code CursoRepositoryPort}), otra tecnología. La cadena
 * completa de la traducción es:</p>
 *
 * <pre>
 * Caso de uso → CursoRepositoryPort (puerto, dominio)
 *                   ↑ implementa
 *               CursoRepositoryJpaAdapter (adaptador)
 *                   ↓ usa                     ↓ usa
 *               SpringDataCursoRepository   CursoPersistenceMapper
 *               (tecnología: Spring Data)   (CursoJpaEntity ↔ Curso)
 * </pre>
 *
 * <p>Piezas y su papel:</p>
 * <ul>
 *   <li>{@code CursoJpaEntity}: modelo de PERSISTENCIA (una fila de tabla),
 *       no de dominio. JPA queda fuera del dominio.</li>
 *   <li>{@code CursoPersistenceMapper}: traduce entidad JPA ↔ dominio en ambos
 *       sentidos. Es el peaje que pagamos por mantener el dominio limpio.</li>
 *   <li>{@code SpringDataCursoRepository}: interfaz de Spring Data; el framework
 *       genera la implementación y el SQL.</li>
 *   <li>{@code CursoRepositoryJpaAdapter}: implementa el puerto combinando
 *       repositorio + mapper. Es lo único que "ve" el resto de la aplicación.</li>
 * </ul>
 *
 * <p><b>Este paquete es un ESBOZO DIDÁCTICO:</b> se desarrolla del todo en el
 * Tema 6 (conexión real con Spring Boot y H2, configuración, datos iniciales)
 * y en el paquete {@code com.curso.cleanarchitecture.proyecto}, donde está la
 * versión funcional. Recuerda además que la aplicación arrancable solo escanea
 * {@code proyecto}: las anotaciones {@code @Entity} y {@code @Repository} de
 * aquí compilan pero nunca se registran.</p>
 */
package com.curso.cleanarchitecture.tema05.adapter.out.persistence.jpa;

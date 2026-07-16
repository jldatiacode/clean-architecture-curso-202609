/**
 * Adaptador de salida de persistencia con JPA y Spring Data (secciones 9 y 10).
 *
 * <p>Este paquete ES la capa de frameworks y drivers para la base de datos.
 * Contiene tres tipos de pieza, y conviene distinguirlos bien:</p>
 * <ul>
 *   <li><b>Entidades JPA</b> ({@code CursoJpaEntity}, {@code AlumnoJpaEntity},
 *       {@code InscripcionJpaEntity}): clases anotadas con {@code @Entity}
 *       que describen tablas. Pertenecen a la persistencia, NO al dominio.</li>
 *   <li><b>Repositorios Spring Data</b> ({@code SpringDataCursoRepository}...):
 *       interfaces que extienden {@code JpaRepository}; Spring genera la
 *       implementación en tiempo de arranque. Son un detalle técnico que los
 *       casos de uso jamás ven.</li>
 *   <li><b>Adaptadores JPA</b> ({@code CursoRepositoryJpaAdapter}...): las
 *       clases que implementan los <b>puertos</b> del paquete {@code base} y
 *       traducen entre objetos de dominio y entidades JPA con sus mappers
 *       privados {@code toDomain} / {@code toEntity}.</li>
 * </ul>
 *
 * <p>Dirección de las dependencias: este paquete importa del paquete
 * {@code base} (dominio y puertos); {@code base} no importa NADA de aquí.
 * La flecha apunta hacia dentro, como manda la regla de dependencia.</p>
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}, así que estas entidades no
 * generan tablas ni estos {@code @Repository} se registran como beans. Las
 * tablas reales las crean las entidades del paquete {@code proyecto} (con
 * {@code spring.jpa.hibernate.ddl-auto=update} de
 * {@code application.properties}) y se rellenan desde {@code data.sql}.</p>
 *
 * <p><b>Cómo se prueba esto de verdad:</b> en el paquete {@code proyecto},
 * con {@code @DataJpaTest} (levanta solo la capa JPA contra H2). Por eso este
 * tema no incluye tests propios.</p>
 */
package com.curso.cleanarchitecture.tema06.persistence;

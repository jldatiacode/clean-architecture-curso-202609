/**
 * Puertos de salida mínimos del caso de uso (sección 8 de la teoría).
 *
 * <p>Un <b>puerto de salida</b> es una interfaz que expresa una necesidad de
 * la capa de aplicación: "necesito buscar un curso por id", "necesito guardar
 * una inscripción". No dice CÓMO se hace, solo QUÉ se necesita.</p>
 *
 * <p>Estas interfaces no hablan de JPA, Hibernate, H2 ni PostgreSQL. Hablan de
 * necesidades de la aplicación. Gracias a ellas:</p>
 * <ul>
 *   <li>El caso de uso no depende de una base de datos concreta
 *       (inversión de dependencias: la infraestructura implementará estas
 *       interfaces, no al revés).</li>
 *   <li>Podemos probar el caso de uso con implementaciones en memoria
 *       (paquete {@code fake}) o con mocks.</li>
 * </ul>
 *
 * <p>El Tema 4 profundizará en puertos (incluidos los de entrada); aquí
 * definimos solo los imprescindibles para el flujo de inscripción.</p>
 */
package com.curso.cleanarchitecture.tema03.application.port.out;

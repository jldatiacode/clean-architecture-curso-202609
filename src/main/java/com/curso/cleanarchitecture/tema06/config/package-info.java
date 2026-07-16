/**
 * Configuración de infraestructura del tema 6 (secciones 8 y 11).
 *
 * <p>Este paquete responde a la pregunta: si el caso de uso es Java puro y
 * los adaptadores son beans de Spring, ¿quién los conecta? Respuesta: la
 * <b>configuración</b>, la pieza más externa del sistema junto con el main.
 * Aquí se decide qué implementación concreta recibe cada puerto.</p>
 *
 * <ul>
 *   <li>{@code ApplicationConfigEjemplo}: crea el bean del caso de uso con
 *       un {@code new} normal, para que {@code InscribirAlumnoUseCase} siga
 *       sin anotaciones (sección 8).</li>
 *   <li>{@code DataLoaderConfigEjemplo}: carga datos de ejemplo con un
 *       {@code CommandLineRunner} (sección 11). En el proyecto real esta
 *       tarea la hace {@code src/main/resources/data.sql}.</li>
 * </ul>
 *
 * <p>La configuración declarativa (properties) es la otra mitad de esta capa:
 * la conexión H2, el ddl-auto y la consola web viven en
 * {@code src/main/resources/application.properties} (sección 7 de la teoría).
 * Ese fichero es compartido por todo el proyecto: no se duplica aquí.</p>
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}, así que estas clases
 * {@code @Configuration} compilan pero nunca se procesan: sus @Bean no se
 * crean y su CommandLineRunner no se ejecuta.</p>
 */
package com.curso.cleanarchitecture.tema06.config;

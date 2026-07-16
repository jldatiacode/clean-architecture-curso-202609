/**
 * Tema 6. Frameworks, drivers e infraestructura.
 *
 * <p>Hasta ahora (temas 2 a 5) todo era Java puro: dominio, casos de uso,
 * puertos y adaptadores diseñados sin tecnología. En este tema incorporamos
 * las herramientas reales —Spring Boot, Spring Data JPA, Hibernate y H2— y
 * la lección central es dónde las colocamos: en la <b>capa más externa</b>.
 * Las usamos sin dejar que controlen la arquitectura.</p>
 *
 * <p>Ideas clave que demuestra el código de este paquete:</p>
 * <ul>
 *   <li><b>Spring Boot es una herramienta, no la arquitectura</b> (sección 5
 *       de la teoría). El dominio y los casos de uso deben poder entenderse
 *       y probarse sin arrancar el framework.</li>
 *   <li><b>JPA es un detalle de persistencia</b> (sección 9): las clases con
 *       {@code @Entity} viven en el adaptador de salida, nunca en el dominio.
 *       {@code Curso} y {@code Inscripcion} del dominio no llevan anotaciones.</li>
 *   <li><b>Los casos de uso no conocen JpaRepository</b> (sección 10): hablan
 *       con puertos; los adaptadores JPA implementan esos puertos y traducen
 *       entre entidades JPA y objetos de dominio.</li>
 *   <li><b>Los beans se crean desde {@code @Configuration}</b> (sección 8):
 *       así el interactor {@code InscribirAlumnoUseCase} sigue siendo Java
 *       puro, sin {@code @Service} ni {@code @Autowired}.</li>
 * </ul>
 *
 * <p>Estructura del tema:</p>
 * <ul>
 *   <li>{@code base}: mini núcleo de dominio + aplicación ya visto en los
 *       temas 2 a 4, repetido aquí de forma resumida para que el tema sea
 *       autocontenido.</li>
 *   <li>{@code persistence}: entidades JPA, repositorios Spring Data y
 *       adaptadores JPA (secciones 9 y 10).</li>
 *   <li>{@code config}: configuración de beans y carga de datos iniciales
 *       (secciones 8 y 11).</li>
 * </ul>
 *
 * <h2>Importante: este paquete es material de LECTURA</h2>
 *
 * <p>La única aplicación Spring arrancable del curso vive en
 * {@code com.curso.cleanarchitecture.proyecto} y SOLO escanea ese paquete.
 * Las clases anotadas de este tema ({@code @Configuration}, {@code @Entity},
 * {@code @Repository}...) compilan, pero nunca se registran como beans ni
 * generan tablas: existen para estudiar el patrón con calma. La configuración
 * H2 real está en {@code src/main/resources/application.properties} y los
 * datos iniciales reales en {@code src/main/resources/data.sql}.</p>
 *
 * <h2>Por qué no hay tests en este tema</h2>
 *
 * <p>La persistencia real se prueba en el paquete {@code proyecto} con
 * {@code @DataJpaTest}, que levanta un contexto JPA mínimo contra H2 y
 * verifica entidades, repositorios y adaptadores de verdad. Probar aquí las
 * clases de este tema no aportaría nada: no se escanean, así que un test de
 * integración sobre ellas exigiría duplicar la aplicación arrancable. La
 * lección de testing de infraestructura pertenece al proyecto integrado.</p>
 *
 * <h2>Consola H2 (sección 12 de la teoría)</h2>
 *
 * <p>Con la aplicación real arrancada
 * ({@code CleanArchitectureCursosApplication} del paquete {@code proyecto}):</p>
 * <ol>
 *   <li>Abrir en el navegador: {@code http://localhost:8080/h2-console}</li>
 *   <li>Conectar con estos datos:
 *     <ul>
 *       <li>JDBC URL: {@code jdbc:h2:mem:cursosdb}</li>
 *       <li>User Name: {@code sa}</li>
 *       <li>Password: (vacío, no escribir nada)</li>
 *     </ul>
 *   </li>
 *   <li>Ejecutar las consultas de comprobación:
 *     <pre>
 *     SELECT * FROM CURSOS;
 *     SELECT * FROM ALUMNOS;
 *     SELECT * FROM INSCRIPCIONES;
 *     </pre>
 *   </li>
 * </ol>
 *
 * <p>Criterio final del tema (sección 13): si mañana cambiamos H2 por
 * PostgreSQL, deberían cambiar la configuración y los adaptadores JPA,
 * pero NO el dominio ni los casos de uso.</p>
 */
package com.curso.cleanarchitecture.tema06;

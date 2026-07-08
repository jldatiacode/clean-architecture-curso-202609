/**
 * <h2>Tema 1 — La versión acoplada: "todo depende de todo"</h2>
 *
 * <p>Este subpaquete reproduce, con código que compila, el ejemplo problemático
 * de la sección 5 de la teoría: un controlador REST que inscribe alumnos en
 * cursos mezclando en un único método las responsabilidades de tres capas
 * distintas.</p>
 *
 * <h3>Contenido</h3>
 * <ul>
 *   <li>{@code CursoRepositoryAcoplado}, {@code AlumnoRepositoryAcoplado},
 *       {@code InscripcionRepositoryAcoplado} — simulan el acceso a base de
 *       datos con mapas estáticos en memoria. Cada método lleva un comentario
 *       del estilo "imagina aquí una consulta SQL/JDBC" para que el ejemplo no
 *       necesite una base de datos real pero se lea como si la tuviera.</li>
 *   <li>{@code InscripcionControllerAcoplado} — el protagonista del tema: el
 *       controlador que valida reglas de negocio, consulta repositorios, crea
 *       objetos, guarda datos y construye respuestas HTTP, todo en un método.</li>
 * </ul>
 *
 * <h3>Problemas que ilustra (tabla de la sección 6 de la teoría)</h3>
 * <ul>
 *   <li>El controlador tiene demasiada responsabilidad.</li>
 *   <li>La lógica de negocio está mezclada con la web.</li>
 *   <li>La lógica depende de Spring (vive dentro de un {@code @RestController}).</li>
 *   <li>Es difícil de probar: hace falta levantar demasiadas piezas externas.</li>
 *   <li>El negocio no está protegido: las reglas viven en una clase externa.</li>
 *   <li>Cambiar la persistencia puede afectar al flujo: el controlador conoce
 *       directamente los repositorios.</li>
 * </ul>
 *
 * <p><b>Importante:</b> este código "funciona". La lección del Tema 1 es que
 * funcionar no basta: la pregunta correcta es si la regla está situada en el
 * lugar donde mejor se protege y reutiliza (sección 1.3 de la teoría).</p>
 *
 * <p><b>Nota docente:</b> las clases anotadas con Spring de este paquete son
 * material de lectura. La aplicación arrancable del proyecto solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}, así que estos beans nunca se
 * registran ni se exponen sus endpoints.</p>
 */
package com.curso.cleanarchitecture.tema01.acoplado;

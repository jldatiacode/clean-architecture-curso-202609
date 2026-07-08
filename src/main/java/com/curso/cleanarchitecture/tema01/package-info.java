/**
 * <h2>Tema 1 — Fundamentos de Clean Architecture: el problema del acoplamiento</h2>
 *
 * <p>Este paquete contiene el material de código del Tema 1 del curso. Aquí NO se
 * construye todavía una arquitectura limpia: se muestra el <b>problema</b> que
 * Clean Architecture intenta resolver, usando el proyecto transversal del curso,
 * un <b>sistema de gestión de cursos e inscripciones</b>.</p>
 *
 * <h3>Qué enseña este tema</h3>
 * <ul>
 *   <li><b>Qué es el acoplamiento:</b> el nivel de dependencia entre partes del
 *       sistema. Cuando una clase "sabe demasiado" (HTTP, reglas de negocio,
 *       persistencia, framework), cualquier cambio en un detalle técnico se
 *       propaga por muchas clases. Es la situación de la imagen "todo depende
 *       de todo" de la teoría (sección 7).</li>
 *   <li><b>Por qué un controlador no debe contener negocio:</b> si la regla
 *       "un alumno no puede inscribirse en un curso cerrado" vive dentro de un
 *       {@code @RestController}, solo se aplica cuando la entrada es HTTP. El día
 *       que la inscripción llegue por consola, CSV, mensajería o un panel web,
 *       habrá que duplicar la lógica (secciones 5, 6 y 24 de la teoría).</li>
 *   <li><b>Qué es negocio y qué es detalle técnico:</b> {@code Curso},
 *       {@code Alumno}, {@code Inscripcion} y las plazas disponibles son negocio.
 *       Spring Boot, la base de datos, el endpoint REST y el JSON de respuesta
 *       son detalles técnicos (Actividad 4 de la teoría). El negocio debe estar
 *       en el centro; los detalles, en los bordes.</li>
 *   <li><b>Por qué el código acoplado es difícil de probar:</b> para probar una
 *       regla de negocio encerrada en un controlador hay que levantar Spring,
 *       preparar la "base de datos", construir una petición HTTP y leer la
 *       respuesta. En una arquitectura limpia, la misma regla se probaría con
 *       Java puro y JUnit, sin servidor ni framework (sección 26).</li>
 * </ul>
 *
 * <h3>Organización del paquete</h3>
 * <ul>
 *   <li>{@code tema01.modelo} — modelos anémicos (solo datos, sin comportamiento):
 *       el primer síntoma de que las reglas vivirán en el lugar equivocado.</li>
 *   <li>{@code tema01.acoplado} — la versión problemática: un controlador que
 *       mezcla HTTP, reglas de negocio y persistencia (sección 5 de la teoría).</li>
 *   <li>{@code tema01.separado} — un primer esbozo conceptual de separación:
 *       la regla extraída del controlador y la comparación entre el flujo
 *       acoplado y el flujo limpio (secciones 8 y 29).</li>
 * </ul>
 *
 * <p><b>Pregunta guía del tema</b> (sección 4.8 de la teoría):
 * <i>¿Estoy protegiendo las reglas importantes del negocio o las estoy mezclando
 * con detalles técnicos?</i></p>
 *
 * <p><b>Nota docente:</b> las clases anotadas con Spring de este paquete son
 * material de lectura. La única aplicación arrancable del proyecto vive en
 * {@code com.curso.cleanarchitecture.proyecto} y solo escanea ese paquete, por
 * lo que nada de lo que hay aquí se registra como bean ni se ejecuta.</p>
 */
package com.curso.cleanarchitecture.tema01;

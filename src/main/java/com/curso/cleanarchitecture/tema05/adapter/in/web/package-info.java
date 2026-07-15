/**
 * <h2>Tema 5 — Adaptador de ENTRADA: web/REST (secciones 5, 6 y 10 de la teoría)</h2>
 *
 * <p>Un adaptador de entrada permite que algo externo invoque la aplicación.
 * Aquí ese "algo externo" es HTTP: el controlador recibe JSON, lo traduce a un
 * command interno, delega en el puerto de entrada y traduce el response interno
 * a JSON de vuelta. Tres pasos: <b>convertir, delegar, devolver</b>.</p>
 *
 * <p>Piezas del paquete:</p>
 * <ul>
 *   <li>{@code InscripcionController}: el adaptador propiamente dicho (sección 5).</li>
 *   <li>{@code InscribirAlumnoRequest} / {@code InscripcionHttpResponse}: DTOs
 *       EXTERNOS que definen el contrato JSON de la API (sección 6). No son el
 *       command ni el response internos: cada frontera tiene su propio modelo.</li>
 *   <li>{@code ApiExceptionHandler} + {@code ErrorResponse}: traducción de
 *       excepciones de negocio a códigos HTTP (sección 10).</li>
 * </ul>
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación arrancable vive en
 * {@code com.curso.cleanarchitecture.proyecto} y solo escanea ese paquete.
 * Las clases anotadas de aquí compilan pero nunca se registran como beans.
 * La versión funcional equivalente (probable con Postman) está en {@code proyecto}.</p>
 */
package com.curso.cleanarchitecture.tema05.adapter.in.web;

/**
 * <h2>Tema 1 — Primera separación conceptual</h2>
 *
 * <p>Este subpaquete NO implementa todavía la arquitectura completa del curso
 * (eso se hará capa a capa en los temas siguientes). Contiene un único fichero,
 * {@code PrimeraSeparacionConceptual}, que esboza a nivel conceptual cómo se
 * repartirían las responsabilidades que el controlador acoplado mezclaba.</p>
 *
 * <h3>La idea (secciones 8 y 29 de la teoría)</h3>
 * <p>Flujo acoplado del Tema 1:</p>
 * <pre>
 * HTTP → Controller (HTTP + reglas + persistencia + errores) → "BD"
 * </pre>
 * <p>Flujo limpio hacia el que caminamos:</p>
 * <pre>
 * HTTP Request
 *     ↓
 * Controller Adapter        (traduce HTTP, nada más)
 *     ↓
 * Input Port / Use Case     (coordina la acción "inscribir alumno")
 *     ↓
 * Domain Entities           (Curso protege sus propias reglas)
 *     ↓
 * Output Port               (interfaz: qué necesita la aplicación)
 *     ↓
 * Persistence Adapter       (cómo se guarda: JPA, JDBC, memoria...)
 *     ↓
 * Database
 * </pre>
 *
 * <h3>Qué enseña</h3>
 * <ul>
 *   <li>Que la regla "curso cerrado o sin plazas no acepta inscripciones" puede
 *       extraerse del controlador y vivir en la entidad (sección 15).</li>
 *   <li>Que el caso de uso coordina el flujo sin conocer HTTP ni SQL
 *       (sección 16).</li>
 *   <li>Que los puertos (interfaces) permiten que la aplicación declare lo que
 *       necesita sin decidir la tecnología (secciones 11 y 12: regla de
 *       dependencia e inversión de dependencias).</li>
 *   <li>Que con esta separación la regla se prueba con Java puro, sin Spring
 *       (sección 26).</li>
 * </ul>
 *
 * <p><b>Nota docente:</b> nada de este paquete usa anotaciones Spring y nada se
 * ejecuta como bean: es material de lectura para comparar los dos flujos.</p>
 */
package com.curso.cleanarchitecture.tema01.separado;

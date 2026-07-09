/**
 * Puertos de SALIDA (output ports) del Tema 4.
 *
 * <p>Un puerto de salida declara una NECESIDAD de la aplicación hacia el
 * exterior: persistir cursos, buscar alumnos, enviar notificaciones...
 * Quién lo usa y quién lo implementa (secciones 7 y 9 de la teoría):</p>
 *
 * <pre>
 *   UseCase ──usa──▶ OutputPort ◀──implementa── Adaptador (memoria, JPA, API...)
 * </pre>
 *
 * <p>Aquí está la INVERSIÓN DE DEPENDENCIAS en acción:</p>
 * <ul>
 *   <li>Diseño acoplado:      {@code InscribirAlumnoService → SpringDataCursoRepository}
 *       (la aplicación depende de una clase técnica).</li>
 *   <li>Clean Architecture:   {@code InscribirAlumnoUseCase → CursoRepositoryPort ← CursoRepositoryJpaAdapter}
 *       (la aplicación depende de una interfaz QUE ELLA MISMA CONTROLA, y es la
 *       infraestructura quien implementa esa interfaz).</li>
 * </ul>
 *
 * <p>La aplicación define estas interfaces porque es quien tiene la necesidad;
 * la infraestructura las implementa porque es quien conoce la tecnología.
 * Por eso podremos sustituir memoria por JPA, ficheros o una API externa sin
 * tocar una sola línea de los casos de uso.</p>
 *
 * <p>ADVERTENCIA (secciones 9 y 11): un puerto de salida nunca debe devolver
 * entidades JPA. Si {@code CursoRepositoryPort} devolviera
 * {@code CursoJpaEntity}, la aplicación seguiría contaminada por la
 * infraestructura aunque hayamos puesto una interfaz en medio. Los puertos
 * hablan SIEMPRE con modelos del dominio o de la aplicación.</p>
 */
package com.curso.cleanarchitecture.tema04.application.port.out;

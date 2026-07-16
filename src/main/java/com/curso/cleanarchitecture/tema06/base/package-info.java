/**
 * Mini núcleo de soporte del tema 6: dominio + capa de aplicación.
 *
 * <p><b>Nada de este paquete es nuevo.</b> Es un resumen de lo construido en
 * los temas 2 a 4, repetido aquí (simplificado) para que el tema 6 sea
 * autocontenido y podamos centrarnos en lo que sí es nuevo: la infraestructura
 * con Spring Boot, JPA y H2 de los paquetes {@code persistence} y {@code config}.</p>
 *
 * <ul>
 *   <li>Dominio (tema 2): {@link com.curso.cleanarchitecture.tema06.base.Curso},
 *       {@link com.curso.cleanarchitecture.tema06.base.Alumno},
 *       {@link com.curso.cleanarchitecture.tema06.base.Inscripcion},
 *       {@link com.curso.cleanarchitecture.tema06.base.EstadoInscripcion}.</li>
 *   <li>Aplicación (tema 3): puertos de salida
 *       ({@link com.curso.cleanarchitecture.tema06.base.CursoRepositoryPort},
 *       {@link com.curso.cleanarchitecture.tema06.base.AlumnoRepositoryPort},
 *       {@link com.curso.cleanarchitecture.tema06.base.InscripcionRepositoryPort}),
 *       puerto de entrada
 *       ({@link com.curso.cleanarchitecture.tema06.base.InscribirAlumnoInputPort}),
 *       interactor
 *       ({@link com.curso.cleanarchitecture.tema06.base.InscribirAlumnoUseCase})
 *       y sus DTOs ({@code InscribirAlumnoCommand}, {@code InscripcionResponse}).</li>
 * </ul>
 *
 * <p><b>Observación clave para este tema:</b> recorre estas clases buscando
 * imports de Spring o de JPA. No hay ninguno. Todo es {@code java.*}. El
 * dominio no tiene {@code @Entity} y el caso de uso no conoce
 * {@code JpaRepository}: esas piezas técnicas viven en {@code persistence},
 * al otro lado de los puertos. Esa frontera es exactamente lo que el tema 6
 * pone a prueba al enchufar la tecnología real.</p>
 */
package com.curso.cleanarchitecture.tema06.base;

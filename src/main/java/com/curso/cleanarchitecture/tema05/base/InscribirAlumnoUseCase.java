package com.curso.cleanarchitecture.tema05.base;

/**
 * Caso de uso "inscribir alumno" (construido en el tema 3, con puertos en el tema 4).
 *
 * <p>Orquesta el flujo de negocio hablando SOLO con puertos:</p>
 * <ol>
 *   <li>Recupera curso y alumno a través de los puertos de salida.</li>
 *   <li>Aplica la regla de negocio (ocupar plaza) en el dominio.</li>
 *   <li>Persiste los cambios y la nueva inscripción.</li>
 *   <li>Notifica al alumno.</li>
 *   <li>Devuelve un response INTERNO.</li>
 * </ol>
 *
 * <p>Obsérvese lo que NO hay aquí: ni {@code @RestController}, ni
 * {@code ResponseEntity}, ni JPA, ni JSON. Las excepciones que lanza
 * ({@link IllegalArgumentException}, {@link IllegalStateException}) son de
 * negocio; el adaptador web las traducirá a códigos HTTP en
 * {@code ApiExceptionHandler} (sección 10 de la teoría).</p>
 */
public class InscribirAlumnoUseCase implements InscribirAlumnoInputPort {

    private final CursoRepositoryPort cursoRepository;
    private final AlumnoRepositoryPort alumnoRepository;
    private final InscripcionRepositoryPort inscripcionRepository;
    private final NotificacionPort notificacionPort;

    /**
     * Las dependencias entran por constructor como PUERTOS (interfaces).
     * Quién las implementa (memoria, JPA, consola, email) se decide fuera,
     * en la configuración: el caso de uso ni lo sabe ni le importa.
     */
    public InscribirAlumnoUseCase(CursoRepositoryPort cursoRepository,
                                  AlumnoRepositoryPort alumnoRepository,
                                  InscripcionRepositoryPort inscripcionRepository,
                                  NotificacionPort notificacionPort) {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.notificacionPort = notificacionPort;
    }

    @Override
    public InscripcionResponse ejecutar(InscribirAlumnoCommand command) {

        // 1. Recuperar los datos necesarios a través de los puertos de salida.
        //    Si no existen, excepción de negocio (el adaptador web la volverá 400).
        Curso curso = cursoRepository.buscarPorId(command.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el curso con id " + command.getCursoId()));

        Alumno alumno = alumnoRepository.buscarPorId(command.getAlumnoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el alumno con id " + command.getAlumnoId()));

        // 2. Regla de negocio: la decide el DOMINIO, no el controlador.
        curso.ocuparPlaza();

        // 3. Persistir a través de puertos: el caso de uso no sabe si detrás
        //    hay un HashMap, H2 o PostgreSQL.
        cursoRepository.guardar(curso);
        Inscripcion inscripcion = inscripcionRepository.guardar(
                Inscripcion.nueva(curso.getId(), alumno.getId()));

        // 4. Notificar por el canal que sea: eso lo decide el adaptador.
        notificacionPort.notificarInscripcion(alumno, curso);

        // 5. Devolver un response interno. NUNCA un ResponseEntity:
        //    el caso de uso no conoce HTTP.
        return new InscripcionResponse(
                inscripcion.getId(),
                inscripcion.getCursoId(),
                inscripcion.getAlumnoId(),
                inscripcion.getEstado().name());
    }
}

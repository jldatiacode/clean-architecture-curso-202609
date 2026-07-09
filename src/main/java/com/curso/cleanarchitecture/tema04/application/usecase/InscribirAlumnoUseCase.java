package com.curso.cleanarchitecture.tema04.application.usecase;

import com.curso.cleanarchitecture.tema04.application.command.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema04.application.port.in.InscribirAlumnoInputPort;
import com.curso.cleanarchitecture.tema04.application.port.out.AlumnoRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.InscripcionRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.NotificacionPort;
import com.curso.cleanarchitecture.tema04.application.response.InscripcionResponse;
import com.curso.cleanarchitecture.tema04.model.Alumno;
import com.curso.cleanarchitecture.tema04.model.Curso;
import com.curso.cleanarchitecture.tema04.model.Inscripcion;

/**
 * Caso de uso: inscribir un alumno en un curso.
 *
 * <p>Implementa el puerto de entrada {@link InscribirAlumnoInputPort} (sección 6
 * de la teoría): así los adaptadores de entrada dependen del contrato y no de
 * esta clase concreta.</p>
 *
 * <p>Y depende SOLO de puertos de salida (sección 9): mira los imports de esta
 * clase. No hay ni una clase de infraestructura. Esta clase no sabe si los
 * cursos viven en memoria, en PostgreSQL o detrás de una API. Eso es la
 * inversión de dependencias: la aplicación define las interfaces que necesita
 * y la infraestructura las implementa, de modo que las dependencias de código
 * apuntan siempre hacia dentro.</p>
 *
 * <pre>
 *   InscribirAlumnoUseCase → CursoRepositoryPort ← CursoRepositoryJpaAdapter (Tema 5)
 *                                                ← CursoRepositoryFake        (tests)
 * </pre>
 */
public class InscribirAlumnoUseCase implements InscribirAlumnoInputPort {

    // Todas las dependencias son ABSTRACCIONES (interfaces) definidas por la
    // propia capa de aplicación. Nunca clases técnicas concretas.
    private final CursoRepositoryPort cursoRepository;
    private final AlumnoRepositoryPort alumnoRepository;
    private final InscripcionRepositoryPort inscripcionRepository;
    private final NotificacionPort notificacionPort;

    /**
     * Inyección de dependencias por constructor, a mano y sin framework.
     * Quien construya el caso de uso decide QUÉ implementaciones entregar;
     * el caso de uso solo exige que cumplan los contratos.
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

    /**
     * Flujo completo del caso de uso. Léelo de arriba abajo: es la
     * descripción ejecutable de la regla de negocio, sin ruido técnico.
     */
    @Override
    public InscripcionResponse ejecutar(InscribirAlumnoCommand command) {

        // 1. Recuperar el curso a través del puerto de salida.
        //    El Optional obliga a decidir qué pasa si no existe.
        Curso curso = cursoRepository.buscarPorId(command.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el curso con id " + command.getCursoId()));

        // 2. Recuperar el alumno. Misma técnica, mismo lenguaje de dominio.
        Alumno alumno = alumnoRepository.buscarPorId(command.getAlumnoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el alumno con id " + command.getAlumnoId()));

        // 3. Regla de aplicación: no se admite inscripción duplicada.
        //    La consulta habla lenguaje de negocio ("existe inscripción activa"),
        //    no lenguaje SQL.
        if (inscripcionRepository.existeInscripcionActiva(curso.getId(), alumno.getId())) {
            throw new IllegalStateException(
                    "El alumno " + alumno.getId() + " ya tiene una inscripción activa en el curso " + curso.getId());
        }

        // 4. Regla de dominio: ocupar plaza. Si el curso está cerrado o sin
        //    plazas, la ENTIDAD lanza IllegalStateException. El caso de uso
        //    orquesta; la invariante vive en el dominio.
        curso.ocuparPlaza();
        cursoRepository.guardar(curso);

        // 5. Crear la inscripción (nace ACTIVA y sin id) y persistirla.
        //    El id lo asigna el adaptador de persistencia al guardar.
        Inscripcion inscripcion = new Inscripcion(null, curso.getId(), alumno.getId());
        inscripcionRepository.guardar(inscripcion);

        // 6. Notificar la confirmación a través del puerto de salida.
        //    El caso de uso no sabe si será email, SMS o evento: solo pide
        //    "confirma esta inscripción" y el adaptador decide el canal.
        notificacionPort.enviarConfirmacionInscripcion(alumno.getId(), curso.getId());

        // 7. Devolver un response (contrato de salida del input port),
        //    nunca la entidad de dominio y muchísimo menos una entidad JPA.
        return new InscripcionResponse(
                inscripcion.getId(),
                inscripcion.getCursoId(),
                inscripcion.getAlumnoId(),
                inscripcion.getEstado().name());
    }
}

package com.curso.cleanarchitecture.tema04.application.usecase;

import com.curso.cleanarchitecture.tema04.application.command.SolicitarListaEsperaCommand;
import com.curso.cleanarchitecture.tema04.application.port.in.SolicitarListaEsperaInputPort;
import com.curso.cleanarchitecture.tema04.application.port.out.AlumnoRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.InscripcionRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.ListaEsperaRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.port.out.NotificacionPort;
import com.curso.cleanarchitecture.tema04.application.response.SolicitudListaEsperaResponse;
import com.curso.cleanarchitecture.tema04.model.Alumno;
import com.curso.cleanarchitecture.tema04.model.Curso;
import com.curso.cleanarchitecture.tema04.model.SolicitudListaEspera;

/**
 * Caso de uso que solicita la incorporación de un alumno a la lista de espera.
 *
 * <p>Este ejemplo amplía el proyecto transversal sin acoplar la aplicación a
 * una tecnología. La clase depende exclusivamente de puertos de salida.</p>
 *
 * <pre>
 * SolicitarListaEsperaUseCase
 *      → CursoRepositoryPort
 *      → AlumnoRepositoryPort
 *      → InscripcionRepositoryPort
 *      → ListaEsperaRepositoryPort
 *      → NotificacionPort
 * </pre>
 */
public class SolicitarListaEsperaUseCase implements SolicitarListaEsperaInputPort {

    private final CursoRepositoryPort cursoRepository;
    private final AlumnoRepositoryPort alumnoRepository;
    private final InscripcionRepositoryPort inscripcionRepository;
    private final ListaEsperaRepositoryPort listaEsperaRepository;
    private final NotificacionPort notificacionPort;

    public SolicitarListaEsperaUseCase(CursoRepositoryPort cursoRepository,
                                       AlumnoRepositoryPort alumnoRepository,
                                       InscripcionRepositoryPort inscripcionRepository,
                                       ListaEsperaRepositoryPort listaEsperaRepository,
                                       NotificacionPort notificacionPort) {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.listaEsperaRepository = listaEsperaRepository;
        this.notificacionPort = notificacionPort;
    }

    @Override
    public SolicitudListaEsperaResponse ejecutar(SolicitarListaEsperaCommand command) {

        // 1. Obtener las entidades necesarias mediante puertos de salida.
        Curso curso = cursoRepository.buscarPorId(command.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el curso con id " + command.getCursoId()));

        Alumno alumno = alumnoRepository.buscarPorId(command.getAlumnoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el alumno con id " + command.getAlumnoId()));

        // 2. Pedir al dominio que valide si la lista de espera tiene sentido.
        //    La entidad Curso protege las reglas relativas a su propio estado.
        curso.validarPuedeSolicitarListaEspera();

        // 3. Regla de aplicación: un alumno ya inscrito no puede entrar en espera.
        if (inscripcionRepository.existeInscripcionActiva(curso.getId(), alumno.getId())) {
            throw new IllegalStateException(
                    "El alumno ya tiene una inscripción activa en el curso");
        }

        // 4. Regla de aplicación: evitar solicitudes pendientes duplicadas.
        if (listaEsperaRepository.existeSolicitudPendiente(curso.getId(), alumno.getId())) {
            throw new IllegalStateException(
                    "El alumno ya tiene una solicitud pendiente en la lista de espera");
        }

        // 5. Crear la nueva entidad de dominio y persistirla a través del puerto.
        SolicitudListaEspera solicitud = new SolicitudListaEspera(
                null, curso.getId(), alumno.getId());
        listaEsperaRepository.guardar(solicitud);

        // 6. Solicitar una notificación sin conocer el canal concreto.
        notificacionPort.enviarConfirmacionListaEspera(alumno.getId(), curso.getId());

        // 7. Devolver un response propio de la capa de aplicación.
        return new SolicitudListaEsperaResponse(
                solicitud.getId(),
                solicitud.getCursoId(),
                solicitud.getAlumnoId(),
                solicitud.getEstado().name(),
                solicitud.getFechaSolicitud());
    }
}

package com.curso.cleanarchitecture.tema04.demo;

import com.curso.cleanarchitecture.tema04.application.command.SolicitarListaEsperaCommand;
import com.curso.cleanarchitecture.tema04.application.port.in.SolicitarListaEsperaInputPort;
import com.curso.cleanarchitecture.tema04.application.response.SolicitudListaEsperaResponse;
import com.curso.cleanarchitecture.tema04.application.usecase.SolicitarListaEsperaUseCase;
import com.curso.cleanarchitecture.tema04.fake.AlumnoRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.CursoRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.InscripcionRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.ListaEsperaRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.NotificacionFake;
import com.curso.cleanarchitecture.tema04.model.Alumno;
import com.curso.cleanarchitecture.tema04.model.Curso;

/**
 * Demostración ejecutable sin Spring y sin base de datos.
 *
 * <p>En Eclipse: botón derecho sobre la clase → Run As → Java Application.</p>
 */
public class DemoListaEsperaTema04 {

    public static void main(String[] args) {
        CursoRepositoryFake cursoRepository = new CursoRepositoryFake();
        AlumnoRepositoryFake alumnoRepository = new AlumnoRepositoryFake();
        InscripcionRepositoryFake inscripcionRepository = new InscripcionRepositoryFake();
        ListaEsperaRepositoryFake listaEsperaRepository = new ListaEsperaRepositoryFake();
        NotificacionFake notificacionPort = new NotificacionFake();

        // Curso abierto, pero sin plazas: escenario válido para lista de espera.
        cursoRepository.guardar(new Curso(10L, "Clean Architecture con Java", 0, false));
        alumnoRepository.guardar(new Alumno(7L, "Ana García", "ana@example.com"));

        SolicitarListaEsperaInputPort useCase = new SolicitarListaEsperaUseCase(
                cursoRepository,
                alumnoRepository,
                inscripcionRepository,
                listaEsperaRepository,
                notificacionPort);

        SolicitudListaEsperaResponse response = useCase.ejecutar(
                new SolicitarListaEsperaCommand(10L, 7L));

        System.out.println("Solicitud creada con id: " + response.getSolicitudId());
        System.out.println("Estado: " + response.getEstado());
        System.out.println("Notificaciones de lista de espera: "
                + notificacionPort.getListaEsperaEnviadas().size());
    }
}

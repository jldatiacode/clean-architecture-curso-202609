package com.curso.cleanarchitecture.tema04.application.port.out;

import java.util.List;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.model.SolicitudListaEspera;

/**
 * Puerto de salida especializado en persistir solicitudes de lista de espera.
 *
 * <p>El contrato está expresado en lenguaje de negocio y no menciona JPA,
 * SQL, tablas ni ninguna tecnología concreta.</p>
 */
public interface ListaEsperaRepositoryPort {

    Optional<SolicitudListaEspera> buscarPorId(Long solicitudId);

    boolean existeSolicitudPendiente(Long cursoId, Long alumnoId);

    List<SolicitudListaEspera> buscarPendientesPorCursoId(Long cursoId);

    void guardar(SolicitudListaEspera solicitud);
}

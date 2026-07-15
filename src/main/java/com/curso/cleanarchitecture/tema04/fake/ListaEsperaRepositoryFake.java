package com.curso.cleanarchitecture.tema04.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.application.port.out.ListaEsperaRepositoryPort;
import com.curso.cleanarchitecture.tema04.model.SolicitudListaEspera;

/**
 * Adaptador fake que guarda las solicitudes de lista de espera en memoria.
 *
 * <p>Sirve para ejecutar y probar el caso de uso sin base de datos real.</p>
 */
public class ListaEsperaRepositoryFake implements ListaEsperaRepositoryPort {

    private final Map<Long, SolicitudListaEspera> solicitudes = new HashMap<>();
    private long siguienteId = 1L;

    @Override
    public Optional<SolicitudListaEspera> buscarPorId(Long solicitudId) {
        return Optional.ofNullable(solicitudes.get(solicitudId));
    }

    @Override
    public boolean existeSolicitudPendiente(Long cursoId, Long alumnoId) {
        return solicitudes.values().stream()
                .anyMatch(solicitud -> solicitud.getCursoId().equals(cursoId)
                        && solicitud.getAlumnoId().equals(alumnoId)
                        && solicitud.estaPendiente());
    }

    @Override
    public List<SolicitudListaEspera> buscarPendientesPorCursoId(Long cursoId) {
        List<SolicitudListaEspera> resultado = new ArrayList<>();
        for (SolicitudListaEspera solicitud : solicitudes.values()) {
            if (solicitud.getCursoId().equals(cursoId) && solicitud.estaPendiente()) {
                resultado.add(solicitud);
            }
        }
        return resultado;
    }

    @Override
    public void guardar(SolicitudListaEspera solicitud) {
        if (solicitud.getId() == null) {
            solicitud.asignarId(siguienteId++);
        }
        solicitudes.put(solicitud.getId(), solicitud);
    }
}

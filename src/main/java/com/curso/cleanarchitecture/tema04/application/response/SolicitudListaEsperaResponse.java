package com.curso.cleanarchitecture.tema04.application.response;

import java.time.LocalDateTime;

/**
 * Datos de salida publicados por el caso de uso de lista de espera.
 *
 * <p>No se devuelve directamente la entidad de dominio, lo que permite que el
 * contrato de aplicación evolucione sin exponer internamente el dominio.</p>
 */
public class SolicitudListaEsperaResponse {

    private final Long solicitudId;
    private final Long cursoId;
    private final Long alumnoId;
    private final String estado;
    private final LocalDateTime fechaSolicitud;

    public SolicitudListaEsperaResponse(Long solicitudId,
                                        Long cursoId,
                                        Long alumnoId,
                                        String estado,
                                        LocalDateTime fechaSolicitud) {
        this.solicitudId = solicitudId;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = estado;
        this.fechaSolicitud = fechaSolicitud;
    }

    public Long getSolicitudId() {
        return solicitudId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
}

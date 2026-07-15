package com.curso.cleanarchitecture.tema04.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa la petición de un alumno para entrar en
 * la lista de espera de un curso sin plazas.
 *
 * <p>La entidad no conoce repositorios, JPA, Spring ni HTTP. Únicamente
 * representa el estado y protege sus propias reglas.</p>
 */
public class SolicitudListaEspera {

    private Long id;
    private final Long cursoId;
    private final Long alumnoId;
    private final LocalDateTime fechaSolicitud;
    private EstadoSolicitudListaEspera estado;

    /**
     * Crea una solicitud nueva en estado PENDIENTE.
     *
     * @param id identificador; puede ser null antes de persistir
     * @param cursoId identificador del curso
     * @param alumnoId identificador del alumno
     */
    public SolicitudListaEspera(Long id, Long cursoId, Long alumnoId) {
        if (cursoId == null || alumnoId == null) {
            throw new IllegalArgumentException(
                    "Una solicitud de lista de espera necesita cursoId y alumnoId");
        }

        this.id = id;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = EstadoSolicitudListaEspera.PENDIENTE;
    }

    /**
     * Marca la solicitud como avisada cuando se comunica que existe una plaza.
     */
    public void marcarComoAvisada() {
        if (estado != EstadoSolicitudListaEspera.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se puede avisar una solicitud que esté pendiente");
        }
        estado = EstadoSolicitudListaEspera.AVISADA;
    }

    /**
     * Cancela una solicitud pendiente.
     */
    public void cancelar() {
        if (estado != EstadoSolicitudListaEspera.PENDIENTE) {
            throw new IllegalStateException(
                    "Solo se puede cancelar una solicitud que esté pendiente");
        }
        estado = EstadoSolicitudListaEspera.CANCELADA;
    }

    /**
     * Asigna el id generado por el adaptador de persistencia.
     */
    public void asignarId(Long nuevoId) {
        if (nuevoId == null) {
            throw new IllegalArgumentException("El identificador no puede ser null");
        }
        if (id != null) {
            throw new IllegalStateException("La solicitud ya tiene id: " + id);
        }
        id = nuevoId;
    }

    public boolean estaPendiente() {
        return estado == EstadoSolicitudListaEspera.PENDIENTE;
    }

    public Long getId() {
        return id;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public EstadoSolicitudListaEspera getEstado() {
        return estado;
    }
}

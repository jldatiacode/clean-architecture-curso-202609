package com.curso.cleanarchitecture.tema04.application.response;

/**
 * Datos de salida que la aplicación publica sobre una inscripción.
 *
 * <p>Fíjate en que el estado se expone como {@code String} y no como el enum
 * de dominio {@code EstadoInscripcion}. Es una decisión deliberada: el response
 * pertenece al contrato público de la aplicación y no queremos que los
 * adaptadores de entrada dependan de tipos internos del dominio.</p>
 */
public class InscripcionResponse {

    private final Long inscripcionId;
    private final Long cursoId;
    private final Long alumnoId;
    private final String estado;

    public InscripcionResponse(Long inscripcionId, Long cursoId, Long alumnoId, String estado) {
        this.inscripcionId = inscripcionId;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = estado;
    }

    public Long getInscripcionId() {
        return inscripcionId;
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
}

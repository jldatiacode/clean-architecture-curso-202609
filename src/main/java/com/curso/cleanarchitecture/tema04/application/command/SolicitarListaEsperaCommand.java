package com.curso.cleanarchitecture.tema04.application.command;

/**
 * Datos de entrada del caso de uso "solicitar incorporación a lista de espera".
 *
 * <p>El command representa una intención de la aplicación y no depende de
 * HTTP, formularios, JSON ni Spring.</p>
 */
public class SolicitarListaEsperaCommand {

    private final Long cursoId;
    private final Long alumnoId;

    public SolicitarListaEsperaCommand(Long cursoId, Long alumnoId) {
        if (cursoId == null || alumnoId == null) {
            throw new IllegalArgumentException("cursoId y alumnoId son obligatorios");
        }
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }
}

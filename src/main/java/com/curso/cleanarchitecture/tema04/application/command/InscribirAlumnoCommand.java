package com.curso.cleanarchitecture.tema04.application.command;

/**
 * Datos de entrada para la acción "inscribir alumno en curso".
 *
 * <p>Es el command del caso de uso central del proyecto transversal. Llegará
 * desde cualquier adaptador de entrada (REST, test, CLI) y siempre con la misma
 * forma: el contrato no cambia aunque cambie la tecnología que lo invoque.</p>
 */
public class InscribirAlumnoCommand {

    private final Long cursoId;
    private final Long alumnoId;

    public InscribirAlumnoCommand(Long cursoId, Long alumnoId) {
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

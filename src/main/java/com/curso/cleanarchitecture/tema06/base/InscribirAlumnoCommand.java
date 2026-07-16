package com.curso.cleanarchitecture.tema06.base;

/**
 * Command de entrada del caso de uso — visto en el tema 3.
 *
 * <p>Objeto inmutable y sin lógica que transporta los datos que el caso de
 * uso necesita. No sabe nada de HTTP ni de JSON: en el tema 5 vimos que el
 * controlador REST traduce su request a este command. Sin Lombok y sin
 * records por decisión del curso: constructor + getters explícitos.</p>
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

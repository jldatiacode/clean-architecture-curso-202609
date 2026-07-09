package com.curso.cleanarchitecture.tema03.application.response;

/**
 * Resultado del caso de uso {@code InscribirAlumnoUseCase}
 * (reproducido de la sección 7 de la teoría).
 *
 * <p>Observa que el estado viaja como {@code String} y no como el enum
 * {@code EstadoInscripcion} del dominio. Es una decisión deliberada de la
 * teoría: la response es un objeto de frontera de la capa de aplicación y
 * evita que los consumidores externos se acoplen a tipos del dominio.</p>
 *
 * <p>Es inmutable: representa una foto del resultado, no un objeto vivo.</p>
 */
public class InscripcionResponse {

    /** Identificador de la inscripción creada. */
    private final Long inscripcionId;

    /** Identificador del curso en el que se inscribió el alumno. */
    private final Long cursoId;

    /** Identificador del alumno inscrito. */
    private final Long alumnoId;

    /** Estado de la inscripción como texto (por ejemplo, "ACTIVA"). */
    private final String estado;

    public InscripcionResponse(Long inscripcionId, Long cursoId, Long alumnoId, String estado) {
        this.inscripcionId = inscripcionId;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = estado;
    }

    public Long getInscripcionId() { return inscripcionId; }
    public Long getCursoId() { return cursoId; }
    public Long getAlumnoId() { return alumnoId; }
    public String getEstado() { return estado; }
}

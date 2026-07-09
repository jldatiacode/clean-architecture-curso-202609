package com.curso.cleanarchitecture.tema03.application.command;

/**
 * Objeto de entrada del caso de uso {@code InscribirAlumnoUseCase}
 * (reproducido de la sección 7 de la teoría).
 *
 * <p>Características de un buen command:</p>
 * <ul>
 *   <li><b>Inmutable</b>: campos {@code final}, sin setters. Una vez creado,
 *       la intención del llamante no puede cambiar por el camino.</li>
 *   <li><b>Solo datos de entrada</b>: identificadores y valores primarios.
 *       No transporta entidades del dominio ni lógica.</li>
 *   <li><b>Independiente de la entrega</b>: no sabe si estos datos llegaron
 *       por HTTP, por consola, por un mensaje de Kafka o desde un test.</li>
 * </ul>
 */
public class InscribirAlumnoCommand {

    /** Identificador del curso en el que se quiere inscribir al alumno. */
    private final Long cursoId;

    /** Identificador del alumno que se quiere inscribir. */
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

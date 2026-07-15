package com.curso.cleanarchitecture.tema05.base;

/**
 * Command del caso de uso "inscribir alumno" (visto en temas 3 y 4).
 *
 * <p>Es el DTO de entrada INTERNO de la aplicación. No confundir con
 * {@code InscribirAlumnoRequest} (paquete {@code adapter.in.web}), que es el
 * DTO EXTERNO que viaja por HTTP:</p>
 * <ul>
 *   <li>El request HTTP solo trae {@code alumnoId}; el {@code cursoId} llega
 *       en la URL. Es una decisión de diseño de la API.</li>
 *   <li>El command reúne TODO lo que el caso de uso necesita, venga de donde
 *       venga. El caso de uso no sabe que existió una URL.</li>
 * </ul>
 *
 * <p>Esa traducción request → command es exactamente el trabajo del adaptador
 * de entrada.</p>
 */
public class InscribirAlumnoCommand {

    private final Long cursoId;
    private final Long alumnoId;

    public InscribirAlumnoCommand(Long cursoId, Long alumnoId) {
        if (cursoId == null) {
            throw new IllegalArgumentException("El cursoId es obligatorio");
        }
        if (alumnoId == null) {
            throw new IllegalArgumentException("El alumnoId es obligatorio");
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

package com.curso.cleanarchitecture.tema05.base;

/**
 * Response INTERNO del caso de uso "inscribir alumno" (visto en temas 3 y 4).
 *
 * <p>Importante: el caso de uso devuelve ESTE objeto, nunca un
 * {@code ResponseEntity} ni un código HTTP. Si el caso de uso devolviera
 * respuestas HTTP quedaría acoplado a la web y no podríamos invocarlo desde
 * un proceso batch, un consumidor de eventos o un test unitario puro.</p>
 *
 * <p>La conversión a {@code InscripcionHttpResponse} (el DTO externo) la hace
 * el controlador, que para eso es el adaptador de entrada.</p>
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

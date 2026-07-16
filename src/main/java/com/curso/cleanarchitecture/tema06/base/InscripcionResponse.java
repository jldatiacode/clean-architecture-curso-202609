package com.curso.cleanarchitecture.tema06.base;

/**
 * Response de salida del caso de uso — visto en el tema 3.
 *
 * <p>El caso de uso no devuelve la entidad de dominio "en crudo": devuelve
 * este DTO plano, pensado para quien consume el caso de uso. Así el dominio
 * no se filtra hacia fuera y podemos cambiarlo sin romper a los clientes.</p>
 */
public class InscripcionResponse {

    private final Long cursoId;
    private final Long alumnoId;
    private final String nombreCurso;
    private final String nombreAlumno;
    private final String estado;

    public InscripcionResponse(Long cursoId, Long alumnoId,
                               String nombreCurso, String nombreAlumno,
                               String estado) {
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.nombreCurso = nombreCurso;
        this.nombreAlumno = nombreAlumno;
        this.estado = estado;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public String getEstado() {
        return estado;
    }
}

package com.curso.cleanarchitecture.tema05.base;

/**
 * Modelo de dominio {@code Inscripcion}: relaciona un alumno con un curso
 * (visto en temas 3 y 4).
 *
 * <p>El {@code id} puede ser {@code null} mientras la inscripción no ha sido
 * persistida: es el adaptador de persistencia quien asigna identificadores,
 * porque generar ids es un detalle de infraestructura, no de negocio.</p>
 */
public class Inscripcion {

    private final Long id;
    private final Long cursoId;
    private final Long alumnoId;
    private final EstadoInscripcion estado;

    public Inscripcion(Long id, Long cursoId, Long alumnoId, EstadoInscripcion estado) {
        if (cursoId == null || alumnoId == null) {
            throw new IllegalArgumentException("La inscripción necesita curso y alumno");
        }
        if (estado == null) {
            throw new IllegalArgumentException("La inscripción necesita un estado");
        }
        this.id = id;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = estado;
    }

    /** Fábrica de conveniencia: inscripción nueva, aún sin id, en estado ACTIVA. */
    public static Inscripcion nueva(Long cursoId, Long alumnoId) {
        return new Inscripcion(null, cursoId, alumnoId, EstadoInscripcion.ACTIVA);
    }

    /** Copia de esta inscripción con el id asignado por la persistencia. */
    public Inscripcion conId(Long nuevoId) {
        return new Inscripcion(nuevoId, cursoId, alumnoId, estado);
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

    public EstadoInscripcion getEstado() {
        return estado;
    }
}

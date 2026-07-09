package com.curso.cleanarchitecture.tema03.model;

/**
 * Entidad de dominio {@code Inscripcion}.
 *
 * <p><b>Evolución respecto al Tema 2</b> (ver package-info de este paquete):
 * la inscripción ya no referencia objetos {@code Curso} y {@code Alumno}
 * completos, sino sus <b>identificadores</b> ({@code cursoId} y
 * {@code alumnoId}), tal y como hace la teoría del Tema 3 (sección 9:
 * {@code new Inscripcion(null, curso.getId(), alumno.getId())}). Esto
 * simplifica la persistencia y evita cargar grafos de objetos completos.</p>
 *
 * <p>REGLA DE NEGOCIO que protege esta entidad: una inscripción cancelada no
 * puede volver a cancelarse (sección 5 de la teoría).</p>
 */
public class Inscripcion {

    /**
     * Identificador de la inscripción. No es final porque lo asigna el
     * mecanismo de persistencia DESPUÉS de crear el objeto: el caso de uso
     * crea la inscripción con id null y el repositorio le asigna uno al
     * guardarla (igual que haría una base de datos con una columna
     * autoincremental).
     */
    private Long id;

    /** Identificador del curso al que se inscribe el alumno. */
    private final Long cursoId;

    /** Identificador del alumno inscrito. */
    private final Long alumnoId;

    /** Estado actual de la inscripción. */
    private EstadoInscripcion estado;

    /**
     * Crea una inscripción. Toda inscripción nace en estado
     * {@link EstadoInscripcion#ACTIVA}: es una invariante del dominio, no una
     * decisión del caso de uso, por eso se fija aquí y no fuera.
     *
     * @param id       identificador (null si aún no está persistida)
     * @param cursoId  identificador del curso
     * @param alumnoId identificador del alumno
     */
    public Inscripcion(Long id, Long cursoId, Long alumnoId) {
        this.id = id;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = EstadoInscripcion.ACTIVA;
    }

    /**
     * REGLA DE NEGOCIO: cancela la inscripción.
     *
     * <p>Lanza {@link IllegalStateException} si ya estaba cancelada, porque el
     * problema es el estado del objeto, no los argumentos recibidos.</p>
     */
    public void cancelar() {
        if (estado == EstadoInscripcion.CANCELADA) {
            throw new IllegalStateException("La inscripción ya está cancelada");
        }
        this.estado = EstadoInscripcion.CANCELADA;
    }

    /**
     * Asigna el identificador tras la persistencia.
     *
     * <p>Este método existe para que el repositorio (o su fake en memoria)
     * pueda simular lo que hace una base de datos con un id autoincremental.
     * Se protege para que solo pueda asignarse una vez: reasignar la identidad
     * de una entidad ya persistida sería un error grave.</p>
     *
     * @param id identificador asignado por la persistencia
     */
    public void asignarId(Long id) {
        if (this.id != null) {
            throw new IllegalStateException("La inscripción ya tiene un id asignado");
        }
        this.id = id;
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

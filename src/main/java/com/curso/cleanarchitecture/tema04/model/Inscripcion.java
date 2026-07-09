package com.curso.cleanarchitecture.tema04.model;

/**
 * Entidad de dominio Inscripción.
 *
 * <p>Relaciona un alumno con un curso mediante sus identificadores. Nace en
 * estado {@link EstadoInscripcion#ACTIVA} y solo puede transitar a
 * {@link EstadoInscripcion#CANCELADA} a través de {@link #cancelar()}, que
 * protege la regla de negocio "no se puede cancelar dos veces".</p>
 *
 * <p>Detalle docente sobre el identificador: quién asigna el id es una
 * responsabilidad de la PERSISTENCIA (una secuencia de base de datos, un
 * contador en memoria...). Por eso la inscripción puede nacer sin id y el
 * adaptador de salida se lo asigna una única vez al guardarla. El caso de uso
 * no sabe ni le importa cómo se genera.</p>
 */
public class Inscripcion {

    private Long id;
    private final Long cursoId;
    private final Long alumnoId;
    private EstadoInscripcion estado;

    /**
     * @param id       identificador; puede ser null si aún no se ha persistido
     * @param cursoId  identificador del curso al que se inscribe el alumno
     * @param alumnoId identificador del alumno inscrito
     */
    public Inscripcion(Long id, Long cursoId, Long alumnoId) {
        if (cursoId == null || alumnoId == null) {
            throw new IllegalArgumentException("Una inscripción necesita cursoId y alumnoId");
        }
        this.id = id;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = EstadoInscripcion.ACTIVA;
    }

    /**
     * Cancela la inscripción.
     *
     * <p>Regla de negocio: una inscripción ya cancelada no puede cancelarse de
     * nuevo. De nuevo, {@link IllegalStateException} porque el estado actual
     * no permite la transición.</p>
     */
    public void cancelar() {
        if (estado == EstadoInscripcion.CANCELADA) {
            throw new IllegalStateException(
                    "La inscripción " + id + " ya está cancelada");
        }
        this.estado = EstadoInscripcion.CANCELADA;
    }

    /**
     * Asigna el identificador definitivo. Pensado para que lo invoque el
     * adaptador de persistencia (en este tema, los fakes en memoria) una única
     * vez. Reasignar un id es un error de programación, de ahí la excepción.
     */
    public void asignarId(Long nuevoId) {
        if (this.id != null) {
            throw new IllegalStateException(
                    "La inscripción ya tiene id asignado: " + this.id);
        }
        this.id = nuevoId;
    }

    /** Indica si la inscripción sigue vigente. */
    public boolean estaActiva() {
        return estado == EstadoInscripcion.ACTIVA;
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

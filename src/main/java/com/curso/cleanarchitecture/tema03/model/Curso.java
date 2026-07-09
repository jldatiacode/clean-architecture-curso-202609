package com.curso.cleanarchitecture.tema03.model;

/**
 * Entidad de dominio {@code Curso}.
 *
 * <p>Contiene <b>reglas de negocio</b> (sección 5 de la teoría): normas que
 * existen aunque cambiemos Java, Spring, la base de datos o la interfaz.</p>
 *
 * <p>Reglas de negocio que protege esta entidad:</p>
 * <ul>
 *   <li>Un curso cerrado no acepta inscripciones.</li>
 *   <li>Un curso sin plazas no acepta más alumnos.</li>
 * </ul>
 *
 * <p>Fíjate en que estas reglas viven AQUÍ, en el dominio, y no en el caso de
 * uso. El caso de uso (capa de aplicación) se limita a <b>pedir</b> al curso
 * que ocupe una plaza; es el curso quien decide si puede o no. Si las reglas
 * vivieran en el caso de uso, la entidad sería anémica y cualquier otro flujo
 * podría saltarse la validación.</p>
 */
public class Curso {

    /** Identificador del curso. Lo asigna el mecanismo de persistencia. */
    private final Long id;

    /** Nombre descriptivo del curso. */
    private final String nombre;

    /**
     * Plazas que quedan libres. Es estado mutable del dominio: cambia al
     * ocupar o liberar plazas, siempre a través de métodos con reglas.
     */
    private int plazasDisponibles;

    /** Indica si el curso está cerrado y ya no admite inscripciones. */
    private boolean cerrado;

    /**
     * Construye un curso.
     *
     * @param id                identificador (puede ser null si aún no persiste)
     * @param nombre            nombre del curso
     * @param plazasDisponibles número de plazas libres iniciales
     * @param cerrado           true si el curso no admite inscripciones
     */
    public Curso(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
    }

    /**
     * REGLA DE NEGOCIO: valida que el curso puede recibir una inscripción.
     *
     * <p>Se lanza {@link IllegalStateException} porque el objeto está en un
     * estado que no permite la operación (curso cerrado o sin plazas). No es
     * un problema de argumentos del llamante, es un problema de estado del
     * dominio: por eso NO usamos {@code IllegalArgumentException}.</p>
     */
    public void validarPuedeRecibirInscripcion() {
        if (cerrado) {
            throw new IllegalStateException("El curso está cerrado y no admite inscripciones");
        }
        if (plazasDisponibles <= 0) {
            throw new IllegalStateException("El curso no tiene plazas disponibles");
        }
    }

    /**
     * Ocupa una plaza del curso.
     *
     * <p>Primero valida las reglas de negocio y solo después muta el estado.
     * El caso de uso llamará a este método sin conocer los detalles de la
     * validación: la entidad es la guardiana de sus invariantes.</p>
     */
    public void ocuparPlaza() {
        validarPuedeRecibirInscripcion();
        plazasDisponibles--;
    }

    /**
     * Libera una plaza del curso (por ejemplo, al cancelar una inscripción).
     *
     * <p>La incluimos ya en este tema porque el caso de uso
     * {@code CancelarInscripcionUseCase} (identificado en la sección 4 de la
     * teoría) la necesitará.</p>
     */
    public void liberarPlaza() {
        plazasDisponibles++;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public boolean isCerrado() {
        return cerrado;
    }
}

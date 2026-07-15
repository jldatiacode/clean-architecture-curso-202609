package com.curso.cleanarchitecture.tema04.model;

/**
 * Entidad de dominio Curso.
 *
 * <p>Contiene las reglas de negocio sobre la ocupación de plazas. Fíjate en que
 * las invariantes se protegen AQUÍ, dentro del dominio, y no en el caso de uso
 * ni en la base de datos: nadie puede ocupar una plaza de un curso cerrado o
 * sin plazas, porque la propia entidad lo impide con una excepción.</p>
 *
 * <p>Esta clase es la que viajará por los puertos ({@code CursoRepositoryPort}
 * devuelve {@code Curso}, nunca una entidad JPA). Así el contrato entre capas
 * habla lenguaje de dominio y no de tecnología.</p>
 */
public class Curso {

    private final Long id;
    private final String nombre;
    private int plazasDisponibles;
    private boolean cerrado;

    /**
     * Constructor completo. Sin Lombok y sin records: escribimos el constructor
     * y los getters a mano para que se vea explícitamente el estado que gestiona
     * la entidad.
     *
     * @param id                identificador del curso
     * @param nombre            nombre descriptivo del curso
     * @param plazasDisponibles número de plazas libres en este momento
     * @param cerrado           true si el curso no admite más inscripciones
     */
    public Curso(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        if (plazasDisponibles < 0) {
            throw new IllegalArgumentException("Las plazas disponibles no pueden ser negativas");
        }
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
    }

    /**
     * Ocupa una plaza del curso.
     *
     * <p>Regla de negocio: no se puede ocupar plaza en un curso cerrado ni en un
     * curso sin plazas. La entidad se protege a sí misma con
     * {@link IllegalStateException}: el estado actual no permite la operación.</p>
     */
    public void ocuparPlaza() {
        if (cerrado) {
            throw new IllegalStateException(
                    "No se puede ocupar plaza: el curso '" + nombre + "' está cerrado");
        }
        if (plazasDisponibles <= 0) {
            throw new IllegalStateException(
                    "No se puede ocupar plaza: el curso '" + nombre + "' no tiene plazas disponibles");
        }
        plazasDisponibles--;
    }

    /**
     * Libera una plaza previamente ocupada (por ejemplo, al cancelar una inscripción).
     *
     * <p>Regla de negocio: un curso cerrado ya no gestiona plazas, por lo que
     * liberar sobre un curso cerrado también es un estado ilegal.</p>
     */
    public void liberarPlaza() {
        if (cerrado) {
            throw new IllegalStateException(
                    "No se puede liberar plaza: el curso '" + nombre + "' está cerrado");
        }
        plazasDisponibles++;
    }

    /** Indica si el curso admite inscripciones: abierto y con plazas libres. */
    public boolean tienePlazasLibres() {
        return !cerrado && plazasDisponibles > 0;
    }

    /**
     * Valida que tenga sentido solicitar la incorporación a lista de espera.
     *
     * <p>La lista de espera solo se utiliza cuando el curso está abierto pero
     * no quedan plazas. Si todavía hay plazas, debe realizarse una inscripción
     * normal; si está cerrado, no se admite ninguna solicitud.</p>
     */
    public void validarPuedeSolicitarListaEspera() {
        if (cerrado) {
            throw new IllegalStateException(
                    "No se puede solicitar lista de espera: el curso '" + nombre + "' está cerrado");
        }
        if (plazasDisponibles > 0) {
            throw new IllegalStateException(
                    "El curso '" + nombre + "' todavía tiene plazas; debe realizarse una inscripción directa");
        }
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

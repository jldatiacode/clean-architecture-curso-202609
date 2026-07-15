package com.curso.cleanarchitecture.tema05.base;

/**
 * Modelo de dominio {@code Curso}, tal y como quedó en los temas 3 y 4.
 *
 * <p>Es un objeto de negocio puro: sin anotaciones de JPA, sin anotaciones de
 * Spring, sin dependencias de frameworks. Compárese con
 * {@code CursoJpaEntity} (paquete {@code adapter.out.persistence.jpa}):
 * aquella representa PERSISTENCIA (una fila de la tabla {@code cursos});
 * esta representa NEGOCIO (un curso con reglas sobre sus plazas).</p>
 */
public class Curso {

    private final Long id;
    private final String nombre;
    private int plazasDisponibles;
    private boolean cerrado;

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
     * Regla de negocio: solo admite inscripciones un curso abierto y con plazas.
     * Esta decisión vive AQUÍ, en el dominio, no en el controlador REST
     * ni en el adaptador de persistencia.
     */
    public boolean admiteInscripciones() {
        return !cerrado && plazasDisponibles > 0;
    }

    /**
     * Ocupa una plaza del curso. Lanza {@link IllegalStateException} si no es
     * posible: el adaptador web traducirá esa excepción a un código HTTP
     * (sección 10 de la teoría), pero el dominio no sabe nada de HTTP.
     */
    public void ocuparPlaza() {
        if (cerrado) {
            throw new IllegalStateException("El curso está cerrado y no admite inscripciones");
        }
        if (plazasDisponibles <= 0) {
            throw new IllegalStateException("El curso no tiene plazas disponibles");
        }
        plazasDisponibles--;
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

package com.curso.cleanarchitecture.tema06.base;

/**
 * Entidad de dominio {@code Curso} — versión resumida de la vista en el tema 2.
 *
 * <p><b>Capa:</b> dominio (el centro de los círculos). <b>En este tema lo
 * importante es lo que NO tiene:</b> ni {@code @Entity}, ni {@code @Table},
 * ni {@code @Id}. Un curso del negocio no sabe que existe una base de datos.
 * La clase con anotaciones JPA que se persiste de verdad es
 * {@code CursoJpaEntity}, en el paquete {@code persistence}: son dos clases
 * distintas con responsabilidades distintas, y el adaptador traduce entre
 * ambas.</p>
 */
public class Curso {

    // Identidad inmutable: un curso no cambia de id una vez creado.
    private final Long id;

    private final String nombre;

    // Estado mutable SOLO a través de métodos con significado de negocio.
    private int plazasDisponibles;
    private boolean cerrado;

    /**
     * Constructor de reconstrucción: permite recrear un curso con todo su
     * estado (incluido si está cerrado). Lo usará el adaptador JPA para
     * convertir una fila de la tabla en un objeto de dominio.
     * En el tema 2 vimos la versión con validaciones completas; aquí las
     * resumimos para no repetir material.
     */
    public Curso(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        if (nombre == null || nombre.isBlank()) {
            // REGLA (tema 2): un curso no puede tener nombre vacío.
            throw new IllegalArgumentException("El nombre del curso es obligatorio");
        }
        if (plazasDisponibles < 0) {
            // REGLA (tema 2): las plazas no pueden ser negativas.
            throw new IllegalArgumentException("Las plazas disponibles no pueden ser negativas");
        }
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
    }

    /**
     * Regla de negocio central: ¿admite este curso una inscripción?
     * Vive en el dominio, no en el caso de uso ni (mucho menos) en el
     * adaptador JPA. La tecnología puede cambiar; esta regla, no.
     */
    public void validarPuedeRecibirInscripcion() {
        if (cerrado) {
            throw new IllegalStateException("No se puede inscribir en un curso cerrado");
        }
        if (plazasDisponibles <= 0) {
            throw new IllegalStateException("No hay plazas disponibles en el curso");
        }
    }

    /** Acción de negocio: ocupar una plaza tras validar las reglas. */
    public void ocuparPlaza() {
        validarPuedeRecibirInscripcion();
        this.plazasDisponibles--;
    }

    // Getters de solo lectura: el adaptador JPA los usará para volcar el
    // estado del dominio hacia la entidad JPA (método toEntity del adaptador).
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

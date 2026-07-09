package com.curso.cleanarchitecture.tema03.model;

/**
 * Entidad de dominio {@code Alumno}.
 *
 * <p>En el flujo de inscripción del Tema 3 el alumno no aporta reglas de
 * negocio propias: el caso de uso solo necesita comprobar que existe y usar
 * su identificador. Aun así lo modelamos como entidad (y no como un simple
 * Long) porque en temas posteriores incorporará reglas (por ejemplo,
 * validación de email o límite de inscripciones simultáneas).</p>
 */
public class Alumno {

    /** Identificador del alumno. */
    private final Long id;

    /** Nombre del alumno. */
    private final String nombre;

    /** Email de contacto del alumno. */
    private final String email;

    /**
     * Construye un alumno.
     *
     * @param id     identificador (puede ser null si aún no persiste)
     * @param nombre nombre del alumno
     * @param email  email de contacto
     */
    public Alumno(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}

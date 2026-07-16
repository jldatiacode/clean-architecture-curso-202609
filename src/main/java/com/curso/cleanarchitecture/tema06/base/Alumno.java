package com.curso.cleanarchitecture.tema06.base;

/**
 * Entidad de dominio {@code Alumno} — versión resumida de la vista en el tema 2
 * (allí el email era un value object {@code Email}; aquí lo simplificamos a
 * {@code String} porque el foco del tema 6 es la infraestructura).
 *
 * <p>Igual que {@code Curso}: sin anotaciones JPA, sin Spring. Su gemela
 * persistente es {@code AlumnoJpaEntity} en el paquete {@code persistence}.</p>
 */
public class Alumno {

    private final Long id;
    private final String nombre;
    private final String email;

    public Alumno(Long id, String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            // REGLA (tema 2): un alumno debe tener nombre.
            throw new IllegalArgumentException("El nombre del alumno es obligatorio");
        }
        if (email == null || !email.contains("@")) {
            // REGLA (tema 2): email con formato mínimo válido.
            throw new IllegalArgumentException("El email del alumno no es válido");
        }
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

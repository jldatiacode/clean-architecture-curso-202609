package com.curso.cleanarchitecture.tema04.model;

/**
 * Entidad de dominio Alumno.
 *
 * <p>Modelo sencillo e inmutable: una vez creado el alumno, sus datos no cambian
 * en este tema. Al ser inmutable, puede circular por los puertos sin riesgo de
 * que un adaptador lo modifique por accidente.</p>
 *
 * <p>Recuerda: esta clase es la que aparece en los contratos
 * ({@code AlumnoRepositoryPort} devuelve {@code Optional<Alumno>}). El puerto
 * jamás debe exponer una hipotética {@code AlumnoJpaEntity}, porque eso
 * filtraría la infraestructura hacia el núcleo de la aplicación.</p>
 */
public class Alumno {

    private final Long id;
    private final String nombre;
    private final String email;

    /**
     * @param id     identificador del alumno
     * @param nombre nombre completo
     * @param email  correo de contacto (usado también como criterio de búsqueda)
     */
    public Alumno(Long id, String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del alumno es obligatorio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email del alumno no es válido: " + email);
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

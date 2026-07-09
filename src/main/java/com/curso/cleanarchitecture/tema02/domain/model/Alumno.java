package com.curso.cleanarchitecture.tema02.domain.model;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;

/**
 * Entidad de dominio {@code Alumno} (versión final de las secciones 17-19:
 * usa el objeto de valor {@link Email} en lugar de un {@code String}).
 *
 * <p><b>Capa:</b> dominio. <b>Responsabilidad:</b> representar a un alumno
 * con identidad propia y garantizar que siempre tiene nombre y email válidos.</p>
 *
 * <p><b>Dependencias que NO debe tener:</b> Spring, JPA, DTOs, controladores.
 * Solo conoce clases de su propio paquete de dominio.</p>
 *
 * <p>Observa el reparto de responsabilidades: {@code Alumno} solo comprueba
 * que el email <i>existe</i> (no es null); la validez del formato y la
 * normalización a minúsculas son responsabilidad de {@link Email}. Si un
 * {@code Email} llega hasta aquí, ya es válido por construcción.</p>
 */
public class Alumno {

    // Identidad inmutable: el alumno es "quien es" durante toda su vida.
    private final Long id;

    private String nombre;

    // El email es un objeto de valor, no un String: es imposible que un
    // Alumno tenga un email sin '@' o sin normalizar.
    private Email email;

    public Alumno(Long id, String nombre, Email email) {
        validarNombre(nombre);

        // REGLA: el alumno debe tener email. El formato ya lo garantizó Email.
        if (email == null) {
            throw new ReglaNegocioException("El email del alumno es obligatorio");
        }

        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    /**
     * Acción de negocio (no un setter): cambiar el email del alumno.
     * El nuevo email llega ya validado porque es un objeto de valor;
     * aquí solo protegemos que no falte.
     */
    public void cambiarEmail(Email nuevoEmail) {
        if (nuevoEmail == null) {
            throw new ReglaNegocioException("El nuevo email es obligatorio");
        }

        this.email = nuevoEmail;
    }

    private void validarNombre(String nombre) {
        // REGLA: un alumno debe tener nombre.
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaNegocioException("El nombre del alumno es obligatorio");
        }
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Email getEmail() {
        return email;
    }
}

package com.curso.cleanarchitecture.tema04.application.response;

/**
 * Datos de salida que la aplicación publica sobre un alumno.
 */
public class AlumnoResponse {

    private final Long id;
    private final String nombre;
    private final String email;

    public AlumnoResponse(Long id, String nombre, String email) {
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

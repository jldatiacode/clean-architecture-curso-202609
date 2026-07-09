package com.curso.cleanarchitecture.tema04.application.response;

/**
 * Datos de salida que la aplicación publica sobre un curso.
 *
 * <p>Es una "fotografía" inmutable del curso en el momento de la consulta.
 * El adaptador que la reciba (REST, test...) no puede alterar el dominio a
 * través de ella.</p>
 */
public class CursoResponse {

    private final Long id;
    private final String nombre;
    private final int plazasDisponibles;
    private final boolean cerrado;

    public CursoResponse(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
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

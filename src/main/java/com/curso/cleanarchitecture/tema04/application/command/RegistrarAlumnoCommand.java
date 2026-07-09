package com.curso.cleanarchitecture.tema04.application.command;

/**
 * Datos de entrada para la acción "registrar alumno".
 *
 * <p>De nuevo sin id: el exterior aporta nombre y email; la identidad la
 * gestiona la aplicación con ayuda de la persistencia.</p>
 */
public class RegistrarAlumnoCommand {

    private final String nombre;
    private final String email;

    public RegistrarAlumnoCommand(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}

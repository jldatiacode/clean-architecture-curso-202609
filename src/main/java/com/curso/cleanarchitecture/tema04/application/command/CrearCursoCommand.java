package com.curso.cleanarchitecture.tema04.application.command;

/**
 * Datos de entrada para la acción "crear curso".
 *
 * <p>Observa que NO incluye el id: el identificador lo asignará la persistencia,
 * no quien invoca la acción. El command solo lleva lo que el exterior puede
 * y debe aportar.</p>
 */
public class CrearCursoCommand {

    private final String nombre;
    private final int plazasDisponibles;

    public CrearCursoCommand(String nombre, int plazasDisponibles) {
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }
}

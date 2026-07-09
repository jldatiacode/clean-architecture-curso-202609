package com.curso.cleanarchitecture.tema04.application.port.in;

import com.curso.cleanarchitecture.tema04.application.command.RegistrarAlumnoCommand;
import com.curso.cleanarchitecture.tema04.application.response.AlumnoResponse;

/**
 * Puerto de entrada: registrar un alumno.
 *
 * <p>Contrato de la sección 10 de la teoría. Observa que el contrato habla
 * lenguaje de aplicación (command → response) y no de HTTP ni de JSON:
 * la misma acción sirve igual para un endpoint REST que para un test.</p>
 */
public interface RegistrarAlumnoInputPort {

    /**
     * Registra un alumno nuevo en el sistema.
     *
     * @param command datos de entrada (nombre y email)
     * @return representación del alumno registrado
     */
    AlumnoResponse ejecutar(RegistrarAlumnoCommand command);
}

package com.curso.cleanarchitecture.tema04.application.port.in;

import com.curso.cleanarchitecture.tema04.application.command.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema04.application.response.InscripcionResponse;

/**
 * Puerto de entrada: inscribir un alumno en un curso.
 *
 * <p>Es el contrato central del proyecto transversal (sección 6 de la teoría).
 * Lo implementa {@code InscribirAlumnoUseCase} y lo consumirán los adaptadores
 * de entrada del Tema 5.</p>
 *
 * <p>Punto docente clave: cuando en el Tema 5 exista un controlador REST, este
 * dependerá de ESTA interfaz, no de la clase {@code InscribirAlumnoUseCase}.
 * Así el adaptador de entrada queda desacoplado de la implementación concreta
 * de la acción.</p>
 */
public interface InscribirAlumnoInputPort {

    /**
     * Inscribe al alumno indicado en el curso indicado, aplicando todas las
     * reglas de negocio (curso existente y con plazas, alumno existente,
     * sin inscripción activa duplicada).
     *
     * @param command identificadores de curso y alumno
     * @return representación de la inscripción creada
     */
    InscripcionResponse ejecutar(InscribirAlumnoCommand command);
}

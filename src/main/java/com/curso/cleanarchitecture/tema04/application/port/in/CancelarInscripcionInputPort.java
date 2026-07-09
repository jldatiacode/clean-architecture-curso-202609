package com.curso.cleanarchitecture.tema04.application.port.in;

import com.curso.cleanarchitecture.tema04.application.command.CancelarInscripcionCommand;
import com.curso.cleanarchitecture.tema04.application.response.InscripcionResponse;

/**
 * Puerto de entrada: cancelar una inscripción existente.
 *
 * <p>Contrato de la sección 10 de la teoría. Devuelve la inscripción ya
 * cancelada para que el adaptador de entrada pueda mostrar su estado final.</p>
 */
public interface CancelarInscripcionInputPort {

    /**
     * Cancela la inscripción indicada.
     *
     * @param command identificador de la inscripción a cancelar
     * @return representación de la inscripción con estado CANCELADA
     */
    InscripcionResponse ejecutar(CancelarInscripcionCommand command);
}

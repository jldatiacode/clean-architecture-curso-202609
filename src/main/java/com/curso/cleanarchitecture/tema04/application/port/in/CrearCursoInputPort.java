package com.curso.cleanarchitecture.tema04.application.port.in;

import com.curso.cleanarchitecture.tema04.application.command.CrearCursoCommand;
import com.curso.cleanarchitecture.tema04.application.response.CursoResponse;

/**
 * Puerto de entrada: crear un curso.
 *
 * <p>Contrato de la sección 10 de la teoría. La interfaz solo dice QUÉ se puede
 * hacer (crear un curso a partir de unos datos) y qué se devuelve. El CÓMO
 * pertenece al caso de uso que la implemente.</p>
 */
public interface CrearCursoInputPort {

    /**
     * Crea un curso nuevo con los datos del command.
     *
     * @param command datos de entrada (nombre y plazas)
     * @return representación del curso creado, con su id asignado
     */
    CursoResponse ejecutar(CrearCursoCommand command);
}

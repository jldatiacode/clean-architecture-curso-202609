package com.curso.cleanarchitecture.tema04.application.port.in;

import com.curso.cleanarchitecture.tema04.application.command.SolicitarListaEsperaCommand;
import com.curso.cleanarchitecture.tema04.application.response.SolicitudListaEsperaResponse;

/**
 * Puerto de entrada del caso de uso de lista de espera.
 *
 * <p>Cualquier adaptador de entrada futuro —REST, consola, CSV o mensajería—
 * podrá invocar esta operación sin depender de la clase concreta del caso de uso.</p>
 */
public interface SolicitarListaEsperaInputPort {

    SolicitudListaEsperaResponse ejecutar(SolicitarListaEsperaCommand command);
}

package com.curso.cleanarchitecture.tema04.application.command;

/**
 * Datos de entrada para la acción "cancelar inscripción".
 *
 * <p>Aunque solo transporta un dato, sigue mereciendo la pena el command:
 * da un nombre al concepto, permite evolucionar el contrato (añadir un motivo
 * de cancelación, por ejemplo) sin romper la firma del puerto de entrada.</p>
 */
public class CancelarInscripcionCommand {

    private final Long inscripcionId;

    public CancelarInscripcionCommand(Long inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public Long getInscripcionId() {
        return inscripcionId;
    }
}

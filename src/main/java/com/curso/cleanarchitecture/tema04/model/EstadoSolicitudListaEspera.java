package com.curso.cleanarchitecture.tema04.model;

/**
 * Estados posibles de una solicitud de lista de espera.
 *
 * <p>La solicitud nace en estado PENDIENTE. En temas posteriores podrían
 * añadirse casos de uso para avisar al alumno cuando quede una plaza o para
 * cancelar la solicitud.</p>
 */
public enum EstadoSolicitudListaEspera {
    PENDIENTE,
    AVISADA,
    CANCELADA
}

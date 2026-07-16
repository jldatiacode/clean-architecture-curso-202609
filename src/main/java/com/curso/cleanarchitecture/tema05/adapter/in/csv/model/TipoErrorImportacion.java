package com.curso.cleanarchitecture.tema05.adapter.in.csv.model;

/** Tipos de error que pueden aparecer durante una importación. */
public enum TipoErrorImportacion {

    /** La fila no respeta el formato o contiene datos no convertibles. */
    FORMATO,

    /** La solicitud fue rechazada por una regla del negocio. */
    NEGOCIO,

    /** Fallo inesperado de infraestructura o ejecución. */
    TECNICO
}

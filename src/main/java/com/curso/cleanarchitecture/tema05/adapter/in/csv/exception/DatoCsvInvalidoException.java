package com.curso.cleanarchitecture.tema05.adapter.in.csv.exception;

/**
 * Error aislado de una fila. No obliga a detener toda la importación.
 */
public class DatoCsvInvalidoException extends RuntimeException {

    public DatoCsvInvalidoException(String message) {
        super(message);
    }
}

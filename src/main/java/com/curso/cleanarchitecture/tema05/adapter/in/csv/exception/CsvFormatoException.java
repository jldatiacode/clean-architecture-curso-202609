package com.curso.cleanarchitecture.tema05.adapter.in.csv.exception;

/**
 * Error global que impide procesar el archivo completo, por ejemplo una
 * cabecera incorrecta o un archivo vacío.
 */
public class CsvFormatoException extends RuntimeException {

    public CsvFormatoException(String message) {
        super(message);
    }

    public CsvFormatoException(String message, Throwable cause) {
        super(message, cause);
    }
}

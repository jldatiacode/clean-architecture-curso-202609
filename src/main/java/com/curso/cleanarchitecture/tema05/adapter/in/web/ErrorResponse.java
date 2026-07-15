package com.curso.cleanarchitecture.tema05.adapter.in.web;

/**
 * DTO EXTERNO de error (sección 10 de la teoría): el JSON que devuelve la API
 * cuando algo falla.
 *
 * <pre>
 * { "message": "El curso no tiene plazas disponibles" }
 * </pre>
 *
 * <p>Igual que con las respuestas de éxito, el error tiene su propio DTO:
 * no serializamos la excepción ni su stack trace (sería filtrar detalles
 * internos al exterior). El formato del error forma parte del contrato
 * de la API tanto como el de las respuestas correctas.</p>
 *
 * <p><b>MATERIAL DE LECTURA:</b> versión funcional en
 * {@code com.curso.cleanarchitecture.proyecto}.</p>
 */
public class ErrorResponse {

    private final String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

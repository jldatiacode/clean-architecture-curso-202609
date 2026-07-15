package com.curso.cleanarchitecture.tema05.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Gestión de errores en el adaptador web (sección 10 de la teoría).
 *
 * <p>El caso de uso lanza excepciones de NEGOCIO ("no existe el curso",
 * "no quedan plazas") sin saber nada de HTTP. Alguien tiene que traducir
 * esas excepciones a códigos de estado, y ese alguien es el adaptador web:
 * la traducción error de negocio → error HTTP es tan "adaptación" como la
 * traducción request → command.</p>
 *
 * <p>Con {@code @RestControllerAdvice} centralizamos esa traducción para
 * todos los controladores, en lugar de llenar cada método de try/catch.
 * Regla de la teoría: <b>el caso de uso no debe devolver códigos HTTP</b>;
 * los decide esta clase.</p>
 *
 * <p>Convención usada en el curso (simplificada a propósito):</p>
 * <ul>
 *   <li>{@link IllegalArgumentException} → 400: datos de entrada inválidos
 *       o referencias inexistentes.</li>
 *   <li>{@link IllegalStateException} → 400: la operación viola una regla
 *       de negocio (curso cerrado, sin plazas). En el tema 7 discutiremos
 *       excepciones propias del dominio y códigos más finos (404, 409...).</li>
 * </ul>
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación arrancable vive en
 * {@code com.curso.cleanarchitecture.proyecto} y solo escanea ese paquete;
 * este {@code @RestControllerAdvice} compila pero nunca se registra.
 * La versión funcional equivalente está en {@code proyecto}.</p>
 */
@RestControllerAdvice
public class ApiExceptionHandler {

    /** Entrada inválida: el cliente pidió algo que no existe o mandó datos erróneos. */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        // Traducción: excepción de negocio → 400 Bad Request + DTO de error.
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

    /** Regla de negocio violada: la petición era válida pero el estado no la permite. */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleBusinessError(IllegalStateException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }
}

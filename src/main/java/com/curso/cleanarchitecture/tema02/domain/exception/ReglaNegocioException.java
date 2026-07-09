package com.curso.cleanarchitecture.tema02.domain.exception;

/**
 * Excepción de dominio: señala que se ha incumplido una regla de negocio.
 *
 * <p><b>Capa:</b> dominio (núcleo). <b>Responsabilidad:</b> expresar con un tipo
 * propio que una operación no es válida para el negocio, con un mensaje en
 * lenguaje de negocio ("No hay plazas disponibles en el curso").</p>
 *
 * <p><b>Dependencias que NO debe tener:</b> ninguna técnica. No extiende de
 * excepciones de Spring, no conoce HTTP ni códigos de estado. Cómo se comunica
 * este error al exterior (HTTP 400, mensaje en consola, etc.) es decisión de
 * los adaptadores, no del dominio.</p>
 *
 * <p>Extiende {@link RuntimeException} (no comprobada) a propósito: las reglas
 * de negocio pueden fallar en muchos puntos y no queremos contaminar todas las
 * firmas del dominio con {@code throws}. Quien deba reaccionar, la captura.</p>
 */
public class ReglaNegocioException extends RuntimeException {

    public ReglaNegocioException(String message) {
        super(message);
    }
}

package com.curso.cleanarchitecture.tema02.domain.model;

/**
 * Estados válidos de una inscripción (sección 21 de la teoría).
 *
 * <p><b>Capa:</b> dominio. <b>Responsabilidad:</b> restringir los estados
 * posibles de una {@link Inscripcion} a un conjunto cerrado y con nombre
 * de negocio.</p>
 *
 * <p>Usar un enum en lugar de un {@code String} ("ACTIVA", "activa",
 * "Activa"...) elimina toda una familia de errores: no se puede escribir un
 * estado inexistente ni comparar con typos. Un estado no es una entidad:
 * no tiene identidad ni comportamiento propio, solo distingue situaciones.</p>
 */
public enum EstadoInscripcion {
    ACTIVA,
    CANCELADA
}

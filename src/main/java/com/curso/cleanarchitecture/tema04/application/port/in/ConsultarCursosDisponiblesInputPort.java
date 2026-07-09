package com.curso.cleanarchitecture.tema04.application.port.in;

import java.util.List;

import com.curso.cleanarchitecture.tema04.application.response.CursoResponse;

/**
 * Puerto de entrada: consultar los cursos con plazas disponibles.
 *
 * <p>Contrato de la sección 10 de la teoría. Es una acción de CONSULTA:
 * no recibe command porque no necesita parámetros, y no modifica estado.</p>
 *
 * <p>Devuelve responses, no entidades de dominio: el exterior recibe una
 * fotografía de los cursos, sin capacidad de mutar el modelo.</p>
 */
public interface ConsultarCursosDisponiblesInputPort {

    /**
     * @return cursos abiertos y con plazas libres, listos para inscripción
     */
    List<CursoResponse> ejecutar();
}

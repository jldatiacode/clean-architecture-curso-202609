package com.curso.cleanarchitecture.tema05.adapter.in.csv.model;

/**
 * DTO externo que representa una fila válida del archivo CSV.
 *
 * <p>No es una entidad del dominio. Su única responsabilidad es transportar
 * los datos que llegan desde el formato CSV hasta el mapper del adaptador.</p>
 */
public record InscripcionCsvRow(
        int numeroFila,
        Long cursoId,
        Long alumnoId) {
}

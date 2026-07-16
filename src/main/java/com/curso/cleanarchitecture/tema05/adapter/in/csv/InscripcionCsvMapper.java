package com.curso.cleanarchitecture.tema05.adapter.in.csv;

import com.curso.cleanarchitecture.tema05.adapter.in.csv.exception.DatoCsvInvalidoException;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.InscripcionCsvRow;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoCommand;

/**
 * Traduce el formato externo CSV al modelo interno de la aplicación.
 */
public class InscripcionCsvMapper {

    /**
     * Convierte una línea CSV en un DTO de fila validado.
     */
    public InscripcionCsvRow toRow(int numeroFila, String linea) {
        if (linea == null || linea.isBlank()) {
            throw new DatoCsvInvalidoException("La fila está vacía");
        }

        String[] columnas = linea.split(",", -1);
        if (columnas.length != 2) {
            throw new DatoCsvInvalidoException(
                    "La fila debe contener exactamente cursoId y alumnoId");
        }

        Long cursoId = convertirId(columnas[0], "cursoId");
        Long alumnoId = convertirId(columnas[1], "alumnoId");

        return new InscripcionCsvRow(numeroFila, cursoId, alumnoId);
    }

    /**
     * Convierte el DTO externo de fila en el command que entiende el caso de uso.
     */
    public InscribirAlumnoCommand toCommand(InscripcionCsvRow row) {
        return new InscribirAlumnoCommand(row.cursoId(), row.alumnoId());
    }

    private Long convertirId(String valor, String nombreCampo) {
        String valorLimpio = valor == null ? "" : valor.trim();
        if (valorLimpio.isEmpty()) {
            throw new DatoCsvInvalidoException(
                    "El campo " + nombreCampo + " es obligatorio");
        }

        try {
            long id = Long.parseLong(valorLimpio);
            if (id <= 0) {
                throw new DatoCsvInvalidoException(
                        "El campo " + nombreCampo + " debe ser mayor que cero");
            }
            return id;
        } catch (NumberFormatException ex) {
            throw new DatoCsvInvalidoException(
                    "El campo " + nombreCampo + " debe ser numérico");
        }
    }
}

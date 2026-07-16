package com.curso.cleanarchitecture.tema05.adapter.in.csv.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Resumen completo de una importación CSV.
 */
public class ResultadoImportacion {

    private final String nombreArchivo;
    private final List<ResultadoFila> resultados = new ArrayList<>();

    public ResultadoImportacion(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public void agregar(ResultadoFila resultadoFila) {
        resultados.add(resultadoFila);
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public int getTotalFilas() {
        return resultados.size();
    }

    public long getCorrectas() {
        return resultados.stream()
                .filter(ResultadoFila::isCorrecta)
                .count();
    }

    public long getRechazadas() {
        return resultados.stream()
                .filter(resultado -> !resultado.isCorrecta())
                .count();
    }

    public List<ResultadoFila> getResultados() {
        return Collections.unmodifiableList(resultados);
    }
}

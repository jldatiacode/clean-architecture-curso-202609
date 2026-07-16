package com.curso.cleanarchitecture.tema05.adapter.out.report;

import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.ResultadoFila;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.ResultadoImportacion;
import com.curso.cleanarchitecture.tema05.application.port.out.InformeImportacionPort;

/**
 * Adaptador de salida que muestra el informe de importación por consola.
 */
public class InformeImportacionConsoleAdapter implements InformeImportacionPort {

    @Override
    public void generar(ResultadoImportacion resultado) {
        System.out.println("\n=== INFORME DE IMPORTACIÓN ===");
        System.out.println("Archivo: " + resultado.getNombreArchivo());
        System.out.println("Filas procesadas: " + resultado.getTotalFilas());
        System.out.println("Correctas: " + resultado.getCorrectas());
        System.out.println("Rechazadas: " + resultado.getRechazadas());

        for (ResultadoFila fila : resultado.getResultados()) {
            String estado = fila.isCorrecta() ? "OK" : "ERROR " + fila.getTipoError();
            System.out.println(
                    "Fila " + fila.getNumeroFila()
                            + " [" + estado + "]: "
                            + fila.getMensaje());
        }
    }
}

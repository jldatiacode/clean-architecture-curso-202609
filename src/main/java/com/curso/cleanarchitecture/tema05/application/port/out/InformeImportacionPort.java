package com.curso.cleanarchitecture.tema05.application.port.out;

import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.ResultadoImportacion;

/**
 * Puerto de salida para publicar o guardar el resultado de una importación.
 *
 * <p>La aplicación expresa la necesidad de generar un informe, pero no decide
 * si este se imprime, se guarda en un archivo o se envía por correo.</p>
 */
@FunctionalInterface
public interface InformeImportacionPort {

    void generar(ResultadoImportacion resultado);
}

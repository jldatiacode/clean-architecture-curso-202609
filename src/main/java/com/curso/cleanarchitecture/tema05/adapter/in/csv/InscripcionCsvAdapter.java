package com.curso.cleanarchitecture.tema05.adapter.in.csv;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.curso.cleanarchitecture.tema05.adapter.in.csv.exception.CsvFormatoException;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.exception.DatoCsvInvalidoException;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.InscripcionCsvRow;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.ResultadoFila;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.ResultadoImportacion;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.TipoErrorImportacion;
import com.curso.cleanarchitecture.tema05.application.port.out.InformeImportacionPort;
import com.curso.cleanarchitecture.tema05.base.InscripcionResponse;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoInputPort;

/**
 * Adaptador de entrada que permite ejecutar el caso de uso de inscripción
 * desde un archivo CSV.
 *
 * <p>Lee el formato externo, lo convierte a commands y llama al puerto de
 * entrada existente. No contiene reglas de negocio ni accede directamente a
 * repositorios.</p>
 */
public class InscripcionCsvAdapter {

    private static final String CABECERA_ESPERADA = "cursoid,alumnoid";

    private final InscribirAlumnoInputPort inscribirAlumnoInputPort;
    private final InscripcionCsvMapper mapper;
    private final InformeImportacionPort informeImportacionPort;

    public InscripcionCsvAdapter(InscribirAlumnoInputPort inscribirAlumnoInputPort,
                                 InscripcionCsvMapper mapper,
                                 InformeImportacionPort informeImportacionPort) {
        this.inscribirAlumnoInputPort = inscribirAlumnoInputPort;
        this.mapper = mapper;
        this.informeImportacionPort = informeImportacionPort;
    }

    /**
     * Procesa todas las filas válidas y registra los errores aislados sin
     * detener el archivo completo.
     */
    public ResultadoImportacion importar(Path archivo) {
        List<String> lineas = leerArchivo(archivo);
        validarCabecera(lineas);

        ResultadoImportacion resultado = new ResultadoImportacion(
                archivo.getFileName().toString());

        for (int indice = 1; indice < lineas.size(); indice++) {
            int numeroFila = indice + 1;
            String contenidoOriginal = lineas.get(indice);

            // Las líneas totalmente vacías no representan solicitudes.
            if (contenidoOriginal.isBlank()) {
                continue;
            }

            procesarFila(
                    numeroFila,
                    contenidoOriginal,
                    resultado);
        }

        informeImportacionPort.generar(resultado);
        return resultado;
    }

    private void procesarFila(int numeroFila,
                              String contenidoOriginal,
                              ResultadoImportacion resultado) {
        try {
            InscripcionCsvRow row = mapper.toRow(numeroFila, contenidoOriginal);
            InscribirAlumnoCommand command = mapper.toCommand(row);
            InscripcionResponse response = inscribirAlumnoInputPort.ejecutar(command);

            resultado.agregar(ResultadoFila.correcta(
                    numeroFila,
                    contenidoOriginal,
                    response.getInscripcionId()));

        } catch (DatoCsvInvalidoException ex) {
            resultado.agregar(ResultadoFila.error(
                    numeroFila,
                    contenidoOriginal,
                    TipoErrorImportacion.FORMATO,
                    ex.getMessage()));

        } catch (IllegalArgumentException | IllegalStateException ex) {
            resultado.agregar(ResultadoFila.error(
                    numeroFila,
                    contenidoOriginal,
                    TipoErrorImportacion.NEGOCIO,
                    ex.getMessage()));

        } catch (RuntimeException ex) {
            resultado.agregar(ResultadoFila.error(
                    numeroFila,
                    contenidoOriginal,
                    TipoErrorImportacion.TECNICO,
                    "Error técnico durante el procesamiento: " + ex.getMessage()));
        }
    }

    private List<String> leerArchivo(Path archivo) {
        if (archivo == null) {
            throw new CsvFormatoException("Debe indicarse un archivo CSV");
        }
        if (!Files.exists(archivo) || !Files.isRegularFile(archivo)) {
            throw new CsvFormatoException("El archivo CSV no existe: " + archivo);
        }

        try {
            List<String> lineas = Files.readAllLines(archivo, StandardCharsets.UTF_8);
            if (lineas.isEmpty()) {
                throw new CsvFormatoException("El archivo CSV está vacío");
            }
            return lineas;
        } catch (IOException ex) {
            throw new CsvFormatoException("No se ha podido leer el archivo CSV", ex);
        }
    }

    private void validarCabecera(List<String> lineas) {
        String cabecera = eliminarBom(lineas.get(0))
                .replace(" ", "")
                .toLowerCase();

        if (!CABECERA_ESPERADA.equals(cabecera)) {
            throw new CsvFormatoException(
                    "Cabecera incorrecta. Se esperaba: cursoId,alumnoId");
        }
    }

    private String eliminarBom(String texto) {
        if (texto != null && texto.startsWith("\uFEFF")) {
            return texto.substring(1);
        }
        return texto;
    }
}

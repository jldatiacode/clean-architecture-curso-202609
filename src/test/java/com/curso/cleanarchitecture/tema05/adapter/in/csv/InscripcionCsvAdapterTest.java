package com.curso.cleanarchitecture.tema05.adapter.in.csv;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicLong;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.curso.cleanarchitecture.tema05.adapter.in.csv.exception.CsvFormatoException;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.ResultadoImportacion;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.model.TipoErrorImportacion;
import com.curso.cleanarchitecture.tema05.application.port.out.InformeImportacionPort;
import com.curso.cleanarchitecture.tema05.base.InscripcionResponse;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoInputPort;

class InscripcionCsvAdapterTest {

    @TempDir
    Path tempDir;

    @Test
    void procesaTodasLasFilasYContinuaTrasLosErrores() throws IOException {
        Path csv = tempDir.resolve("inscripciones.csv");
        Files.writeString(csv, """
                cursoId,alumnoId
                1,10
                texto,11
                2,99
                3,12
                """);

        AtomicLong secuencia = new AtomicLong();
        InscribirAlumnoInputPort inputPort = command -> ejecutarCasoSimulado(command, secuencia);

        AtomicReference<ResultadoImportacion> informeGenerado = new AtomicReference<>();
        InformeImportacionPort informePort = informeGenerado::set;

        InscripcionCsvAdapter adapter = new InscripcionCsvAdapter(
                inputPort,
                new InscripcionCsvMapper(),
                informePort);

        ResultadoImportacion resultado = adapter.importar(csv);

        assertThat(resultado.getTotalFilas()).isEqualTo(4);
        assertThat(resultado.getCorrectas()).isEqualTo(2);
        assertThat(resultado.getRechazadas()).isEqualTo(2);
        assertThat(informeGenerado.get()).isSameAs(resultado);

        assertThat(resultado.getResultados().get(1).getTipoError())
                .isEqualTo(TipoErrorImportacion.FORMATO);
        assertThat(resultado.getResultados().get(2).getTipoError())
                .isEqualTo(TipoErrorImportacion.NEGOCIO);
    }

    @Test
    void detieneLaImportacionSiLaCabeceraEsIncorrecta() throws IOException {
        Path csv = tempDir.resolve("incorrecto.csv");
        Files.writeString(csv, "curso,alumno\n1,10\n");

        InscripcionCsvAdapter adapter = new InscripcionCsvAdapter(
                command -> new InscripcionResponse(1L, 1L, 10L, "ACTIVA"),
                new InscripcionCsvMapper(),
                resultado -> { });

        assertThatThrownBy(() -> adapter.importar(csv))
                .isInstanceOf(CsvFormatoException.class)
                .hasMessageContaining("Cabecera incorrecta");
    }

    private InscripcionResponse ejecutarCasoSimulado(InscribirAlumnoCommand command,
                                                       AtomicLong secuencia) {
        if (command.getAlumnoId().equals(99L)) {
            throw new IllegalArgumentException("No existe el alumno con id 99");
        }

        return new InscripcionResponse(
                secuencia.incrementAndGet(),
                command.getCursoId(),
                command.getAlumnoId(),
                "ACTIVA");
    }
}

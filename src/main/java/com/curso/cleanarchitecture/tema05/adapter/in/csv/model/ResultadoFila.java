package com.curso.cleanarchitecture.tema05.adapter.in.csv.model;

/**
 * Resultado del procesamiento de una fila del archivo.
 */
public class ResultadoFila {

    private final int numeroFila;
    private final String contenidoOriginal;
    private final boolean correcta;
    private final Long inscripcionId;
    private final String mensaje;
    private final TipoErrorImportacion tipoError;

    private ResultadoFila(int numeroFila,
                          String contenidoOriginal,
                          boolean correcta,
                          Long inscripcionId,
                          String mensaje,
                          TipoErrorImportacion tipoError) {
        this.numeroFila = numeroFila;
        this.contenidoOriginal = contenidoOriginal;
        this.correcta = correcta;
        this.inscripcionId = inscripcionId;
        this.mensaje = mensaje;
        this.tipoError = tipoError;
    }

    public static ResultadoFila correcta(int numeroFila,
                                         String contenidoOriginal,
                                         Long inscripcionId) {
        return new ResultadoFila(
                numeroFila,
                contenidoOriginal,
                true,
                inscripcionId,
                "Inscripción realizada correctamente",
                null);
    }

    public static ResultadoFila error(int numeroFila,
                                      String contenidoOriginal,
                                      TipoErrorImportacion tipoError,
                                      String mensaje) {
        return new ResultadoFila(
                numeroFila,
                contenidoOriginal,
                false,
                null,
                mensaje,
                tipoError);
    }

    public int getNumeroFila() {
        return numeroFila;
    }

    public String getContenidoOriginal() {
        return contenidoOriginal;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public String getMensaje() {
        return mensaje;
    }

    public TipoErrorImportacion getTipoError() {
        return tipoError;
    }
}

package com.curso.cleanarchitecture.tema05.adapter.in.web;

/**
 * DTO EXTERNO de salida (sección 6 de la teoría): el JSON que devuelve la API.
 *
 * <pre>
 * {
 *   "inscripcionId": 1,
 *   "cursoId": 1,
 *   "alumnoId": 10,
 *   "estado": "ACTIVA"
 * }
 * </pre>
 *
 * <p>Advertencia de la teoría: <b>no exponer entidades de dominio ni de JPA
 * directamente por la API</b> sin reflexionar. Si serializáramos
 * {@code Inscripcion} tal cual, cualquier refactor interno rompería el
 * contrato público de la API (y viceversa). El DTO HTTP es la frontera
 * estable que protege ambos lados.</p>
 *
 * <p>Es inmutable (solo getters): a diferencia del request, la respuesta la
 * construimos nosotros y Jackson solo necesita leerla.</p>
 *
 * <p><b>MATERIAL DE LECTURA:</b> versión funcional en
 * {@code com.curso.cleanarchitecture.proyecto}.</p>
 */
public class InscripcionHttpResponse {

    private final Long inscripcionId;
    private final Long cursoId;
    private final Long alumnoId;
    private final String estado;

    public InscripcionHttpResponse(Long inscripcionId, Long cursoId, Long alumnoId, String estado) {
        this.inscripcionId = inscripcionId;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = estado;
    }

    public Long getInscripcionId() {
        return inscripcionId;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public String getEstado() {
        return estado;
    }
}

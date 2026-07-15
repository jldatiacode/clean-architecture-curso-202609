package com.curso.cleanarchitecture.tema05.adapter.in.web;

/**
 * DTO EXTERNO de entrada (sección 6 de la teoría): representa el cuerpo JSON
 * que el cliente envía por HTTP.
 *
 * <pre>
 * POST /tema05/cursos/1/inscripciones
 * { "alumnoId": 10 }
 * </pre>
 *
 * <p>¿Por qué no reutilizar {@code InscribirAlumnoCommand} directamente?</p>
 * <ul>
 *   <li>El request refleja el CONTRATO DE LA API: aquí el {@code cursoId} no
 *       viaja en el cuerpo porque ya va en la URL. El command, en cambio,
 *       necesita ambos datos.</li>
 *   <li>El contrato de la API puede evolucionar (renombrar campos, añadir
 *       versiones) sin tocar el núcleo, y viceversa.</li>
 *   <li>Jackson necesita constructor vacío y setters para deserializar;
 *       nuestro command es inmutable y con validación. Son necesidades
 *       distintas: mejor clases distintas.</li>
 * </ul>
 *
 * <p><b>MATERIAL DE LECTURA:</b> este DTO se usa desde clases que nunca se
 * escanean como beans (ver package-info). La versión funcional está en
 * {@code com.curso.cleanarchitecture.proyecto}.</p>
 */
public class InscribirAlumnoRequest {

    private Long alumnoId;

    /** Constructor vacío: lo exige Jackson para deserializar el JSON. */
    public InscribirAlumnoRequest() {
    }

    /** Constructor de conveniencia para los tests. */
    public InscribirAlumnoRequest(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }
}

package com.curso.cleanarchitecture.tema01.modelo;

/**
 * Modelo ANÉMICO de una inscripción: relaciona un curso con un alumno, pero no
 * sabe nada de las reglas que gobiernan esa relación.
 *
 * <p><b>Capa que representa:</b> ninguna definida. Es el reflejo directo de la
 * tabla INSCRIPCIONES (cursoId, alumnoId, fecha) en lugar del concepto de
 * negocio "inscripción".</p>
 *
 * <p><b>Qué enseña:</b> según la Actividad 2 de la teoría, la regla "un alumno
 * no puede inscribirse dos veces en el mismo curso" está relacionada con la
 * Inscripción. Sin embargo, este modelo no puede hacer nada al respecto: la
 * comprobación de duplicados acabará en el controlador, consultando el
 * repositorio a mano ({@code existsByCursoIdAndAlumnoId}). Cuando las entidades
 * no tienen comportamiento, las reglas emigran a las capas externas.</p>
 *
 * <p>Obsérvese también que en el ejemplo de la teoría (sección 5) la inscripción
 * se construye con {@code new Inscripcion(curso, alumno)}: aquí guardamos los
 * objetos completos, lo que en un contexto JPA real arrastraría además problemas
 * de mapeo y de carga. En el Tema 1 nos basta con señalar el problema de diseño.</p>
 *
 * <p><b>Dependencias:</b> solo del propio paquete de modelos del tema.</p>
 */
public class InscripcionSimple {

    private Long id;

    // Referencias directas a los otros modelos anémicos: la inscripción "sabe"
    // quiénes son el curso y el alumno, pero no sabe si la relación es válida.
    private CursoSimple curso;
    private AlumnoSimple alumno;

    /**
     * Constructor vacío para frameworks: de nuevo, permite una inscripción sin
     * curso ni alumno, un estado que en el negocio es absurdo.
     */
    public InscripcionSimple() {
    }

    public InscripcionSimple(CursoSimple curso, AlumnoSimple alumno) {
        // Ni siquiera aquí se valida que curso y alumno no sean null, ni que el
        // curso admita inscripciones. La clase confía ciegamente en quien la usa.
        this.curso = curso;
        this.alumno = alumno;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CursoSimple getCurso() {
        return curso;
    }

    public void setCurso(CursoSimple curso) {
        this.curso = curso;
    }

    public AlumnoSimple getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoSimple alumno) {
        this.alumno = alumno;
    }
}

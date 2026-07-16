package com.curso.cleanarchitecture.tema06.base;

/**
 * Entidad de dominio {@code Inscripcion} — versión resumida de la del tema 2.
 *
 * <p>Relaciona un curso y un alumno por sus identificadores. Nace siempre
 * {@code ACTIVA} y solo puede transitar a {@code CANCELADA} a través de la
 * acción de negocio {@link #cancelar()}: no hay {@code setEstado}, así que
 * es imposible dejarla en un estado inválido desde fuera.</p>
 *
 * <p><b>Contraste con {@code InscripcionJpaEntity} (paquete {@code persistence}):</b>
 * la entidad JPA sí tiene setters y constructor vacío, porque los necesita
 * Hibernate. Aquí no cedemos: el modelo de dominio se diseña para el negocio,
 * no para el framework. Por eso son dos clases separadas.</p>
 */
public class Inscripcion {

    private final Long id;
    private final Long cursoId;
    private final Long alumnoId;
    private EstadoInscripcion estado;

    /**
     * Crea una inscripción ACTIVA. El {@code id} puede ser {@code null}
     * cuando la inscripción es nueva y todavía no ha sido persistida:
     * será la base de datos (columna IDENTITY) quien lo genere.
     */
    public Inscripcion(Long id, Long cursoId, Long alumnoId) {
        if (cursoId == null || alumnoId == null) {
            // REGLA (tema 2): una inscripción sin curso o sin alumno no tiene sentido.
            throw new IllegalArgumentException("La inscripción requiere curso y alumno");
        }
        this.id = id;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = EstadoInscripcion.ACTIVA;
    }

    /**
     * Acción de negocio: cancelar la inscripción.
     * El adaptador JPA también la usa al reconstruir desde la base de datos
     * una inscripción cuyo estado guardado es CANCELADA (ver el método
     * {@code toDomain} de {@code InscripcionRepositoryJpaAdapter}).
     */
    public void cancelar() {
        if (estado == EstadoInscripcion.CANCELADA) {
            throw new IllegalStateException("La inscripción ya está cancelada");
        }
        this.estado = EstadoInscripcion.CANCELADA;
    }

    public Long getId() {
        return id;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }
}

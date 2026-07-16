package com.curso.cleanarchitecture.tema06.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA de la tabla {@code inscripciones} — el ejemplo literal de la
 * sección 9 de la teoría.
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}; esta clase compila pero nunca se registra en Hibernate.</p>
 *
 * <p>Dos decisiones de diseño que merecen atención:</p>
 * <ul>
 *   <li><b>{@code cursoId} y {@code alumnoId} como {@code Long}, no como
 *       {@code @ManyToOne}:</b> guardamos identificadores, igual que hace el
 *       dominio. Evitamos que las relaciones JPA (lazy loading, cascadas)
 *       compliquen el ejemplo y contaminen el diseño.</li>
 *   <li><b>{@code estado} como {@code String}, no como enum:</b> en la tabla
 *       es un VARCHAR ('ACTIVA' / 'CANCELADA'). La traducción a
 *       {@code EstadoInscripcion} (enum del dominio) la hace el adaptador
 *       en {@code toDomain} / {@code toEntity}. Cada capa con su tipo.</li>
 * </ul>
 */
@Entity
@Table(name = "inscripciones")
public class InscripcionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Id generado por H2 al insertar.
    private Long id;

    private Long cursoId;   // columna curso_id
    private Long alumnoId;  // columna alumno_id
    private String estado;  // columna estado (VARCHAR: 'ACTIVA' / 'CANCELADA')

    /**
     * Constructor protegido para JPA: Hibernate lo necesita para instanciar
     * por reflexión al mapear cada fila. Protegido (no público) para que
     * nadie más construya entidades sin datos.
     */
    protected InscripcionJpaEntity() {
    }

    /** Constructor completo: lo usa el mapper {@code toEntity} del adaptador. */
    public InscripcionJpaEntity(Long id, Long cursoId, Long alumnoId, String estado) {
        this.id = id;
        this.cursoId = cursoId;
        this.alumnoId = alumnoId;
        this.estado = estado;
    }

    // Getters y setters requeridos por el estilo JavaBean que espera JPA.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCursoId() {
        return cursoId;
    }

    public void setCursoId(Long cursoId) {
        this.cursoId = cursoId;
    }

    public Long getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(Long alumnoId) {
        this.alumnoId = alumnoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

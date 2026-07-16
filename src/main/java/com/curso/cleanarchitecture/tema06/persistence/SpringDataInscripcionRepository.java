package com.curso.cleanarchitecture.tema06.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data para inscripciones — el ejemplo literal de la
 * sección 9 de la teoría.
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}; Spring nunca instancia esta interfaz desde aquí.</p>
 *
 * <p>Además del CRUD heredado, declara dos <b>consultas derivadas</b>:
 * Spring Data analiza el NOMBRE del método y genera el SQL:</p>
 * <ul>
 *   <li>{@code existsByCursoIdAndAlumnoIdAndEstado} →
 *       {@code SELECT COUNT(*) > 0 ... WHERE curso_id = ? AND alumno_id = ? AND estado = ?}</li>
 *   <li>{@code findByAlumnoId} →
 *       {@code SELECT * FROM inscripciones WHERE alumno_id = ?}</li>
 * </ul>
 *
 * <p>Observa que el estado viaja como {@code String} ('ACTIVA'), porque así
 * está en la tabla. El adaptador convierte desde el enum del dominio con
 * {@code EstadoInscripcion.ACTIVA.name()}. El lenguaje técnico se queda en
 * esta orilla; el puerto habla de "existe inscripción activa".</p>
 */
public interface SpringDataInscripcionRepository
        extends JpaRepository<InscripcionJpaEntity, Long> {

    /** Consulta derivada: ¿existe una inscripción de ese curso/alumno en ese estado? */
    boolean existsByCursoIdAndAlumnoIdAndEstado(Long cursoId, Long alumnoId, String estado);

    /** Consulta derivada: todas las inscripciones (entidades JPA) de un alumno. */
    List<InscripcionJpaEntity> findByAlumnoId(Long alumnoId);
}

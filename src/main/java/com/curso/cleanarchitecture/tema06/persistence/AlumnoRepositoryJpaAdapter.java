package com.curso.cleanarchitecture.tema06.persistence;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema06.base.Alumno;
import com.curso.cleanarchitecture.tema06.base.AlumnoRepositoryPort;

/**
 * Adaptador JPA que implementa {@code AlumnoRepositoryPort} (sección 10).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}; este {@code @Repository} nunca se registra desde aquí.</p>
 *
 * <p>Adaptador mínimo: el puerto solo pide {@code buscarPorId}, así que el
 * adaptador solo implementa eso. Aunque {@code JpaRepository} nos "regala"
 * save, delete, findAll..., no los exponemos: la superficie la define el
 * puerto (las necesidades del caso de uso), no la tecnología.</p>
 */
@Repository // Ver nota en CursoRepositoryJpaAdapter sobre esta anotación.
@Profile("jpa")
public class AlumnoRepositoryJpaAdapter implements AlumnoRepositoryPort {

    private final SpringDataAlumnoRepository repository;

    public AlumnoRepositoryJpaAdapter(SpringDataAlumnoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Alumno> buscarPorId(Long alumnoId) {
        // Misma técnica que en cursos: map(this::toDomain) traduce dentro
        // del Optional y la entidad JPA no sale del adaptador.
        return repository.findById(alumnoId).map(this::toDomain);
    }

    // ------------------------------------------------------------------
    // MAPPER PRIVADO
    // ------------------------------------------------------------------

    /**
     * Fila de la tabla → objeto de dominio. El constructor de {@code Alumno}
     * revalida nombre y email: la frontera vuelve a actuar de control de
     * calidad de los datos que entran al núcleo.
     *
     * <p>No hay {@code toEntity} porque el puerto no declara escritura de
     * alumnos: no se escribe código "por si acaso".</p>
     */
    private Alumno toDomain(AlumnoJpaEntity entity) {
        return new Alumno(
                entity.getId(),
                entity.getNombre(),
                entity.getEmail());
    }
}

package com.curso.cleanarchitecture.tema06.persistence;

import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema06.base.Curso;
import com.curso.cleanarchitecture.tema06.base.CursoRepositoryPort;

/**
 * Adaptador JPA que implementa {@code CursoRepositoryPort} (sección 10).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}, así que este {@code @Repository} nunca se registra como
 * bean desde aquí; en el proyecto real existe su equivalente escaneado.</p>
 *
 * <p><b>Papel arquitectónico:</b> es el traductor entre dos mundos.
 * Hacia dentro implementa el puerto (idioma del dominio: {@code Curso});
 * hacia fuera usa Spring Data (idioma técnico: {@code CursoJpaEntity}).
 * Gracias a él, el caso de uso puede guardar cursos sin saber que existe
 * JPA. Si mañana la persistencia pasa a PostgreSQL, MongoDB o un servicio
 * REST, se reescribe este adaptador y el resto del sistema no cambia.</p>
 */
@Repository // Variante de @Component para adaptadores de persistencia. Además
            // de documentar la intención, activa la traducción de excepciones
            // de JPA a la jerarquía DataAccessException de Spring.
@Profile("jpa")
public class CursoRepositoryJpaAdapter implements CursoRepositoryPort {

    // El adaptador SÍ conoce Spring Data. Está en la capa externa: aquí es legal.
    private final SpringDataCursoRepository repository;

    /** Inyección por constructor: Spring pasa el repositorio generado. */
    public CursoRepositoryJpaAdapter(SpringDataCursoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Curso> buscarPorId(Long cursoId) {
        // findById devuelve Optional<CursoJpaEntity>; con map(this::toDomain)
        // lo convertimos en Optional<Curso> SIN que la entidad JPA cruce la frontera.
        return repository.findById(cursoId).map(this::toDomain);
    }

    @Override
    public void guardar(Curso curso) {
        // Sentido inverso: dominio -> entidad JPA -> save (INSERT o UPDATE
        // según el id, decisión que toma JPA, no nosotros).
        repository.save(toEntity(curso));
    }

    // ------------------------------------------------------------------
    // MAPPERS PRIVADOS: el corazón didáctico del adaptador.
    // ------------------------------------------------------------------

    /**
     * Fila de la tabla → objeto de dominio.
     *
     * <p>Reconstruimos el {@code Curso} pasando por su constructor, que
     * revalida las invariantes (nombre obligatorio, plazas no negativas).
     * Si alguien corrompe la tabla a mano, el problema explota AQUÍ, en la
     * frontera, y no en mitad de un caso de uso. La base de datos no es de
     * fiar; el dominio sí, porque se protege a sí mismo.</p>
     */
    private Curso toDomain(CursoJpaEntity entity) {
        return new Curso(
                entity.getId(),
                entity.getNombre(),
                entity.getPlazasDisponibles(),
                entity.isCerrado());
    }

    /**
     * Objeto de dominio → fila de la tabla.
     *
     * <p>Volcado plano usando los getters del dominio. Aquí no hay reglas de
     * negocio: solo transporte de datos. Un mapper que "decide" cosas es una
     * señal de alarma: las decisiones pertenecen al dominio o al caso de uso.</p>
     */
    private CursoJpaEntity toEntity(Curso curso) {
        return new CursoJpaEntity(
                curso.getId(),
                curso.getNombre(),
                curso.getPlazasDisponibles(),
                curso.isCerrado());
    }
}

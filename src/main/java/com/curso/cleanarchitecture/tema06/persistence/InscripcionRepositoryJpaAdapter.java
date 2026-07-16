package com.curso.cleanarchitecture.tema06.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema06.base.EstadoInscripcion;
import com.curso.cleanarchitecture.tema06.base.Inscripcion;
import com.curso.cleanarchitecture.tema06.base.InscripcionRepositoryPort;

/**
 * Adaptador JPA de inscripciones — el ejemplo literal de la sección 10 de la
 * teoría, comentado línea a línea.
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}; este {@code @Repository} nunca se registra desde aquí.</p>
 *
 * <p>Implementa el puerto {@code InscripcionRepositoryPort} (idioma de
 * negocio) delegando en {@code SpringDataInscripcionRepository} (idioma
 * técnico). Todas las traducciones viven en los dos mappers privados del
 * final. Ninguna entidad JPA entra ni sale de esta clase.</p>
 */
@Repository // Ver nota en CursoRepositoryJpaAdapter sobre esta anotación.
@Profile("jpa")
public class InscripcionRepositoryJpaAdapter implements InscripcionRepositoryPort {

    private final SpringDataInscripcionRepository repository;

    public InscripcionRepositoryJpaAdapter(SpringDataInscripcionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Inscripcion> buscarPorId(Long inscripcionId) {
        // Optional<InscripcionJpaEntity> -> Optional<Inscripcion>.
        return repository.findById(inscripcionId).map(this::toDomain);
    }

    @Override
    public boolean existeInscripcionActiva(Long cursoId, Long alumnoId) {
        // TRADUCCIÓN de idiomas en una línea:
        //   puerto:      "¿existe inscripción activa?"       (negocio)
        //   Spring Data: existsByCursoIdAndAlumnoIdAndEstado (técnica)
        // El enum del dominio se convierte al String de la tabla con name().
        return repository.existsByCursoIdAndAlumnoIdAndEstado(
                cursoId, alumnoId, EstadoInscripcion.ACTIVA.name());
    }

    @Override
    public List<Inscripcion> buscarPorAlumnoId(Long alumnoId) {
        // Lista de entidades JPA -> stream -> map a dominio -> lista inmutable.
        // Patrón habitual para colecciones en adaptadores.
        return repository.findByAlumnoId(alumnoId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void guardar(Inscripcion inscripcion) {
        // save() decide INSERT o UPDATE según el id (null -> INSERT y H2
        // genera el id por IDENTITY). El caso de uso no sabe nada de esto.
        repository.save(toEntity(inscripcion));
    }

    // ------------------------------------------------------------------
    // MAPPERS PRIVADOS toDomain / toEntity (el detalle más fino del tema)
    // ------------------------------------------------------------------

    /**
     * Fila de la tabla → objeto de dominio.
     *
     * <p>Detalle importante: {@code Inscripcion} nace siempre ACTIVA (así lo
     * impone su constructor, y está bien que lo imponga). Para reconstruir
     * una inscripción CANCELADA no usamos un setter que no existe: usamos la
     * propia acción de negocio {@code cancelar()}. El adaptador respeta las
     * reglas del dominio incluso al rehidratar desde la base de datos.</p>
     */
    private Inscripcion toDomain(InscripcionJpaEntity entity) {
        Inscripcion inscripcion = new Inscripcion(
                entity.getId(), entity.getCursoId(), entity.getAlumnoId());
        if (EstadoInscripcion.CANCELADA.name().equals(entity.getEstado())) {
            // String de la tabla -> transición legítima del dominio.
            inscripcion.cancelar();
        }
        return inscripcion;
    }

    /**
     * Objeto de dominio → fila de la tabla.
     *
     * <p>El enum {@code EstadoInscripcion} se serializa con {@code name()}
     * ('ACTIVA' / 'CANCELADA'), que es lo que verás en la consola H2 al
     * ejecutar {@code SELECT * FROM INSCRIPCIONES}.</p>
     */
    private InscripcionJpaEntity toEntity(Inscripcion inscripcion) {
        return new InscripcionJpaEntity(
                inscripcion.getId(),
                inscripcion.getCursoId(),
                inscripcion.getAlumnoId(),
                inscripcion.getEstado().name());
    }
}

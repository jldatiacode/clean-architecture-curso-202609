package com.curso.cleanarchitecture.tema05.adapter.out.persistence.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema05.base.Curso;
import com.curso.cleanarchitecture.tema05.base.CursoRepositoryPort;

/**
 * Adaptador de SALIDA con JPA (sección 9 de la teoría).
 *
 * <p>Implementa el MISMO puerto que {@code CursoRepositoryInMemoryAdapter},
 * pero delegando en Spring Data. Su trabajo es pura traducción:</p>
 * <ul>
 *   <li>Traducir la llamada del puerto a la API de Spring Data.</li>
 *   <li>Traducir los resultados de entidad JPA a dominio con el mapper.</li>
 * </ul>
 *
 * <p>Frase clave de la teoría: <b>el caso de uso solo conoce
 * {@code CursoRepositoryPort}; el adaptador conoce JPA</b>. Sustituir el
 * adaptador en memoria por este no toca ni el caso de uso ni el dominio:
 * solo cambia qué implementación del puerto se inyecta.</p>
 *
 * <p><b>MATERIAL DE LECTURA / ESBOZO:</b> este {@code @Repository} nunca se
 * escanea (la aplicación arrancable solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}). El desarrollo completo,
 * con H2 y configuración real, llega en el Tema 6 y vive en {@code proyecto}.</p>
 */
@Repository
public class CursoRepositoryJpaAdapter implements CursoRepositoryPort {

    private final SpringDataCursoRepository repository;

    /** El mapper no tiene estado: podemos instanciarlo directamente. */
    private final CursoPersistenceMapper mapper = new CursoPersistenceMapper();

    public CursoRepositoryJpaAdapter(SpringDataCursoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Curso> buscarPorId(Long cursoId) {
        // findById devuelve Optional<CursoJpaEntity>; map(mapper::toDomain)
        // lo convierte a Optional<Curso> sin que el Optional vacío cambie.
        return repository.findById(cursoId).map(mapper::toDomain);
    }

    @Override
    public List<Curso> buscarCursosDisponibles() {
        // La query derivada filtra en la base de datos; el mapper traduce
        // cada fila a dominio antes de cruzar la frontera hacia el núcleo.
        return repository.findByCerradoFalseAndPlazasDisponiblesGreaterThan(0)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void guardar(Curso curso) {
        // Sentido inverso: dominio → entidad JPA → save.
        repository.save(mapper.toEntity(curso));
    }
}

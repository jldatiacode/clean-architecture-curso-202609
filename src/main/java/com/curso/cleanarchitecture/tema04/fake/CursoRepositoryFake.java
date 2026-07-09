package com.curso.cleanarchitecture.tema04.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema04.model.Curso;

/**
 * Adaptador de salida FAKE: cursos en memoria.
 *
 * <p>Implementa {@link CursoRepositoryPort} con un simple {@link Map}. Es la
 * demostración práctica de la inversión de dependencias: esta clase DEPENDE
 * de la interfaz definida por la aplicación (la implementa), y no al revés.</p>
 *
 * <pre>
 *   InscribirAlumnoUseCase → CursoRepositoryPort ← CursoRepositoryFake
 * </pre>
 *
 * <p>En el Tema 5, un {@code CursoRepositoryJpaAdapter} ocupará el mismo hueco
 * sin que el caso de uso se entere.</p>
 */
public class CursoRepositoryFake implements CursoRepositoryPort {

    /** Almacén en memoria: id del curso → curso. */
    private final Map<Long, Curso> cursos = new HashMap<>();

    @Override
    public Optional<Curso> buscarPorId(Long cursoId) {
        // Optional.ofNullable traduce "no está en el mapa" al contrato del
        // puerto: "puede no existir".
        return Optional.ofNullable(cursos.get(cursoId));
    }

    @Override
    public List<Curso> buscarCursosDisponibles() {
        // El contrato dice "cursos donde es posible inscribirse": abiertos y
        // con plazas. El filtrado usa la propia regla de la entidad de dominio
        // (tienePlazasLibres) para no duplicar la lógica aquí.
        List<Curso> disponibles = new ArrayList<>();
        for (Curso curso : cursos.values()) {
            if (curso.tienePlazasLibres()) {
                disponibles.add(curso);
            }
        }
        return disponibles;
    }

    @Override
    public void guardar(Curso curso) {
        // En memoria, guardar es simplemente reemplazar la entrada del mapa.
        // Un adaptador JPA haría un save(); el contrato es idéntico.
        cursos.put(curso.getId(), curso);
    }
}

package com.curso.cleanarchitecture.tema03.fake;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema03.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema03.model.Curso;

/**
 * Fake en memoria de {@link CursoRepositoryPort}
 * (reproducido de la sección 10 de la teoría).
 *
 * <p>Un simple {@code Map} indexado por id hace el papel de tabla de cursos.
 * Cumple el contrato del puerto igual que lo haría un adaptador JPA, y eso es
 * lo importante: el caso de uso no nota la diferencia.</p>
 */
public class CursoRepositoryEnMemoria implements CursoRepositoryPort {

    /** "Tabla" en memoria: id del curso -> curso. */
    private final Map<Long, Curso> cursos = new HashMap<>();

    @Override
    public Optional<Curso> buscarPorId(Long cursoId) {
        // Optional.ofNullable convierte el "no está en el mapa" (null)
        // en un Optional vacío, que es lo que espera el caso de uso.
        return Optional.ofNullable(cursos.get(cursoId));
    }

    @Override
    public void guardar(Curso curso) {
        // put sirve tanto para insertar como para "actualizar":
        // si el id ya existía, se reemplaza la referencia guardada.
        cursos.put(curso.getId(), curso);
    }
}

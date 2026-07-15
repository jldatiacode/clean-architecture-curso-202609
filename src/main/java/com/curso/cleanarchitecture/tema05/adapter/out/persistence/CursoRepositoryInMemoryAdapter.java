package com.curso.cleanarchitecture.tema05.adapter.out.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema05.base.Curso;
import com.curso.cleanarchitecture.tema05.base.CursoRepositoryPort;

/**
 * Adaptador de SALIDA en memoria para cursos (sección 7 de la teoría).
 *
 * <p>Implementa {@link CursoRepositoryPort} con un simple {@link HashMap}.
 * Parece un juguete, pero cumple el contrato al 100%: para el caso de uso es
 * indistinguible de una base de datos real, porque el caso de uso solo conoce
 * el puerto.</p>
 *
 * <p>Detalle didáctico: el filtro de "cursos disponibles" (no cerrado y con
 * plazas) se aplica aquí porque forma parte del CONTRATO del puerto
 * ({@code buscarCursosDisponibles}). En la versión JPA ese mismo contrato se
 * cumple con una query derivada; el criterio es el mismo, cambia la tecnología.</p>
 *
 * <p>Sin anotaciones Spring a propósito: un adaptador no necesita ser bean
 * para existir. En el paquete {@code proyecto} se registra en la configuración;
 * aquí basta con hacer {@code new} en un test o en un {@code main}.</p>
 */
public class CursoRepositoryInMemoryAdapter implements CursoRepositoryPort {

    /** "Tabla" en memoria: clave = id del curso. */
    private final Map<Long, Curso> cursos = new HashMap<>();

    @Override
    public Optional<Curso> buscarPorId(Long cursoId) {
        // Optional.ofNullable: si el mapa no tiene la clave devuelve Optional.empty(),
        // y es el caso de uso quien decide qué significa "no encontrado".
        return Optional.ofNullable(cursos.get(cursoId));
    }

    @Override
    public List<Curso> buscarCursosDisponibles() {
        return cursos.values().stream()
                .filter(curso -> !curso.isCerrado())
                .filter(curso -> curso.getPlazasDisponibles() > 0)
                .toList();
    }

    @Override
    public void guardar(Curso curso) {
        cursos.put(curso.getId(), curso);
    }
}

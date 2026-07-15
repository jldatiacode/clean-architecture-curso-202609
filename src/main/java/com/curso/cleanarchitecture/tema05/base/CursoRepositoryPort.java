package com.curso.cleanarchitecture.tema05.base;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de SALIDA para la persistencia de cursos (definido en el tema 4).
 *
 * <p>Lo define la APLICACIÓN según sus necesidades, no la base de datos.
 * Por eso habla en términos de negocio ("cursos disponibles") y de modelo
 * de dominio ({@link Curso}), no de tablas ni de SQL.</p>
 *
 * <p>En este tema veremos DOS implementaciones del mismo contrato:</p>
 * <ul>
 *   <li>{@code CursoRepositoryInMemoryAdapter}: un mapa en memoria (sección 7).</li>
 *   <li>{@code CursoRepositoryJpaAdapter}: JPA + Spring Data (sección 9).</li>
 * </ul>
 * <p>El caso de uso no cambia ni una línea al sustituir una por otra:
 * esa es la ventaja de depender del puerto y no de la tecnología.</p>
 */
public interface CursoRepositoryPort {

    Optional<Curso> buscarPorId(Long cursoId);

    List<Curso> buscarCursosDisponibles();

    void guardar(Curso curso);
}

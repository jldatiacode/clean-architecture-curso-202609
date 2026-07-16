package com.curso.cleanarchitecture.tema06.base;

import java.util.Optional;

/**
 * Puerto de salida para cursos — visto en el tema 3.
 *
 * <p>Expresa una <b>necesidad</b> de la capa de aplicación ("necesito buscar
 * y guardar cursos") sin decir nada de tecnología: ni SQL, ni JPA, ni H2.
 * Habla el idioma del dominio ({@code Curso}) y usa tipos de {@code java.*}
 * ({@code Optional}).</p>
 *
 * <p>En el tema 4 lo implementaba un repositorio en memoria (fake). En este
 * tema lo implementa {@code CursoRepositoryJpaAdapter} con Spring Data JPA.
 * El caso de uso no nota la diferencia: esa es la gracia de los puertos.</p>
 */
public interface CursoRepositoryPort {

    /** Busca un curso por id. {@code Optional} vacío si no existe. */
    Optional<Curso> buscarPorId(Long cursoId);

    /** Persiste el estado actual del curso (por ejemplo, tras ocupar una plaza). */
    void guardar(Curso curso);
}

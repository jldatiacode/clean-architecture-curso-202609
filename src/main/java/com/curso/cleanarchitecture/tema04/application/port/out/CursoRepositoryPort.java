package com.curso.cleanarchitecture.tema04.application.port.out;

import java.util.List;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.model.Curso;

/**
 * Puerto de salida: persistencia de cursos.
 *
 * <p>Contrato de la sección 4 de la teoría. Fíjate en el vocabulario: este
 * contrato NO habla de JPA, SQL, H2 ni PostgreSQL. Habla en términos de
 * aplicación y dominio: "buscar por id", "cursos disponibles", "guardar".</p>
 *
 * <p>¿Por qué {@code Optional<Curso>} y no {@code Curso} a secas? Porque
 * "puede no existir" forma parte del contrato: obligamos a quien lo use a
 * decidir explícitamente qué hacer cuando el curso no está.</p>
 *
 * <p>¿Por qué devuelve {@code Curso} (dominio) y nunca una entidad JPA?
 * Porque si devolviera {@code CursoJpaEntity}, la capa de aplicación quedaría
 * contaminada: cualquier cambio en el mapeo de base de datos rompería los
 * casos de uso. El puerto es la frontera; lo que la cruza es dominio.</p>
 */
public interface CursoRepositoryPort {

    /**
     * Busca un curso por su identificador.
     *
     * @param cursoId identificador del curso
     * @return el curso si existe; vacío en caso contrario
     */
    Optional<Curso> buscarPorId(Long cursoId);

    /**
     * Recupera los cursos en los que es posible inscribirse:
     * abiertos y con plazas libres.
     *
     * @return lista (posiblemente vacía) de cursos disponibles
     */
    List<Curso> buscarCursosDisponibles();

    /**
     * Persiste el estado actual del curso (creación o actualización).
     * Cómo se haga (INSERT, UPDATE, put en un mapa...) es cosa del adaptador.
     *
     * @param curso curso a guardar
     */
    void guardar(Curso curso);
}

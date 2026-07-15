package com.curso.cleanarchitecture.tema05.adapter.out.persistence.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio de Spring Data JPA (sección 9 de la teoría).
 *
 * <p>Solo declaramos la interfaz: Spring Data genera la implementación en
 * tiempo de arranque. Con {@code JpaRepository<CursoJpaEntity, Long>} heredamos
 * {@code findById}, {@code save}, {@code findAll}, etc.</p>
 *
 * <p>Matiz arquitectónico importante: esta interfaz NO es nuestro puerto.
 * Es un detalle tecnológico que trabaja con {@code CursoJpaEntity} (modelo de
 * persistencia) y pertenece por completo al adaptador. El puerto de la
 * aplicación sigue siendo {@code CursoRepositoryPort}, que habla en dominio.
 * Si el caso de uso dependiera de {@code JpaRepository}, el núcleo quedaría
 * atado a Spring Data para siempre.</p>
 *
 * <p><b>ESBOZO DIDÁCTICO:</b> se activa de verdad en el Tema 6 (Spring Boot +
 * H2) y en {@code com.curso.cleanarchitecture.proyecto}; aquí nunca se escanea.</p>
 */
public interface SpringDataCursoRepository extends JpaRepository<CursoJpaEntity, Long> {

    /**
     * Query derivada del nombre del método ("derived query"): Spring Data la
     * traduce a SQL: {@code WHERE cerrado = false AND plazas_disponibles > ?}.
     * Es el equivalente JPA del doble filtro que el adaptador en memoria
     * hacía con streams: mismo contrato, distinta tecnología.
     */
    List<CursoJpaEntity> findByCerradoFalseAndPlazasDisponiblesGreaterThan(int plazasMinimas);
}

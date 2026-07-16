package com.curso.cleanarchitecture.tema06.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data para cursos (sección 9 de la teoría).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}, así que Spring nunca genera una implementación de esta
 * interfaz. En el proyecto real existe su equivalente escaneado.</p>
 *
 * <p>No escribimos ninguna implementación: al extender {@code JpaRepository}
 * heredamos {@code findById}, {@code save}, {@code findAll}, {@code delete}...
 * y Spring Data genera el código en el arranque. Comodidad enorme, y
 * precisamente por eso hay que ubicarla bien: esto es un DETALLE de
 * infraestructura. Si un caso de uso dependiera de esta interfaz, quedaría
 * atado a Spring Data para siempre. Por eso entre medias está el puerto
 * {@code CursoRepositoryPort} y el adaptador que lo implementa.</p>
 */
public interface SpringDataCursoRepository extends JpaRepository<CursoJpaEntity, Long> {
    // Sin métodos extra: el CRUD heredado cubre lo que necesita el adaptador.
}

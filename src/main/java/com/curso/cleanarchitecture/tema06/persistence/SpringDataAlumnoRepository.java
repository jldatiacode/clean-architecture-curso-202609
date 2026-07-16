package com.curso.cleanarchitecture.tema06.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data para alumnos (sección 9 de la teoría).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}; Spring nunca instancia esta interfaz desde aquí.</p>
 *
 * <p>Igual que {@code SpringDataCursoRepository}: interfaz vacía que hereda
 * el CRUD completo de {@code JpaRepository<AlumnoJpaEntity, Long>}. Trabaja
 * con la ENTIDAD JPA y con tipos técnicos; nunca con el dominio. La
 * traducción es trabajo del adaptador.</p>
 */
public interface SpringDataAlumnoRepository extends JpaRepository<AlumnoJpaEntity, Long> {
}

package com.curso.cleanarchitecture.tema06.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA de la tabla {@code alumnos} (sección 9 de la teoría).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}; esta clase compila pero nunca se registra en Hibernate.</p>
 *
 * <p>Mismo patrón que {@code CursoJpaEntity}: espejo de la tabla, constructor
 * protegido para JPA, getters/setters sin reglas. La entidad de dominio
 * {@code tema06.base.Alumno} (con sus validaciones) es otra clase distinta;
 * el adaptador traduce entre ambas.</p>
 */
@Entity
@Table(name = "alumnos")
public class AlumnoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;

    /** Constructor protegido exigido por JPA (instanciación por reflexión). */
    protected AlumnoJpaEntity() {
    }

    /** Constructor completo para los mappers del adaptador. */
    public AlumnoJpaEntity(Long id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

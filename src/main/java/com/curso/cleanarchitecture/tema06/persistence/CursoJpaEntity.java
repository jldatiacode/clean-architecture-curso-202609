package com.curso.cleanarchitecture.tema06.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA de la tabla {@code cursos} (sección 9 de la teoría).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}, así que esta {@code @Entity} compila pero Hibernate nunca
 * la registra ni crea su tabla desde aquí.</p>
 *
 * <p><b>No confundir con {@code tema06.base.Curso}:</b> aquella es la entidad
 * de DOMINIO (reglas de negocio, sin anotaciones); esta es la entidad de
 * PERSISTENCIA (un espejo de la tabla, sin reglas). Puede parecer duplicación,
 * pero es una separación deliberada: si mañana la tabla necesita columnas de
 * auditoría o cambia de nombre, se toca esta clase y el dominio ni se entera.</p>
 *
 * <p>Esta clase está hecha "a gusto de JPA": constructor vacío, getters y
 * setters, tipos simples. Todo lo que en el dominio sería mala práctica,
 * aquí es simplemente el contrato que pide el framework. Por eso las
 * concesiones al framework se encierran en la capa externa.</p>
 */
@Entity                  // Marca la clase como persistente para JPA/Hibernate.
@Table(name = "cursos")  // Nombre explícito de la tabla (la que consultas en la consola H2).
public class CursoJpaEntity {

    @Id                                                 // Clave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // El id lo genera la columna IDENTITY de H2.
    private Long id;

    // Hibernate convierte camelCase a snake_case: plazasDisponibles -> plazas_disponibles.
    private String nombre;
    private int plazasDisponibles;
    private boolean cerrado;

    /**
     * Constructor protegido EXIGIDO por JPA: Hibernate crea las instancias
     * por reflexión al leer filas de la base de datos y necesita poder
     * instanciar sin argumentos. Es {@code protected} para que el resto del
     * código no pueda crear entidades "vacías" por accidente: solo JPA
     * (y las subclases proxy que genera) lo usan.
     */
    protected CursoJpaEntity() {
    }

    /** Constructor completo: lo usa el adaptador en su mapper {@code toEntity}. */
    public CursoJpaEntity(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
    }

    // Getters y setters "planos": aquí sí están permitidos. Esta clase no
    // protege reglas de negocio; solo transporta datos entre Java y SQL.
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

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public void setPlazasDisponibles(int plazasDisponibles) {
        this.plazasDisponibles = plazasDisponibles;
    }

    public boolean isCerrado() {
        return cerrado;
    }

    public void setCerrado(boolean cerrado) {
        this.cerrado = cerrado;
    }
}

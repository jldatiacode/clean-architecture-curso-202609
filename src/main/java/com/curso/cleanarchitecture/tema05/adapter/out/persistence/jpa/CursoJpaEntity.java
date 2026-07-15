package com.curso.cleanarchitecture.tema05.adapter.out.persistence.jpa;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Entidad JPA de curso (sección 8 de la teoría).
 *
 * <p>Idea clave: <b>una entidad JPA representa persistencia, no necesariamente
 * dominio</b>. Esta clase modela una fila de la tabla {@code cursos}; el
 * negocio lo modela {@code Curso} (paquete {@code base}). Aunque hoy sus
 * campos coincidan, sus razones para cambiar son distintas: la entidad cambia
 * con el esquema de base de datos, el dominio cambia con las reglas de negocio.
 * Mantenerlas separadas (con un mapper en medio) evita que JPA contamine el
 * núcleo con constructores vacíos, setters obligatorios y anotaciones.</p>
 *
 * <p>Por eso esta entidad es "anémica" a propósito: solo datos, sin reglas.
 * Las reglas ({@code ocuparPlaza}, {@code admiteInscripciones}) viven en el
 * dominio.</p>
 *
 * <p><b>MATERIAL DE LECTURA / ESBOZO:</b> este diseño se desarrolla del todo
 * en el Tema 6 y funciona de verdad en {@code com.curso.cleanarchitecture.proyecto}.
 * La aplicación arrancable solo escanea {@code proyecto}, así que este
 * {@code @Entity} nunca llega a registrarse en el contexto de persistencia.</p>
 */
@Entity
@Table(name = "cursos")
public class CursoJpaEntity {

    /**
     * En este esbozo el id lo asigna la aplicación. En el Tema 6 veremos
     * {@code @GeneratedValue} para delegarlo en la base de datos.
     */
    @Id
    private Long id;

    private String nombre;

    private int plazasDisponibles;

    private boolean cerrado;

    /**
     * Constructor sin argumentos exigido por la especificación JPA: el
     * proveedor (Hibernate) lo usa para instanciar la entidad por reflexión.
     * Lo dejamos {@code protected} para que nadie lo use por error desde
     * fuera. Este tipo de imposiciones del framework es justo lo que NO
     * queremos en el modelo de dominio.
     */
    protected CursoJpaEntity() {
    }

    public CursoJpaEntity(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        this.cerrado = cerrado;
    }

    // Getters y setters: la entidad JPA es un contenedor de datos mutable,
    // al gusto del framework de persistencia.

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

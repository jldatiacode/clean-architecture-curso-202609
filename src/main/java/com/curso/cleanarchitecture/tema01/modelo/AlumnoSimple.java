package com.curso.cleanarchitecture.tema01.modelo;

/**
 * Modelo ANÉMICO de un alumno: una bolsa de datos con getters y setters.
 *
 * <p><b>Capa que representa:</b> igual que {@link CursoSimple}, ninguna clara.
 * Es el típico "bean" que nace pensando en la tabla ALUMNOS y no en el concepto
 * de negocio Alumno (el error de diseñar desde la base de datos, sección 4.3 de
 * la teoría).</p>
 *
 * <p><b>Qué enseña:</b> que un modelo sin comportamiento tampoco puede expresar
 * restricciones tan básicas como "un alumno debe tener nombre" o "el email debe
 * ser válido". Todas esas comprobaciones acabarán repartidas por controladores
 * y servicios, cada uno con su propia versión, y con el tiempo aparecerán
 * inconsistencias.</p>
 *
 * <p><b>Dependencias:</b> ninguna. Java puro.</p>
 */
public class AlumnoSimple {

    private Long id;

    // Sin validación en el modelo, nada impide un alumno con nombre null o
    // vacío. La comprobación, si existe, vivirá dispersa en el código cliente.
    private String nombre;

    private String email;

    /**
     * Constructor vacío para frameworks: permite estados intermedios inválidos
     * (un alumno sin identidad ni nombre).
     */
    public AlumnoSimple() {
    }

    public AlumnoSimple(Long id, String nombre, String email) {
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

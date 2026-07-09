package com.curso.cleanarchitecture.tema02.domain.model;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;

/**
 * Entidad de dominio {@code Curso} (versión completa de la sección 14 de la teoría).
 *
 * <p><b>Capa:</b> dominio. <b>Responsabilidad:</b> representar un curso del
 * negocio y proteger sus reglas: nombre válido, plazas no negativas y control
 * de cuándo puede recibir inscripciones.</p>
 *
 * <p><b>Dependencias que NO debe tener:</b> Spring, JPA, Hibernate, HTTP, SQL.
 * Esta clase debe poder compilarse y probarse con Java puro. Si mañana cambia
 * la base de datos o el framework web, {@code Curso} no se entera.</p>
 *
 * <p>Fíjate en que es un <b>modelo rico</b>: no hay setters genéricos. Quien
 * quiera modificar un curso debe hacerlo a través de acciones con significado
 * de negocio ({@code cerrar()}, {@code ocuparPlaza()}...), lo que impide dejar
 * el objeto en un estado inválido desde fuera.</p>
 */
public class Curso {

    // La identidad es final: un curso no cambia de id una vez creado.
    // Cambiar el id equivaldría a convertirlo en "otro" curso.
    private final Long id;

    // Mutables, pero SOLO a través de métodos de negocio que validan.
    private String nombre;
    private int plazasDisponibles;
    private boolean cerrado;

    /**
     * Crea un curso válido o falla. No existe la posibilidad de construir
     * un curso sin nombre o con plazas negativas: las invariantes se
     * protegen desde el constructor.
     */
    public Curso(Long id, String nombre, int plazasDisponibles) {
        validarNombre(nombre);
        validarPlazasIniciales(plazasDisponibles);

        this.id = id;
        this.nombre = nombre;
        this.plazasDisponibles = plazasDisponibles;
        // REGLA: todo curso nace abierto; cerrarlo es una acción explícita de negocio.
        this.cerrado = false;
    }

    /**
     * Renombra el curso validando la misma regla que en la creación.
     * Es la alternativa correcta a un {@code setNombre} sin control:
     * el nombre nunca puede quedar vacío ni con menos de 3 caracteres.
     */
    public void cambiarNombre(String nuevoNombre) {
        validarNombre(nuevoNombre);
        this.nombre = nuevoNombre;
    }

    /** Acción de negocio: el curso deja de admitir inscripciones. */
    public void cerrar() {
        this.cerrado = true;
    }

    /** Acción de negocio: el curso vuelve a admitir inscripciones. */
    public void abrir() {
        this.cerrado = false;
    }

    /**
     * Ocupa una plaza del curso. Antes de tocar el estado se comprueban las
     * reglas de inscripción: así es imposible que las plazas bajen de cero
     * o que un curso cerrado "acepte" alumnos por error.
     */
    public void ocuparPlaza() {
        validarPuedeRecibirInscripcion();
        this.plazasDisponibles--;
    }

    /**
     * Libera una plaza (por ejemplo, al cancelarse una inscripción).
     * La invoca {@code Inscripcion.cancelar()}.
     */
    public void liberarPlaza() {
        this.plazasDisponibles++;
    }

    /**
     * Regla de negocio central del curso, pública a propósito: en temas
     * posteriores los casos de uso podrán preguntar al curso si admite
     * inscripciones ANTES de crear la inscripción, sin duplicar la regla.
     */
    public void validarPuedeRecibirInscripcion() {
        // REGLA: un curso cerrado no permite nuevas inscripciones.
        if (cerrado) {
            throw new ReglaNegocioException("No se puede inscribir en un curso cerrado");
        }

        // REGLA: un curso sin plazas no acepta inscripciones.
        if (plazasDisponibles <= 0) {
            throw new ReglaNegocioException("No hay plazas disponibles en el curso");
        }
    }

    // Validación privada: vive junto al estado que protege. Ni el controlador
    // ni el caso de uso necesitan (ni deben) repetirla.
    private void validarNombre(String nombre) {
        // REGLA: un curso no puede tener nombre vacío.
        if (nombre == null || nombre.isBlank()) {
            throw new ReglaNegocioException("El nombre del curso es obligatorio");
        }

        // REGLA: el nombre debe tener una longitud mínima con sentido de negocio.
        if (nombre.length() < 3) {
            throw new ReglaNegocioException("El nombre del curso debe tener al menos 3 caracteres");
        }
    }

    private void validarPlazasIniciales(int plazasDisponibles) {
        // REGLA: un curso no puede nacer con plazas negativas.
        if (plazasDisponibles < 0) {
            throw new ReglaNegocioException("Las plazas disponibles no pueden ser negativas");
        }
    }

    // Getters de solo lectura: exponemos el estado, pero no permitimos
    // modificarlo sin pasar por las reglas de negocio.
    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPlazasDisponibles() {
        return plazasDisponibles;
    }

    public boolean isCerrado() {
        return cerrado;
    }
}

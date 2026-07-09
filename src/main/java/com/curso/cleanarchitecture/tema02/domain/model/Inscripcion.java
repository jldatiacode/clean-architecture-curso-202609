package com.curso.cleanarchitecture.tema02.domain.model;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;

import java.time.LocalDate;

/**
 * Entidad de dominio {@code Inscripcion} (sección 21 de la teoría):
 * la relación entre un {@link Alumno} y un {@link Curso}.
 *
 * <p><b>Capa:</b> dominio. <b>Responsabilidad:</b> garantizar que una
 * inscripción siempre tiene curso y alumno, gestionar su ciclo de vida
 * (activa/cancelada) y mantener coherente el cupo del curso: crearse ocupa
 * una plaza y cancelarse la libera.</p>
 *
 * <p><b>Dependencias que NO debe tener:</b> Spring, JPA, repositorios.
 * Solo {@code java.time} y el propio modelo de dominio.</p>
 *
 * <p>Nota didáctica honesta (la teoría también la hace): que la inscripción
 * modifique directamente el curso es una simplificación válida para un curso
 * introductorio. En proyectos reales habría que valorar si esa coordinación
 * pertenece a un caso de uso, y qué ocurre con la concurrencia (dos alumnos
 * inscribiéndose a la vez). El diseño del dominio depende del contexto.</p>
 */
public class Inscripcion {

    // Identidad y relaciones inmutables: una inscripción no cambia de curso
    // ni de alumno; si eso "cambiara", sería otra inscripción distinta.
    private final Long id;
    private final Curso curso;
    private final Alumno alumno;
    private final LocalDate fechaInscripcion;

    // Único atributo mutable: el estado, y solo cambia vía cancelar().
    private EstadoInscripcion estado;

    public Inscripcion(Long id, Curso curso, Alumno alumno) {
        // REGLA: no existe inscripción sin curso.
        if (curso == null) {
            throw new ReglaNegocioException("El curso es obligatorio para crear una inscripción");
        }

        // REGLA: no existe inscripción sin alumno.
        if (alumno == null) {
            throw new ReglaNegocioException("El alumno es obligatorio para crear una inscripción");
        }

        // REGLA: crear la inscripción ocupa una plaza. Si el curso está cerrado
        // o sin plazas, ocuparPlaza() lanza ReglaNegocioException y la
        // inscripción NUNCA llega a construirse: no hay estados intermedios.
        curso.ocuparPlaza();

        this.id = id;
        this.curso = curso;
        this.alumno = alumno;
        // La fecha se fija al crear: es un hecho del negocio, no un dato editable.
        this.fechaInscripcion = LocalDate.now();
        this.estado = EstadoInscripcion.ACTIVA;
    }

    /**
     * Acción de negocio: cancelar la inscripción.
     * Cambia el estado Y devuelve la plaza al curso, manteniendo ambas
     * entidades coherentes en una sola operación con nombre de negocio
     * (compárese con un hipotético {@code setEstado("CANCELADA")}).
     */
    public void cancelar() {
        // REGLA: una inscripción cancelada no puede volver a cancelarse.
        // Sin esta guarda, cada cancelación repetida liberaría una plaza
        // extra y el cupo del curso quedaría corrupto.
        if (estado == EstadoInscripcion.CANCELADA) {
            throw new ReglaNegocioException("La inscripción ya está cancelada");
        }

        this.estado = EstadoInscripcion.CANCELADA;
        this.curso.liberarPlaza();
    }

    /** Pregunta de negocio expresiva: evita que el exterior compare enums a mano. */
    public boolean estaActiva() {
        return estado == EstadoInscripcion.ACTIVA;
    }

    public Long getId() {
        return id;
    }

    public Curso getCurso() {
        return curso;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public EstadoInscripcion getEstado() {
        return estado;
    }
}

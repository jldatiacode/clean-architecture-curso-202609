package com.curso.cleanarchitecture.tema02.domain.model;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests unitarios de la entidad {@link Inscripcion}.
 *
 * <p>Estos tests verifican COMPORTAMIENTO coordinado entre entidades
 * (inscripción y curso) sin ningún repositorio ni mock: usamos las entidades
 * reales porque son Java puro. Cuando el dominio está limpio, los mocks solo
 * hacen falta en las fronteras (repositorios, servicios externos), que
 * aparecerán en temas posteriores.</p>
 */
class InscripcionTest {

    // Pequeños métodos de apoyo para que cada test lea como el escenario
    // de negocio que comprueba, sin ruido de construcción.
    private Curso cursoConPlazas(int plazas) {
        return new Curso(1L, "Clean Architecture", plazas);
    }

    private Alumno alumno() {
        return new Alumno(1L, "Ana García", new Email("ana@empresa.com"));
    }

    @Test
    void crearUnaInscripcionDeberiaOcuparUnaPlazaDelCurso() {
        Curso curso = cursoConPlazas(2);

        Inscripcion inscripcion = new Inscripcion(1L, curso, alumno());

        // Efecto de negocio: la creación consume cupo automáticamente.
        assertEquals(1, curso.getPlazasDisponibles());
        assertTrue(inscripcion.estaActiva());
        assertEquals(EstadoInscripcion.ACTIVA, inscripcion.getEstado());
    }

    @Test
    void cancelarDeberiaLiberarLaPlazaYCambiarElEstado() {
        Curso curso = cursoConPlazas(2);
        Inscripcion inscripcion = new Inscripcion(1L, curso, alumno());

        inscripcion.cancelar();

        // El cupo vuelve a su valor original y el estado refleja la cancelación.
        assertEquals(2, curso.getPlazasDisponibles());
        assertFalse(inscripcion.estaActiva());
        assertEquals(EstadoInscripcion.CANCELADA, inscripcion.getEstado());
    }

    @Test
    void noDeberiaPoderCancelarseDosVeces() {
        Curso curso = cursoConPlazas(2);
        Inscripcion inscripcion = new Inscripcion(1L, curso, alumno());
        inscripcion.cancelar();

        // Segunda cancelación: prohibida. Si se permitiera, cada repetición
        // liberaría una plaza extra y corrompería el cupo del curso.
        assertThrows(ReglaNegocioException.class, inscripcion::cancelar);

        // ALTERNATIVA MÁS LEGIBLE con AssertJ: verificamos que el cupo NO se
        // ha inflado por el intento fallido.
        assertThat(curso.getPlazasDisponibles()).isEqualTo(2);
    }

    @Test
    void noDeberiaCrearInscripcionSinCursoOSinAlumno() {
        // Ambas reglas de obligatoriedad se comprueban antes de tocar el cupo.
        assertThrows(ReglaNegocioException.class,
                () -> new Inscripcion(1L, null, alumno()));

        assertThrows(ReglaNegocioException.class,
                () -> new Inscripcion(1L, cursoConPlazas(2), null));
    }
}

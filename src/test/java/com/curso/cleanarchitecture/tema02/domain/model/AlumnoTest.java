package com.curso.cleanarchitecture.tema02.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;
import com.curso.cleanarchitecture.tema02.domain.model.Alumno;
import com.curso.cleanarchitecture.tema02.domain.model.Email;

/**
 * Tests unitarios de la entidad {@link Alumno}.
 *
 * <p>Obsérvese el reparto de responsabilidades en los tests: aquí NO probamos
 * el formato del email (eso ya lo cubre {@code EmailTest}); probamos lo que es
 * responsabilidad de {@code Alumno}: tener nombre y tener email.</p>
 */
class AlumnoTest {

    @Test
    void deberiaCrearUnAlumnoValido() {
        Alumno alumno = new Alumno(1L, "Ana García", new Email("ana@empresa.com"));

        assertEquals("Ana García", alumno.getNombre());
        // El email llega ya normalizado por el objeto de valor.
        assertEquals("ana@empresa.com", alumno.getEmail().getValor());
    }

    @Test
    void noDeberiaCrearAlumnoSinNombre() {
        assertThrows(ReglaNegocioException.class,
                () -> new Alumno(1L, "  ", new Email("ana@empresa.com")));
    }

    @Test
    void noDeberiaCrearAlumnoSinEmail() {
        // El email es un objeto de valor: la única forma de "no tener email"
        // es pasar null, y la entidad también lo prohíbe.
        assertThrows(ReglaNegocioException.class,
                () -> new Alumno(1L, "Ana García", null));
    }

    @Test
    void deberiaCambiarElEmailConUnMetodoDeNegocio() {
        Alumno alumno = new Alumno(1L, "Ana García", new Email("ana@empresa.com"));

        // cambiarEmail expresa intención de negocio; no hay setEmail.
        alumno.cambiarEmail(new Email("Ana.Garcia@NuevaEmpresa.com"));

        // ALTERNATIVA MÁS LEGIBLE con AssertJ para el resultado compuesto.
        assertThat(alumno.getEmail()).isEqualTo(new Email("ana.garcia@nuevaempresa.com"));

        // Y el cambio a un email nulo sigue estando prohibido.
        assertThrows(ReglaNegocioException.class, () -> alumno.cambiarEmail(null));
    }
}

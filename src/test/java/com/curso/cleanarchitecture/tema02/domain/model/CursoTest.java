package com.curso.cleanarchitecture.tema02.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;
import com.curso.cleanarchitecture.tema02.domain.model.Curso;

/**
 * Tests unitarios de la entidad {@link Curso}.
 *
 * <p>Punto didáctico central (sección 30 de la teoría): probamos reglas de
 * negocio SIN Spring Boot, sin base de datos y sin HTTP. Solo JUnit y la
 * entidad. Por eso estos tests arrancan en milisegundos: es la recompensa
 * directa de tener un dominio en Java puro.</p>
 */
class CursoTest {

    @Test
    void deberiaOcuparUnaPlazaSiElCursoEstaAbiertoYTienePlazas() {
        Curso curso = new Curso(1L, "Clean Architecture", 2);

        curso.ocuparPlaza();

        // Estilo JUnit clásico, como en la teoría: assertEquals(esperado, real).
        assertEquals(1, curso.getPlazasDisponibles());
    }

    @Test
    void noDeberiaOcuparPlazaSiElCursoEstaCerrado() {
        Curso curso = new Curso(1L, "Clean Architecture", 2);
        curso.cerrar();

        // La regla vive en la entidad: el test la comprueba directamente,
        // sin montar ningún controlador que la "traduzca".
        assertThrows(ReglaNegocioException.class, curso::ocuparPlaza);
    }

    @Test
    void noDeberiaOcuparPlazaSiNoHayPlazasDisponibles() {
        // Curso válido pero sin cupo desde el inicio.
        Curso curso = new Curso(1L, "Clean Architecture", 0);

        assertThrows(ReglaNegocioException.class, curso::ocuparPlaza);
    }

    @Test
    void noDeberiaCrearCursoSinNombre() {
        // La invariante se protege en el constructor: el objeto inválido
        // ni siquiera llega a existir.
        assertThrows(ReglaNegocioException.class, () -> new Curso(1L, "", 10));
    }

    @Test
    void noDeberiaCrearCursoConNombreDeMenosDeTresCaracteres() {
        // "IA" tiene 2 caracteres: incumple la regla de longitud mínima.
        assertThrows(ReglaNegocioException.class, () -> new Curso(1L, "IA", 10));
    }

    @Test
    void noDeberiaCrearCursoConPlazasNegativas() {
        assertThrows(ReglaNegocioException.class,
                () -> new Curso(1L, "Clean Architecture", -1));
    }

    @Test
    void deberiaLiberarUnaPlazaTrasOcuparla() {
        Curso curso = new Curso(1L, "Clean Architecture", 5);
        curso.ocuparPlaza();

        curso.liberarPlaza();

        // ALTERNATIVA MÁS LEGIBLE: AssertJ permite encadenar aserciones que se
        // leen casi como una frase. Compáralo con los assertEquals de arriba;
        // ambas opciones son válidas, elegid una y sed consistentes en equipo.
        assertThat(curso.getPlazasDisponibles()).isEqualTo(5);
        assertThat(curso.isCerrado()).isFalse();
    }
}

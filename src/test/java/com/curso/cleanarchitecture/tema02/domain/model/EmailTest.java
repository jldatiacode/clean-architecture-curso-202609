package com.curso.cleanarchitecture.tema02.domain.model;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests unitarios del objeto de valor {@link Email}.
 *
 * <p>Lo relevante aquí: las reglas del email se prueban UNA vez, en la clase
 * que las posee. Si el email fuera un {@code String} suelto, estas mismas
 * comprobaciones estarían repetidas (o ausentes) en cada lugar que lo usa.</p>
 */
class EmailTest {

    @Test
    void deberiaGuardarElEmailEnMinusculas() {
        // La normalización es una regla del objeto de valor, no una cortesía
        // del que llama: da igual cómo escriba el usuario su email.
        Email email = new Email("Ana.GARCIA@Empresa.COM");

        assertEquals("ana.garcia@empresa.com", email.getValor());
    }

    @Test
    void noDeberiaCrearEmailSinArroba() {
        assertThrows(ReglaNegocioException.class, () -> new Email("ana.empresa.com"));
    }

    @Test
    void noDeberiaCrearEmailVacio() {
        // isBlank() cubre null implícitamente en la guarda: probamos ambos casos.
        assertThrows(ReglaNegocioException.class, () -> new Email("   "));
        assertThrows(ReglaNegocioException.class, () -> new Email(null));
    }

    @Test
    void dosEmailsConElMismoTextoDeberianSerIguales() {
        // Igualdad POR VALOR: seña de identidad de un objeto de valor.
        // Nótese que además la normalización hace iguales dos escrituras distintas.
        Email uno = new Email("ana@empresa.com");
        Email otro = new Email("ANA@empresa.com");

        // ALTERNATIVA MÁS LEGIBLE con AssertJ: isEqualTo usa equals() y
        // hasSameHashCodeAs verifica el contrato equals/hashCode de una vez.
        assertThat(uno).isEqualTo(otro);
        assertThat(uno).hasSameHashCodeAs(otro);
    }
}

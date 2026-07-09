package com.curso.cleanarchitecture.tema02.domain.model;

import com.curso.cleanarchitecture.tema02.domain.exception.ReglaNegocioException;

import java.util.Objects;

/**
 * Objeto de valor {@code Email} (sección 19 de la teoría).
 *
 * <p><b>Capa:</b> dominio. <b>Responsabilidad:</b> representar el concepto
 * "email" con sus reglas incorporadas, en lugar de pasear un {@code String}
 * cualquiera por todo el sistema.</p>
 *
 * <p><b>Dependencias que NO debe tener:</b> ninguna técnica. Solo
 * {@code java.util} y la excepción de dominio.</p>
 *
 * <p>Diferencia clave con una entidad: un objeto de valor <b>no tiene
 * identidad propia</b>. Dos alumnos con el mismo email no son el mismo alumno,
 * pero dos objetos {@code Email} con el mismo texto SÍ son equivalentes.
 * Por eso esta clase redefine {@code equals} y {@code hashCode} por valor,
 * y por eso es <b>inmutable</b>: si el email cambia, se crea otro objeto.</p>
 */
public class Email {

    // Inmutable: el valor se fija en el constructor y nunca cambia.
    // Un objeto de valor que muta deja de ser fiable como "valor".
    private final String valor;

    public Email(String valor) {
        // REGLA: el email es obligatorio. La validación vive AQUÍ, una sola vez,
        // en lugar de repetirse en Alumno, en DTOs, en controladores...
        if (valor == null || valor.isBlank()) {
            throw new ReglaNegocioException("El email es obligatorio");
        }

        // REGLA: formato mínimo, debe contener '@'. (Deliberadamente simple:
        // el objetivo didáctico es dónde vive la regla, no una regex perfecta.)
        if (!valor.contains("@")) {
            throw new ReglaNegocioException("El email no tiene un formato válido");
        }

        // REGLA: se almacena normalizado en minúsculas, de forma que
        // "Ana@Mail.com" y "ana@mail.com" sean el mismo valor de dominio.
        this.valor = valor.toLowerCase();
    }

    public String getValor() {
        return valor;
    }

    /**
     * Igualdad por valor: dos {@code Email} con el mismo texto son el mismo
     * concepto. Esto es lo que define a un objeto de valor frente a una entidad
     * (que se compara por identidad/id).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email email)) return false;
        return Objects.equals(valor, email.valor);
    }

    // Contrato equals/hashCode: si dos objetos son iguales, su hash debe coincidir.
    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}

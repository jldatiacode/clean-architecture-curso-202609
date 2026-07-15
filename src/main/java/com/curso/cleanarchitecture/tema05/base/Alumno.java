package com.curso.cleanarchitecture.tema05.base;

/**
 * Modelo de dominio {@code Alumno} (visto en temas 3 y 4).
 *
 * <p>Como todo el núcleo, no depende de ningún framework. El email se usa
 * en la notificación, pero fíjate en que el dominio no sabe CÓMO se notifica
 * (consola, SMTP, SMS...): eso lo decide el adaptador que implemente
 * {@link NotificacionPort}.</p>
 */
public class Alumno {

    private final Long id;
    private final String nombre;
    private final String email;

    public Alumno(Long id, String nombre, String email) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del alumno es obligatorio");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("El email del alumno no es válido");
        }
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}

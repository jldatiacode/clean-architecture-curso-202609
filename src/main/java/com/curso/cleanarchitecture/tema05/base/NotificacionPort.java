package com.curso.cleanarchitecture.tema05.base;

/**
 * Puerto de SALIDA para notificaciones (definido en el tema 4).
 *
 * <p>La aplicación expresa su necesidad ("avisar al alumno de que queda
 * inscrito") sin decidir el canal. En este tema lo implementa
 * {@code NotificacionConsoleAdapter} (un simple {@code System.out}); en
 * producción podría ser un adaptador de email o de SMS. El caso de uso
 * no se entera del cambio.</p>
 */
public interface NotificacionPort {

    void notificarInscripcion(Alumno alumno, Curso curso);
}

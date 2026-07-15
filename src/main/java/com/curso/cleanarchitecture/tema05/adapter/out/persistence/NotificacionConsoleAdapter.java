package com.curso.cleanarchitecture.tema05.adapter.out.persistence;

import com.curso.cleanarchitecture.tema05.base.Alumno;
import com.curso.cleanarchitecture.tema05.base.Curso;
import com.curso.cleanarchitecture.tema05.base.NotificacionPort;

/**
 * Adaptador de SALIDA de notificaciones por consola.
 *
 * <p>Didácticamente este es quizá el adaptador más ilustrativo del tema:
 * el puerto {@code NotificacionPort} dice QUÉ necesita la aplicación
 * ("avisar al alumno"); este adaptador decide CÓMO ("imprimir por consola").
 * Un {@code System.out} basta para desarrollo y demos. El día que haga falta
 * email de verdad, se escribe un {@code NotificacionEmailAdapter} que
 * implemente el mismo puerto y se cambia en la configuración: el caso de uso
 * no se recompila, no se retoca, ni se entera.</p>
 *
 * <p>Esa es la esencia de la inversión de dependencias: el núcleo define el
 * contrato y la infraestructura se adapta a él, nunca al revés.</p>
 */
public class NotificacionConsoleAdapter implements NotificacionPort {

    @Override
    public void notificarInscripcion(Alumno alumno, Curso curso) {
        // Traducción al "lenguaje externo" de este adaptador: texto en consola.
        // Un adaptador de email construiría aquí un MimeMessage; uno de SMS,
        // una llamada a la API del proveedor. El contrato es el mismo.
        System.out.println("[NOTIFICACION] Enviado email a " + alumno.getEmail()
                + ": el alumno " + alumno.getNombre()
                + " ha quedado inscrito en el curso '" + curso.getNombre() + "'.");
    }
}

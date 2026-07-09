package com.curso.cleanarchitecture.tema04.fake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.curso.cleanarchitecture.tema04.application.port.out.NotificacionPort;

/**
 * Adaptador de salida FAKE: notificaciones registradas en memoria.
 *
 * <p>Implementa {@link NotificacionPort} sin enviar nada: simplemente REGISTRA
 * cada notificación solicitada. Esto permite dos cosas:</p>
 * <ul>
 *   <li>Que los tests verifiquen que el caso de uso pidió la notificación
 *       (comportamiento observable sin servidor de correo).</li>
 *   <li>Demostrar la sustitución de implementaciones: el día que exista un
 *       {@code NotificacionEmailAdapter} o un {@code NotificacionKafkaAdapter},
 *       ocupará este mismo hueco sin tocar el caso de uso. Recuerda: el caso
 *       de uso no sabe si la confirmación será email, SMS o evento.</li>
 * </ul>
 */
public class NotificacionFake implements NotificacionPort {

    /**
     * Registro inmutable de una notificación solicitada.
     * Clase anidada estática: solo tiene sentido dentro de este fake.
     */
    public static class NotificacionRegistrada {

        private final Long alumnoId;
        private final Long cursoId;

        public NotificacionRegistrada(Long alumnoId, Long cursoId) {
            this.alumnoId = alumnoId;
            this.cursoId = cursoId;
        }

        public Long getAlumnoId() {
            return alumnoId;
        }

        public Long getCursoId() {
            return cursoId;
        }
    }

    /** Historial de notificaciones pedidas por los casos de uso. */
    private final List<NotificacionRegistrada> enviadas = new ArrayList<>();

    @Override
    public void enviarConfirmacionInscripcion(Long alumnoId, Long cursoId) {
        // Aquí un adaptador real conectaría con SMTP, Twilio, Kafka...
        // El fake se limita a dejar constancia para que el test lo compruebe.
        enviadas.add(new NotificacionRegistrada(alumnoId, cursoId));
    }

    /** Vista de solo lectura del historial, para las aserciones de los tests. */
    public List<NotificacionRegistrada> getEnviadas() {
        return Collections.unmodifiableList(enviadas);
    }
}

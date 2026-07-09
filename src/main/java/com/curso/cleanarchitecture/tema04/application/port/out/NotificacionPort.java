package com.curso.cleanarchitecture.tema04.application.port.out;

/**
 * Puerto de salida: notificaciones al exterior.
 *
 * <p>Contrato de la sección 10 de la teoría. Este puerto demuestra que los
 * puertos de salida NO son solo repositorios: cualquier necesidad de la
 * aplicación hacia fuera (email, SMS, eventos Kafka, push...) se modela igual.</p>
 *
 * <p>Punto docente clave: el caso de uso que invoque este puerto NO SABE si la
 * confirmación se enviará por email, por SMS o publicando un evento. Solo sabe
 * que "hay que confirmar la inscripción". La tecnología concreta la decidirá
 * el adaptador que implemente esta interfaz en infraestructura.</p>
 */
public interface NotificacionPort {

    /**
     * Notifica al alumno que su inscripción en el curso se ha confirmado.
     *
     * @param alumnoId identificador del alumno a notificar
     * @param cursoId  identificador del curso en el que se ha inscrito
     */
    void enviarConfirmacionInscripcion(Long alumnoId, Long cursoId);
}

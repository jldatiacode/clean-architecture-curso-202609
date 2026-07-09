package com.curso.cleanarchitecture.tema03.fake;

import java.util.ArrayList;
import java.util.List;

import com.curso.cleanarchitecture.tema03.application.port.out.InscripcionRepositoryPort;
import com.curso.cleanarchitecture.tema03.model.EstadoInscripcion;
import com.curso.cleanarchitecture.tema03.model.Inscripcion;

/**
 * Fake en memoria de {@link InscripcionRepositoryPort}
 * (reproducido de la sección 10 de la teoría, con una mejora).
 *
 * <p><b>MEJORA respecto a la teoría:</b> al guardar una inscripción nueva
 * (id null), este fake le asigna un id incremental, simulando la columna
 * autoincremental de una base de datos. En el código de la sección 10 la
 * inscripción se guardaba sin id y, por tanto, la
 * {@code InscripcionResponse} devolvía {@code inscripcionId = null}.
 * Con esta mejora la response ya viaja con un id real, que es lo que
 * ocurriría en producción con un adaptador de persistencia de verdad.</p>
 */
public class InscripcionRepositoryEnMemoria implements InscripcionRepositoryPort {

    /** "Tabla" en memoria de inscripciones. */
    private final List<Inscripcion> inscripciones = new ArrayList<>();

    /** Secuencia para simular el id autoincremental de una base de datos. */
    private long secuenciaId = 1L;

    @Override
    public boolean existeInscripcionActiva(Long cursoId, Long alumnoId) {
        // Recorremos las inscripciones buscando una del mismo curso y alumno
        // que siga ACTIVA. Las canceladas no bloquean una nueva inscripción.
        return inscripciones.stream().anyMatch(i ->
                i.getCursoId().equals(cursoId)
                && i.getAlumnoId().equals(alumnoId)
                && i.getEstado() == EstadoInscripcion.ACTIVA);
    }

    @Override
    public void guardar(Inscripcion inscripcion) {
        // MEJORA: si la inscripción llega sin id (recién creada por el caso
        // de uso), le asignamos uno incremental, como haría la base de datos.
        if (inscripcion.getId() == null) {
            inscripcion.asignarId(secuenciaId++);
        }
        inscripciones.add(inscripcion);
    }
}

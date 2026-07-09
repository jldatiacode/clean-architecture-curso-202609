package com.curso.cleanarchitecture.tema04.fake;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.application.port.out.InscripcionRepositoryPort;
import com.curso.cleanarchitecture.tema04.model.Inscripcion;

/**
 * Adaptador de salida FAKE: inscripciones en memoria.
 *
 * <p>Además de almacenar, asume la responsabilidad típica de la persistencia:
 * ASIGNAR EL IDENTIFICADOR a las inscripciones nuevas (aquí con un contador;
 * en base de datos sería una secuencia o un autoincremental). El caso de uso
 * no sabe nada de esto: entrega una inscripción sin id y el adaptador hace
 * el resto.</p>
 */
public class InscripcionRepositoryFake implements InscripcionRepositoryPort {

    /** Almacén en memoria: id de la inscripción → inscripción. */
    private final Map<Long, Inscripcion> inscripciones = new HashMap<>();

    /** Simula la secuencia de base de datos que genera identificadores. */
    private long siguienteId = 1L;

    @Override
    public Optional<Inscripcion> buscarPorId(Long inscripcionId) {
        return Optional.ofNullable(inscripciones.get(inscripcionId));
    }

    @Override
    public boolean existeInscripcionActiva(Long cursoId, Long alumnoId) {
        // La semántica de negocio ("activa para esa pareja curso-alumno")
        // se resuelve aquí recorriendo el almacén. En JPA sería una query;
        // el resultado contractual es el mismo booleano.
        for (Inscripcion inscripcion : inscripciones.values()) {
            boolean mismaPareja = inscripcion.getCursoId().equals(cursoId)
                    && inscripcion.getAlumnoId().equals(alumnoId);
            if (mismaPareja && inscripcion.estaActiva()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Inscripcion> buscarPorAlumnoId(Long alumnoId) {
        List<Inscripcion> resultado = new ArrayList<>();
        for (Inscripcion inscripcion : inscripciones.values()) {
            if (inscripcion.getAlumnoId().equals(alumnoId)) {
                resultado.add(inscripcion);
            }
        }
        return resultado;
    }

    @Override
    public void guardar(Inscripcion inscripcion) {
        // Responsabilidad de la persistencia: generar identidad si es nueva.
        if (inscripcion.getId() == null) {
            inscripcion.asignarId(siguienteId++);
        }
        inscripciones.put(inscripcion.getId(), inscripcion);
    }
}

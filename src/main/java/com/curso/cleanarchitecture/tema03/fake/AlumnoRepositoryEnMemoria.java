package com.curso.cleanarchitecture.tema03.fake;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema03.application.port.out.AlumnoRepositoryPort;
import com.curso.cleanarchitecture.tema03.model.Alumno;

/**
 * Fake en memoria de {@link AlumnoRepositoryPort}.
 *
 * <p>La teoría (sección 10) muestra el fake de cursos y el de inscripciones;
 * este sigue exactamente el mismo patrón para completar el trío de puertos
 * que necesita {@code InscribirAlumnoUseCase}.</p>
 */
public class AlumnoRepositoryEnMemoria implements AlumnoRepositoryPort {

    /** "Tabla" en memoria: id del alumno -> alumno. */
    private final Map<Long, Alumno> alumnos = new HashMap<>();

    @Override
    public Optional<Alumno> buscarPorId(Long alumnoId) {
        return Optional.ofNullable(alumnos.get(alumnoId));
    }

    @Override
    public void guardar(Alumno alumno) {
        alumnos.put(alumno.getId(), alumno);
    }
}

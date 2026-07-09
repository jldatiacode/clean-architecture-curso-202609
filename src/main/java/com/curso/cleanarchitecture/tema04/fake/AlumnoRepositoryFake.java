package com.curso.cleanarchitecture.tema04.fake;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema04.application.port.out.AlumnoRepositoryPort;
import com.curso.cleanarchitecture.tema04.model.Alumno;

/**
 * Adaptador de salida FAKE: alumnos en memoria.
 *
 * <p>Implementa {@link AlumnoRepositoryPort}. Cumple exactamente el mismo
 * contrato que cumplirá el adaptador JPA del Tema 5: por eso los casos de uso
 * y sus tests funcionan igual con uno que con otro.</p>
 */
public class AlumnoRepositoryFake implements AlumnoRepositoryPort {

    /** Almacén en memoria: id del alumno → alumno. */
    private final Map<Long, Alumno> alumnos = new HashMap<>();

    @Override
    public Optional<Alumno> buscarPorId(Long alumnoId) {
        return Optional.ofNullable(alumnos.get(alumnoId));
    }

    @Override
    public Optional<Alumno> buscarPorEmail(String email) {
        // Búsqueda lineal: perfectamente válida para un fake. La eficiencia
        // es problema del adaptador real (un índice en base de datos, por
        // ejemplo); el CONTRATO no dice nada de cómo buscar, solo de qué
        // significa la búsqueda.
        for (Alumno alumno : alumnos.values()) {
            if (alumno.getEmail().equalsIgnoreCase(email)) {
                return Optional.of(alumno);
            }
        }
        return Optional.empty();
    }

    @Override
    public void guardar(Alumno alumno) {
        alumnos.put(alumno.getId(), alumno);
    }
}

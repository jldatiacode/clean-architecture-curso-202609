package com.curso.cleanarchitecture.tema05.adapter.out.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.curso.cleanarchitecture.tema05.base.Alumno;
import com.curso.cleanarchitecture.tema05.base.AlumnoRepositoryPort;

/**
 * Adaptador de SALIDA en memoria para alumnos.
 *
 * <p>Misma idea que {@link CursoRepositoryInMemoryAdapter}: un mapa que cumple
 * el contrato de {@link AlumnoRepositoryPort}. Nótese que cada puerto tiene su
 * adaptador; no hay un "repositorio genérico" que lo sepa todo. Contratos
 * pequeños → adaptadores pequeños → fáciles de sustituir y de probar.</p>
 */
public class AlumnoRepositoryInMemoryAdapter implements AlumnoRepositoryPort {

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

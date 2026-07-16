package com.curso.cleanarchitecture.tema06.memory;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema06.base.Alumno;
import com.curso.cleanarchitecture.tema06.base.AlumnoRepositoryPort;

@Repository
@Profile("memory")
public class AlumnoRepositoryInMemoryAdapter
        implements AlumnoRepositoryPort {

    private final Map<Long, Alumno> alumnos =
            new ConcurrentHashMap<>();

    public AlumnoRepositoryInMemoryAdapter() {
        // Datos iniciales para realizar las pruebas
        alumnos.put(
                1L,
                new Alumno(
                        1L,
                        "Ana García",
                        "ana@email.com"
                )
        );

        alumnos.put(
                2L,
                new Alumno(
                        2L,
                        "Luis Pérez",
                        "luis@email.com"
                )
        );
    }

    @Override
    public Optional<Alumno> buscarPorId(Long alumnoId) {
        return Optional.ofNullable(
                alumnos.get(alumnoId)
        );
    }
}
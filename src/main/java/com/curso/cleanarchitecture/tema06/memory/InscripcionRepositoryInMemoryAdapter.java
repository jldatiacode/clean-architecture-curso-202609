package com.curso.cleanarchitecture.tema06.memory;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema06.base.EstadoInscripcion;
import com.curso.cleanarchitecture.tema06.base.Inscripcion;
import com.curso.cleanarchitecture.tema06.base.InscripcionRepositoryPort;

@Repository
@Profile("memory")
public class InscripcionRepositoryInMemoryAdapter
        implements InscripcionRepositoryPort {

    private final Map<Long, Inscripcion> inscripciones =
            new ConcurrentHashMap<>();

    private final AtomicLong secuenciaIds =
            new AtomicLong(1);

    @Override
    public Optional<Inscripcion> buscarPorId(
            Long inscripcionId) {

        return Optional.ofNullable(
                inscripciones.get(inscripcionId)
        );
    }

    @Override
    public boolean existeInscripcionActiva(
            Long cursoId,
            Long alumnoId) {

        return inscripciones.values()
                .stream()
                .anyMatch(inscripcion ->
                        inscripcion.getCursoId().equals(cursoId)
                        && inscripcion.getAlumnoId().equals(alumnoId)
                        && inscripcion.getEstado()
                            == EstadoInscripcion.ACTIVA
                );
    }

    @Override
    public List<Inscripcion> buscarPorAlumnoId(
            Long alumnoId) {

        return inscripciones.values()
                .stream()
                .filter(inscripcion ->
                        inscripcion.getAlumnoId().equals(alumnoId)
                )
                .toList();
    }

    @Override
    public void guardar(Inscripcion inscripcion) {

        if (inscripcion == null) {
            throw new IllegalArgumentException(
                    "La inscripción no puede ser nula"
            );
        }

        if (inscripcion.getId() == null) {
            guardarNuevaInscripcion(inscripcion);
        } else {
            inscripciones.put(
                    inscripcion.getId(),
                    inscripcion
            );

            actualizarSecuencia(inscripcion.getId());
        }
    }

    private void guardarNuevaInscripcion(
            Inscripcion inscripcion) {

        Long nuevoId = secuenciaIds.getAndIncrement();

        Inscripcion inscripcionGuardada =
                new Inscripcion(
                        nuevoId,
                        inscripcion.getCursoId(),
                        inscripcion.getAlumnoId()
                );

        if (inscripcion.getEstado()
                == EstadoInscripcion.CANCELADA) {

            inscripcionGuardada.cancelar();
        }

        inscripciones.put(
                nuevoId,
                inscripcionGuardada
        );
    }

    private void actualizarSecuencia(Long idExistente) {
        secuenciaIds.updateAndGet(
                valorActual ->
                        Math.max(
                                valorActual,
                                idExistente + 1
                        )
        );
    }
}
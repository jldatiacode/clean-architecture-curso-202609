package com.curso.cleanarchitecture.tema04.application.usecase;

import java.util.ArrayList;
import java.util.List;

import com.curso.cleanarchitecture.tema04.application.port.in.ConsultarCursosDisponiblesInputPort;
import com.curso.cleanarchitecture.tema04.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.response.CursoResponse;
import com.curso.cleanarchitecture.tema04.model.Curso;

/**
 * Caso de uso: consultar los cursos disponibles para inscripción.
 *
 * <p>Implementa {@link ConsultarCursosDisponiblesInputPort} y delega la
 * búsqueda en {@link CursoRepositoryPort}. Es deliberadamente pequeño:
 * sirve para ver que incluso una consulta simple pasa por los mismos
 * contratos que un caso de uso complejo.</p>
 *
 * <p>Su única responsabilidad añadida es TRADUCIR: convierte las entidades de
 * dominio {@link Curso} que le entrega el puerto de salida en
 * {@link CursoResponse} para el exterior. Así el dominio nunca cruza el
 * puerto de entrada hacia los adaptadores.</p>
 */
public class ConsultarCursosDisponiblesUseCase implements ConsultarCursosDisponiblesInputPort {

    private final CursoRepositoryPort cursoRepository;

    public ConsultarCursosDisponiblesUseCase(CursoRepositoryPort cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public List<CursoResponse> ejecutar() {

        // El criterio de "disponible" (abierto y con plazas) forma parte del
        // contrato del puerto de salida: buscarCursosDisponibles(). El caso de
        // uso confía en ese contrato, sea cual sea la implementación.
        List<Curso> disponibles = cursoRepository.buscarCursosDisponibles();

        // Mapeo dominio → response. Sin streams rebuscados: un bucle claro
        // que cualquier alumno pueda leer.
        List<CursoResponse> respuesta = new ArrayList<>();
        for (Curso curso : disponibles) {
            respuesta.add(new CursoResponse(
                    curso.getId(),
                    curso.getNombre(),
                    curso.getPlazasDisponibles(),
                    curso.isCerrado()));
        }
        return respuesta;
    }
}

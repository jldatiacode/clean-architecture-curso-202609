package com.curso.cleanarchitecture.tema04.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.curso.cleanarchitecture.tema04.application.response.CursoResponse;
import com.curso.cleanarchitecture.tema04.fake.CursoRepositoryFake;
import com.curso.cleanarchitecture.tema04.model.Curso;

/**
 * Tests del caso de uso ConsultarCursosDisponiblesUseCase.
 *
 * <p>El caso de uso delega en el puerto de salida {@code CursoRepositoryPort}
 * y traduce dominio → response. Aquí verificamos el contrato completo:
 * solo aparecen cursos abiertos y con plazas, y lo que sale por el puerto de
 * entrada son responses, nunca entidades de dominio.</p>
 */
class ConsultarCursosDisponiblesUseCaseTest {

    private CursoRepositoryFake cursoRepository;
    private ConsultarCursosDisponiblesUseCase useCase;

    @BeforeEach
    void setUp() {
        cursoRepository = new CursoRepositoryFake();
        useCase = new ConsultarCursosDisponiblesUseCase(cursoRepository);
    }

    @Test
    @DisplayName("Filtra los cursos cerrados y los que no tienen plazas")
    void filtraCerradosYSinPlazas() {
        // Given: cuatro cursos con situaciones distintas
        cursoRepository.guardar(new Curso(1L, "Clean Architecture con Java", 10, false)); // disponible
        cursoRepository.guardar(new Curso(2L, "Big Data aplicado", 0, false));            // sin plazas
        cursoRepository.guardar(new Curso(3L, "IA generativa", 15, true));                // cerrado
        cursoRepository.guardar(new Curso(4L, "Ingeniería de Datos", 3, false));          // disponible

        // When
        List<CursoResponse> disponibles = useCase.ejecutar();

        // Then: solo los cursos 1 y 4 pasan el filtro del contrato
        // "buscarCursosDisponibles" (abiertos Y con plazas libres)
        assertThat(disponibles)
                .hasSize(2)
                .extracting(CursoResponse::getId)
                .containsExactlyInAnyOrder(1L, 4L);

        // Y todos los responses reflejan cursos realmente inscribibles
        assertThat(disponibles)
                .allSatisfy(curso -> {
                    assertThat(curso.isCerrado()).isFalse();
                    assertThat(curso.getPlazasDisponibles()).isPositive();
                });
    }

    @Test
    @DisplayName("Sin cursos disponibles devuelve lista vacía, nunca null")
    void sinCursosDevuelveListaVacia() {
        // Given: solo cursos no inscribibles
        cursoRepository.guardar(new Curso(2L, "Big Data aplicado", 0, false));
        cursoRepository.guardar(new Curso(3L, "IA generativa", 15, true));

        // When / Then: el contrato promete "lista posiblemente vacía",
        // lo que evita null-checks en los adaptadores de entrada.
        assertThat(useCase.ejecutar()).isEmpty();
    }
}

package com.curso.cleanarchitecture.tema04.application.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.curso.cleanarchitecture.tema04.application.command.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema04.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema04.application.response.InscripcionResponse;
import com.curso.cleanarchitecture.tema04.fake.AlumnoRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.CursoRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.InscripcionRepositoryFake;
import com.curso.cleanarchitecture.tema04.fake.NotificacionFake;
import com.curso.cleanarchitecture.tema04.model.Alumno;
import com.curso.cleanarchitecture.tema04.model.Curso;
import com.curso.cleanarchitecture.tema04.model.EstadoInscripcion;

/**
 * Tests del caso de uso InscribirAlumnoUseCase.
 *
 * <p>Fíjate en lo que NO hay aquí: ni Spring, ni base de datos, ni servidor de
 * correo. El caso de uso depende solo de PUERTOS (interfaces), así que basta
 * con entregarle implementaciones fake en memoria. Esto es posible únicamente
 * porque los contratos los define la capa de aplicación y cualquier
 * implementación que los cumpla es intercambiable.</p>
 */
class InscribirAlumnoUseCaseTest {

    private CursoRepositoryFake cursoRepository;
    private AlumnoRepositoryFake alumnoRepository;
    private InscripcionRepositoryFake inscripcionRepository;
    private NotificacionFake notificacionFake;
    private InscribirAlumnoUseCase useCase;

    @BeforeEach
    void setUp() {
        // Montaje manual del caso de uso: inyección de dependencias por
        // constructor, sin ningún framework. Cada fake implementa un puerto
        // de salida definido por la aplicación.
        cursoRepository = new CursoRepositoryFake();
        alumnoRepository = new AlumnoRepositoryFake();
        inscripcionRepository = new InscripcionRepositoryFake();
        notificacionFake = new NotificacionFake();
        useCase = new InscribirAlumnoUseCase(
                cursoRepository, alumnoRepository, inscripcionRepository, notificacionFake);
    }

    @Test
    @DisplayName("Caso feliz: inscribe al alumno, ocupa plaza y registra la notificación")
    void inscribeAlumnoYNotifica() {
        // Given: un curso abierto con plazas y un alumno registrado
        cursoRepository.guardar(new Curso(10L, "Clean Architecture con Java", 20, false));
        alumnoRepository.guardar(new Alumno(7L, "Ana García", "ana.garcia@example.com"));

        // When: ejecutamos la acción a través del contrato (el command es la entrada)
        InscripcionResponse response = useCase.ejecutar(new InscribirAlumnoCommand(10L, 7L));

        // Then: la respuesta refleja la inscripción activa con id asignado por la persistencia
        assertThat(response.getInscripcionId()).isNotNull();
        assertThat(response.getCursoId()).isEqualTo(10L);
        assertThat(response.getAlumnoId()).isEqualTo(7L);
        assertThat(response.getEstado()).isEqualTo(EstadoInscripcion.ACTIVA.name());

        // Y el curso ha perdido una plaza (regla de dominio aplicada y persistida)
        assertThat(cursoRepository.buscarPorId(10L).orElseThrow().getPlazasDisponibles())
                .isEqualTo(19);

        // Y el NotificacionFake registró la notificación: el caso de uso pidió
        // "confirma la inscripción" sin saber el canal (email, SMS o evento).
        assertThat(notificacionFake.getEnviadas()).hasSize(1);
        assertThat(notificacionFake.getEnviadas().get(0).getAlumnoId()).isEqualTo(7L);
        assertThat(notificacionFake.getEnviadas().get(0).getCursoId()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Curso sin plazas: la entidad protege la invariante y no se notifica nada")
    void cursoSinPlazasLanzaExcepcion() {
        // Given: curso abierto pero sin plazas libres
        cursoRepository.guardar(new Curso(10L, "Big Data aplicado", 0, false));
        alumnoRepository.guardar(new Alumno(7L, "Ana García", "ana.garcia@example.com"));

        // When / Then: la excepción la lanza la ENTIDAD Curso (ocuparPlaza),
        // no el caso de uso. El dominio se protege a sí mismo.
        assertThatIllegalStateException()
                .isThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(10L, 7L)))
                .withMessageContaining("no tiene plazas disponibles");

        // Y no se registró ninguna notificación: el flujo se cortó antes.
        assertThat(notificacionFake.getEnviadas()).isEmpty();
    }

    @Test
    @DisplayName("Inscripción duplicada: no se permite una segunda inscripción activa")
    void inscripcionDuplicadaLanzaExcepcion() {
        // Given: alumno ya inscrito en el curso
        cursoRepository.guardar(new Curso(10L, "Clean Architecture con Java", 20, false));
        alumnoRepository.guardar(new Alumno(7L, "Ana García", "ana.garcia@example.com"));
        useCase.ejecutar(new InscribirAlumnoCommand(10L, 7L));

        // When / Then: la segunda inscripción viola la regla de aplicación
        assertThatIllegalStateException()
                .isThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(10L, 7L)))
                .withMessageContaining("ya tiene una inscripción activa");
    }

    /**
     * DEMOSTRACIÓN DE LA INVERSIÓN DE DEPENDENCIAS (sección 9 de la teoría).
     *
     * <p>Sustituimos el {@code CursoRepositoryFake} por OTRA implementación
     * completamente distinta ({@link CursoRepositoryPrecargado}, definida más
     * abajo) y el caso de uso funciona igual SIN CAMBIAR NI UNA LÍNEA de
     * {@code InscribirAlumnoUseCase}.</p>
     *
     * <p>¿Por qué es posible? Porque el caso de uso depende de la interfaz
     * {@code CursoRepositoryPort}, que define la propia aplicación, y no de
     * ninguna clase concreta. Hoy intercambiamos dos fakes; en el Tema 5
     * intercambiaremos un fake por un adaptador JPA con la misma facilidad.
     * Esa es exactamente la promesa de la inversión de dependencias.</p>
     */
    @Test
    @DisplayName("Inversión de dependencias: otra implementación del puerto sin tocar el caso de uso")
    void sustituirImplementacionDelPuertoSinCambiarElCasoDeUso() {
        // Given: una implementación ALTERNATIVA del puerto de salida de cursos
        CursoRepositoryPort repositorioAlternativo = new CursoRepositoryPrecargado(
                new Curso(99L, "Ingeniería de Datos", 5, false));
        alumnoRepository.guardar(new Alumno(7L, "Ana García", "ana.garcia@example.com"));

        // Construimos EL MISMO caso de uso, cambiando solo la implementación
        // inyectada. La clase InscribirAlumnoUseCase no se ha modificado.
        InscribirAlumnoUseCase mismoUseCaseOtraInfraestructura = new InscribirAlumnoUseCase(
                repositorioAlternativo, alumnoRepository, inscripcionRepository, notificacionFake);

        // When
        InscripcionResponse response =
                mismoUseCaseOtraInfraestructura.ejecutar(new InscribirAlumnoCommand(99L, 7L));

        // Then: el comportamiento es idéntico con la nueva implementación
        assertThat(response.getCursoId()).isEqualTo(99L);
        assertThat(response.getEstado()).isEqualTo(EstadoInscripcion.ACTIVA.name());
        assertThat(notificacionFake.getEnviadas()).hasSize(1);
    }

    /**
     * Implementación alternativa del puerto de salida, distinta del fake
     * "oficial": se construye precargada con cursos y no admite altas nuevas
     * (simula, por ejemplo, un catálogo de cursos de solo lectura servido por
     * un sistema externo). Cumple el mismo contrato, así que encaja en el
     * mismo hueco del caso de uso.
     */
    private static class CursoRepositoryPrecargado implements CursoRepositoryPort {

        private final Map<Long, Curso> catalogo = new HashMap<>();

        CursoRepositoryPrecargado(Curso... cursosIniciales) {
            for (Curso curso : cursosIniciales) {
                catalogo.put(curso.getId(), curso);
            }
        }

        @Override
        public Optional<Curso> buscarPorId(Long cursoId) {
            return Optional.ofNullable(catalogo.get(cursoId));
        }

        @Override
        public List<Curso> buscarCursosDisponibles() {
            return catalogo.values().stream()
                    .filter(Curso::tienePlazasLibres)
                    .toList();
        }

        @Override
        public void guardar(Curso curso) {
            // Este adaptador solo actualiza cursos ya existentes en su catálogo.
            // Es una implementación distinta con el mismo contrato: al caso de
            // uso le da igual, porque solo conoce la interfaz.
            catalogo.put(curso.getId(), curso);
        }
    }
}

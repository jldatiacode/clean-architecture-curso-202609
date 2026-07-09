package com.curso.cleanarchitecture.tema03;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.curso.cleanarchitecture.tema03.application.command.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema03.application.response.InscripcionResponse;
import com.curso.cleanarchitecture.tema03.application.usecase.InscribirAlumnoUseCase;
import com.curso.cleanarchitecture.tema03.fake.AlumnoRepositoryEnMemoria;
import com.curso.cleanarchitecture.tema03.fake.CursoRepositoryEnMemoria;
import com.curso.cleanarchitecture.tema03.fake.InscripcionRepositoryEnMemoria;
import com.curso.cleanarchitecture.tema03.model.Alumno;
import com.curso.cleanarchitecture.tema03.model.Curso;

/**
 * Test unitario del caso de uso con repositorios FAKE en memoria
 * (sección 11 de la teoría).
 *
 * <p>Fíjate en lo que NO hay aquí: ni {@code @SpringBootTest}, ni contexto de
 * Spring, ni base de datos, ni servidor web. Solo Java, JUnit y los fakes.
 * Esa es la gran ventaja de un caso de uso bien aislado: se prueba en
 * milisegundos.</p>
 */
class InscribirAlumnoUseCaseTest {

    // Los fakes se comparten entre los tests de la clase; JUnit crea una
    // instancia nueva de la clase de test para cada @Test, así que cada
    // prueba parte de repositorios vacíos.
    private CursoRepositoryEnMemoria cursoRepository;
    private AlumnoRepositoryEnMemoria alumnoRepository;
    private InscripcionRepositoryEnMemoria inscripcionRepository;
    private InscribirAlumnoUseCase useCase;

    @BeforeEach
    void setUp() {
        cursoRepository = new CursoRepositoryEnMemoria();
        alumnoRepository = new AlumnoRepositoryEnMemoria();
        inscripcionRepository = new InscripcionRepositoryEnMemoria();
        useCase = new InscribirAlumnoUseCase(
                cursoRepository, alumnoRepository, inscripcionRepository);
    }

    /**
     * CASO FELIZ — reproduce fielmente el test de la sección 11 de la teoría
     * (por eso usa assertEquals de JUnit, como en el material).
     */
    @Test
    @DisplayName("Debería inscribir al alumno cuando hay plazas disponibles")
    void deberiaInscribirAlumnoCuandoHayPlazasDisponibles() {
        // Preparación: un curso abierto con 2 plazas y un alumno registrado.
        Curso curso = new Curso(1L, "Clean Architecture con Java", 2, false);
        Alumno alumno = new Alumno(1L, "Ana", "ana@email.com");

        cursoRepository.guardar(curso);
        alumnoRepository.guardar(alumno);

        // Ejecución del caso de uso: Java puro, sin framework.
        InscripcionResponse response = useCase.ejecutar(new InscribirAlumnoCommand(1L, 1L));

        // Verificación de la response (como en la teoría)...
        assertEquals(1L, response.getCursoId());
        assertEquals(1L, response.getAlumnoId());
        assertEquals("ACTIVA", response.getEstado());
        // ...y del efecto sobre el dominio: de 2 plazas queda 1.
        assertEquals(1, curso.getPlazasDisponibles());

        // Extra respecto a la teoría: gracias a la mejora del fake
        // (id incremental al guardar), la response trae un id real.
        assertThat(response.getInscripcionId()).isNotNull();
    }

    /**
     * CASO NEGATIVO — curso inexistente.
     * El caso de uso lanza IllegalArgumentException: el problema está en los
     * DATOS de entrada (un id que no corresponde a ningún curso).
     */
    @Test
    @DisplayName("Debería fallar cuando el curso no existe")
    void deberiaFallarCuandoElCursoNoExiste() {
        // Solo registramos al alumno; el curso 99 no existe.
        alumnoRepository.guardar(new Alumno(1L, "Ana", "ana@email.com"));

        assertThatThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(99L, 1L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Curso no encontrado");
    }

    /**
     * CASO NEGATIVO — alumno inexistente. Mismo criterio que con el curso.
     */
    @Test
    @DisplayName("Debería fallar cuando el alumno no existe")
    void deberiaFallarCuandoElAlumnoNoExiste() {
        cursoRepository.guardar(new Curso(1L, "Clean Architecture con Java", 2, false));

        assertThatThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(1L, 99L)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Alumno no encontrado");
    }

    /**
     * CASO NEGATIVO — inscripción duplicada.
     * Esta comprobación es REGLA DE APLICACIÓN (necesita consultar el
     * repositorio), y se traduce en IllegalStateException: el estado del
     * sistema (ya inscrito) impide repetir la operación.
     */
    @Test
    @DisplayName("Debería fallar cuando el alumno ya está inscrito en el curso")
    void deberiaFallarCuandoElAlumnoYaEstaInscrito() {
        Curso curso = new Curso(1L, "Clean Architecture con Java", 2, false);
        cursoRepository.guardar(curso);
        alumnoRepository.guardar(new Alumno(1L, "Ana", "ana@email.com"));

        // Primera inscripción: correcta.
        useCase.ejecutar(new InscribirAlumnoCommand(1L, 1L));

        // Segunda inscripción del mismo alumno en el mismo curso: rechazada.
        assertThatThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(1L, 1L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessage("El alumno ya está inscrito en este curso");

        // El fallo no debe haber consumido una segunda plaza.
        assertThat(curso.getPlazasDisponibles()).isEqualTo(1);
    }

    /**
     * CASO NEGATIVO — curso cerrado.
     * Aquí quien rechaza NO es el caso de uso sino el DOMINIO
     * (regla de negocio en Curso.ocuparPlaza / validarPuedeRecibirInscripcion).
     * El test demuestra que la regla se aplica aunque el flujo llegue hasta ahí.
     */
    @Test
    @DisplayName("Debería fallar cuando el curso está cerrado")
    void deberiaFallarCuandoElCursoEstaCerrado() {
        // Curso con plazas pero CERRADO.
        cursoRepository.guardar(new Curso(1L, "Clean Architecture con Java", 5, true));
        alumnoRepository.guardar(new Alumno(1L, "Ana", "ana@email.com"));

        assertThatThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(1L, 1L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("cerrado");
    }

    /**
     * CASO NEGATIVO — curso sin plazas.
     * Otra regla de negocio del dominio: con 0 plazas, Curso.ocuparPlaza()
     * lanza IllegalStateException y el caso de uso simplemente la propaga.
     */
    @Test
    @DisplayName("Debería fallar cuando el curso no tiene plazas disponibles")
    void deberiaFallarCuandoElCursoNoTienePlazas() {
        // Curso abierto pero con 0 plazas.
        cursoRepository.guardar(new Curso(1L, "Clean Architecture con Java", 0, false));
        alumnoRepository.guardar(new Alumno(1L, "Ana", "ana@email.com"));

        assertThatThrownBy(() -> useCase.ejecutar(new InscribirAlumnoCommand(1L, 1L)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("plazas");
    }
}

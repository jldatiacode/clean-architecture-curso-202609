package com.curso.cleanarchitecture.tema03.application.usecase;

import com.curso.cleanarchitecture.tema03.application.command.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema03.application.port.out.AlumnoRepositoryPort;
import com.curso.cleanarchitecture.tema03.application.port.out.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema03.application.port.out.InscripcionRepositoryPort;
import com.curso.cleanarchitecture.tema03.application.response.InscripcionResponse;
import com.curso.cleanarchitecture.tema03.model.Alumno;
import com.curso.cleanarchitecture.tema03.model.Curso;
import com.curso.cleanarchitecture.tema03.model.Inscripcion;

/**
 * Interactor del caso de uso "Inscribir un alumno en un curso"
 * (reproducido de la sección 9 de la teoría).
 *
 * <p><b>QUÉ RECIBE:</b> un {@link InscribirAlumnoCommand} con los
 * identificadores del curso y del alumno. Nada más: no recibe DTOs HTTP,
 * ni peticiones web, ni JSON.</p>
 *
 * <p><b>QUÉ COORDINA (regla de aplicación, sección 5 de la teoría):</b>
 * la secuencia de pasos necesaria para completar la acción:</p>
 * <ol>
 *   <li>Buscar el curso.</li>
 *   <li>Buscar el alumno.</li>
 *   <li>Comprobar si ya existe una inscripción activa.</li>
 *   <li>Pedir al dominio que ocupe una plaza.</li>
 *   <li>Crear la inscripción.</li>
 *   <li>Guardar los cambios.</li>
 * </ol>
 *
 * <p><b>QUÉ REGLAS DELEGA AL DOMINIO (reglas de negocio):</b>
 * "un curso cerrado no acepta inscripciones" y "un curso sin plazas no acepta
 * más alumnos" viven dentro de {@code Curso.ocuparPlaza()}. Este caso de uso
 * NO comprueba {@code isCerrado()} ni cuenta plazas: solo pide la operación y
 * confía en que la entidad proteja sus invariantes. Así evitamos entidades
 * anémicas (aviso de la sección 5).</p>
 *
 * <p><b>QUÉ DEPENDENCIAS NECESITA:</b> los tres puertos de salida
 * ({@link CursoRepositoryPort}, {@link AlumnoRepositoryPort},
 * {@link InscripcionRepositoryPort}), recibidos por constructor. Son
 * interfaces: en tests se inyectan fakes o mocks; en producción, adaptadores
 * reales de persistencia.</p>
 *
 * <p><b>QUÉ NO DEBE SABER:</b> nada de HTTP, JSON, SQL ni Spring. No conoce
 * {@code ResponseEntity}, ni anotaciones {@code @Service}, ni entidades JPA,
 * ni códigos de estado 404/409. Si mañana la aplicación se invoca desde una
 * cola de mensajes o una CLI en lugar de una API REST, esta clase no cambia
 * ni una línea.</p>
 */
public class InscribirAlumnoUseCase {

    // Dependencias: SOLO puertos (interfaces de la capa de aplicación).
    // El caso de uso depende de abstracciones, nunca de implementaciones.
    private final CursoRepositoryPort cursoRepository;
    private final AlumnoRepositoryPort alumnoRepository;
    private final InscripcionRepositoryPort inscripcionRepository;

    /**
     * Inyección de dependencias por constructor, a mano y sin framework.
     * Quien construye el caso de uso decide qué implementaciones usar.
     */
    public InscribirAlumnoUseCase(
            CursoRepositoryPort cursoRepository,
            AlumnoRepositoryPort alumnoRepository,
            InscripcionRepositoryPort inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * Ejecuta el flujo de inscripción. Cada paso está numerado según la
     * regla de aplicación descrita en la sección 5 de la teoría.
     *
     * @param command datos de entrada (ids de curso y alumno)
     * @return response de aplicación con el resultado de la inscripción
     * @throws IllegalArgumentException si el curso o el alumno no existen
     *                                  (los datos recibidos no son válidos)
     * @throws IllegalStateException    si el alumno ya está inscrito, o si el
     *                                  dominio rechaza la operación (curso
     *                                  cerrado o sin plazas)
     */
    public InscripcionResponse ejecutar(InscribirAlumnoCommand command) {

        // PASO 1 — Buscar el curso.
        // REGLA DE APLICACIÓN: si no existe, el flujo no puede continuar.
        // Usamos IllegalArgumentException porque el problema está en los
        // datos que nos pasaron (un id inexistente), no en el estado del dominio.
        Curso curso = cursoRepository.buscarPorId(command.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        // PASO 2 — Buscar el alumno. Mismo criterio que con el curso.
        Alumno alumno = alumnoRepository.buscarPorId(command.getAlumnoId())
                .orElseThrow(() -> new IllegalArgumentException("Alumno no encontrado"));

        // PASO 3 — Comprobar si ya existe una inscripción activa.
        // Esta comprobación es REGLA DE APLICACIÓN: necesita consultar el
        // repositorio (algo que una entidad no puede hacer por sí sola),
        // por eso se orquesta aquí y no dentro de Inscripcion.
        boolean yaInscrito = inscripcionRepository.existeInscripcionActiva(
                curso.getId(), alumno.getId());

        if (yaInscrito) {
            throw new IllegalStateException("El alumno ya está inscrito en este curso");
        }

        // PASO 4 — Pedir al dominio que ocupe una plaza.
        // REGLA DE NEGOCIO delegada: Curso decide si puede (¿cerrado?,
        // ¿quedan plazas?) y lanza IllegalStateException si no.
        // El caso de uso NO repite esas validaciones.
        curso.ocuparPlaza();

        // PASO 5 — Crear la inscripción.
        // Nace sin id (lo asignará la persistencia) y referencia curso y
        // alumno por IDENTIFICADOR, no por objeto completo: así lo hace la
        // teoría del Tema 3 y así se relacionan agregados independientes.
        Inscripcion inscripcion = new Inscripcion(null, curso.getId(), alumno.getId());

        // PASO 6 — Guardar los cambios: la nueva inscripción y el curso con
        // una plaza menos. (La atomicidad transaccional de estos dos guardados
        // es un detalle de infraestructura que se abordará en temas posteriores.)
        inscripcionRepository.guardar(inscripcion);
        cursoRepository.guardar(curso);

        // Devolver la response de aplicación: datos planos, sin exponer
        // entidades del dominio. El estado se convierte a String aquí,
        // en la frontera de la capa de aplicación.
        return new InscripcionResponse(
                inscripcion.getId(),
                curso.getId(),
                alumno.getId(),
                inscripcion.getEstado().name()
        );
    }
}

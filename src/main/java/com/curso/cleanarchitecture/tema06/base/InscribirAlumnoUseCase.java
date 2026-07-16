package com.curso.cleanarchitecture.tema06.base;

/**
 * Interactor del caso de uso "inscribir alumno" — visto en el tema 3.
 *
 * <p><b>Punto central del tema 6:</b> esta clase sigue siendo <b>Java puro</b>.
 * No tiene {@code @Service}, ni {@code @Component}, ni {@code @Autowired},
 * ni {@code @Transactional}. No importa nada de {@code org.springframework}
 * ni de {@code jakarta.persistence}. Y aun así funcionará dentro de una
 * aplicación Spring Boot con H2, porque:</p>
 * <ul>
 *   <li>Habla con <b>puertos</b> (interfaces), no con JpaRepository.</li>
 *   <li>El bean lo crea desde fuera {@code ApplicationConfigEjemplo} con
 *       {@code new InscribirAlumnoUseCase(...)} (sección 8 de la teoría).</li>
 *   <li>Las implementaciones reales de los puertos (adaptadores JPA del
 *       paquete {@code persistence}) se inyectan por constructor.</li>
 * </ul>
 *
 * <p>Consecuencia práctica: se puede seguir probando con fakes en memoria,
 * exactamente igual que en el tema 3, sin arrancar Spring ni H2.</p>
 */
public class InscribirAlumnoUseCase implements InscribirAlumnoInputPort {

    // Dependencias hacia ABSTRACCIONES (puertos), nunca hacia tecnología.
    // La regla de dependencia se cumple: aplicación -> dominio, y la
    // infraestructura queda fuera, al otro lado de las interfaces.
    private final CursoRepositoryPort cursoRepository;
    private final AlumnoRepositoryPort alumnoRepository;
    private final InscripcionRepositoryPort inscripcionRepository;

    /**
     * Inyección por constructor SIN framework: es un constructor normal.
     * Quien construye (la clase de configuración, un test, un main de
     * consola) decide qué implementaciones pasar.
     */
    public InscribirAlumnoUseCase(CursoRepositoryPort cursoRepository,
                                  AlumnoRepositoryPort alumnoRepository,
                                  InscripcionRepositoryPort inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * Orquesta el caso de uso paso a paso. Nota cómo cada paso habla en
     * términos de negocio; el SQL que esto genera por debajo (visible en la
     * consola gracias a {@code spring.jpa.show-sql=true}) es asunto de la
     * infraestructura.
     */
    @Override
    public InscripcionResponse inscribir(InscribirAlumnoCommand command) {
        // 1. Recuperar el curso a través del puerto (¿de H2? ¿de memoria?
        //    El caso de uso no lo sabe ni le importa).
        Curso curso = cursoRepository.buscarPorId(command.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el curso con id " + command.getCursoId()));

        // 2. Recuperar el alumno.
        Alumno alumno = alumnoRepository.buscarPorId(command.getAlumnoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No existe el alumno con id " + command.getAlumnoId()));

        // 3. REGLA DE APLICACIÓN: no duplicar inscripciones activas.
        //    La pregunta es de negocio; la consulta SQL equivalente vive
        //    en el adaptador JPA.
        if (inscripcionRepository.existeInscripcionActiva(curso.getId(), alumno.getId())) {
            throw new IllegalStateException("El alumno ya está inscrito en este curso");
        }

        // 4. REGLAS DE DOMINIO: el propio curso valida si admite inscripción
        //    (cerrado, sin plazas) y ocupa la plaza.
        curso.ocuparPlaza();

        // 5. Persistir los cambios a través de los puertos. Los ids nuevos
        //    los generará la base de datos (IDENTITY): por eso la inscripción
        //    nueva viaja con id null.
        cursoRepository.guardar(curso);
        inscripcionRepository.guardar(new Inscripcion(null, curso.getId(), alumno.getId()));

        // 6. Devolver un DTO plano, nunca la entidad de dominio en crudo.
        return new InscripcionResponse(
                curso.getId(),
                alumno.getId(),
                curso.getNombre(),
                alumno.getNombre(),
                EstadoInscripcion.ACTIVA.name());
    }
}

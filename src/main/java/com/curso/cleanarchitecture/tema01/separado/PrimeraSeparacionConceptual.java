package com.curso.cleanarchitecture.tema01.separado;

import java.util.Optional;

/**
 * ESBOZO CONCEPTUAL de la primera separación de responsabilidades del Tema 1.
 *
 * <p><b>Qué es este fichero:</b> no es la implementación final de la
 * arquitectura del curso, sino un mapa en código de hacia dónde vamos. Todas
 * las piezas están como clases internas estáticas para poder leerlas juntas y
 * compararlas, en un solo vistazo, con el controlador acoplado de
 * {@code tema01.acoplado}. En los temas siguientes cada pieza pasará a su
 * paquete real ({@code domain}, {@code application}, {@code adapter},
 * {@code infrastructure}, sección 21 de la teoría).</p>
 *
 * <p><b>Comparación de flujos</b> (secciones 8 y 29 de la teoría):</p>
 * <pre>
 * ACOPLADO:  HTTP → InscripcionControllerAcoplado ─┬─ decide reglas de negocio
 *                                                  ├─ consulta y guarda datos
 *                                                  └─ construye errores HTTP
 *
 * LIMPIO:    HTTP → Controller Adapter → Use Case → Dominio → Puerto → Adaptador → BD
 *            (cada flecha cruza una FRONTERA y cada pieza hace UNA cosa)
 * </pre>
 *
 * <p><b>La regla de dependencia en este fichero:</b> obsérvese quién conoce a
 * quién. La entidad {@link Curso} no conoce a nadie. El caso de uso
 * {@link InscribirAlumnoUseCase} conoce la entidad y los PUERTOS (interfaces),
 * nunca una base de datos. El controlador esboza conocer solo el caso de uso.
 * Todas las dependencias apuntan hacia dentro (sección 11).</p>
 *
 * <p><b>Nota docente:</b> este fichero no usa ninguna anotación Spring y nunca
 * se ejecuta como bean; la aplicación arrancable solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}. Es material de lectura.</p>
 */
public final class PrimeraSeparacionConceptual {

    // Clase contenedora puramente didáctica: no se instancia.
    private PrimeraSeparacionConceptual() {
    }

    // ════════════════════════════════════════════════════════════════════════
    // 1. DOMINIO — la regla extraída del controlador (sección 15 de la teoría)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Entidad de dominio {@code Curso} CON comportamiento.
     *
     * <p><b>Capa:</b> dominio. <b>Puede depender de:</b> nada externo, solo
     * Java. <b>No puede depender de:</b> Spring, JPA, HTTP, SQL (Advertencia 3
     * de la teoría).</p>
     *
     * <p>Compárese con el modelo anémico {@code CursoSimple} de
     * {@code tema01.modelo}: aquí las reglas "curso cerrado" y "sin plazas" ya
     * no las decide el controlador; las protege la propia entidad. Cualquier
     * punto de entrada (REST, consola, CSV, Kafka, un test) obtiene las mismas
     * reglas gratis (sección 24).</p>
     */
    public static class Curso {

        private final Long id;
        private final String nombre;
        private int plazasDisponibles;
        private boolean cerrado;

        public Curso(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
            this.id = id;
            this.nombre = nombre;
            this.plazasDisponibles = plazasDisponibles;
            this.cerrado = cerrado;
        }

        /**
         * LA REGLA DE NEGOCIO que en el flujo acoplado vivía como dos "if" en
         * el controlador (bloques 2 y 3 de {@code InscripcionControllerAcoplado}).
         * Aquí está protegida: nadie puede ocupar una plaza sin pasar por ella.
         */
        public void validarPuedeRecibirInscripcion() {
            if (cerrado) {
                // Excepción de negocio, no ResponseEntity: la entidad no sabe
                // (ni debe saber) que existe HTTP. Traducir esto a un 400 será
                // trabajo del adaptador web.
                throw new IllegalStateException("El curso está cerrado");
            }
            if (plazasDisponibles <= 0) {
                throw new IllegalStateException("No hay plazas disponibles");
            }
        }

        /**
         * La operación de negocio que en el flujo acoplado era un
         * {@code setPlazasDisponibles(get... - 1)} hecho a mano. Al ser un
         * método de la entidad, valida SIEMPRE antes de mutar: el estado
         * inválido se vuelve imposible, no solo improbable.
         */
        public void ocuparPlaza() {
            validarPuedeRecibirInscripcion();
            plazasDisponibles--;
        }

        public Long getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }

        public int getPlazasDisponibles() {
            return plazasDisponibles;
        }

        public boolean isCerrado() {
            return cerrado;
        }
        // Nótese: no hay setters. La entidad solo cambia a través de
        // operaciones de negocio con nombre e intención.
    }

    /**
     * Entidad de dominio {@code Alumno}, mínima para el esbozo.
     * <b>Capa:</b> dominio. Java puro, sin setters gratuitos.
     */
    public static class Alumno {

        private final Long id;
        private final String nombre;

        public Alumno(Long id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public Long getId() {
            return id;
        }

        public String getNombre() {
            return nombre;
        }
    }

    /**
     * Entidad de dominio {@code Inscripcion}: la relación curso-alumno como
     * concepto de negocio, construida solo cuando las reglas lo permiten.
     * <b>Capa:</b> dominio.
     */
    public static class Inscripcion {

        private final Curso curso;
        private final Alumno alumno;

        public Inscripcion(Curso curso, Alumno alumno) {
            this.curso = curso;
            this.alumno = alumno;
        }

        public Curso getCurso() {
            return curso;
        }

        public Alumno getAlumno() {
            return alumno;
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 2. PUERTOS DE SALIDA — lo que la aplicación NECESITA, sin decir el CÓMO
    //    (secciones 11 y 12: regla de dependencia e inversión de dependencias)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Puerto de salida para cursos. <b>Capa:</b> aplicación (la interfaz la
     * define quien la necesita, no quien la implementa).
     *
     * <p>Esta interfaz no dice si los cursos están en MySQL, PostgreSQL, un
     * fichero o memoria: solo declara la necesidad. La infraestructura la
     * implementará por fuera ({@code CursoRepositoryJpaAdapter},
     * {@code CursoRepositoryEnMemoria}...), y así la flecha de dependencia
     * queda invertida: adaptador → puerto, nunca al revés.</p>
     */
    public interface CursoRepositoryPort {

        Optional<Curso> buscarPorId(Long cursoId);

        void guardar(Curso curso);
    }

    /** Puerto de salida para alumnos. Misma idea que {@link CursoRepositoryPort}. */
    public interface AlumnoRepositoryPort {

        Optional<Alumno> buscarPorId(Long alumnoId);
    }

    /** Puerto de salida para inscripciones. */
    public interface InscripcionRepositoryPort {

        boolean existeInscripcion(Long cursoId, Long alumnoId);

        void guardar(Inscripcion inscripcion);
    }

    // ════════════════════════════════════════════════════════════════════════
    // 3. APLICACIÓN — el caso de uso que coordina (sección 16 de la teoría)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Caso de uso "inscribir alumno en curso".
     *
     * <p><b>Capa:</b> aplicación. <b>Puede depender de:</b> el dominio y los
     * puertos. <b>No puede depender de:</b> Spring, HTTP, JSON, SQL ni ninguna
     * implementación concreta de persistencia.</p>
     *
     * <p>Compárese con el método de 40 líneas del controlador acoplado: aquí el
     * caso de uso COORDINA (busca, pregunta, ordena guardar) pero la regla
     * importante vive en {@link Curso#ocuparPlaza()}. Y como no hay ni rastro
     * de framework, este flujo se prueba con JUnit y tres implementaciones en
     * memoria, sin servidor web ni base de datos (sección 26).</p>
     */
    public static class InscribirAlumnoUseCase {

        // Dependencias declaradas como PUERTOS: el caso de uso no sabe ni le
        // importa qué tecnología habrá detrás. Esto es la inversión de
        // dependencias en la práctica.
        private final CursoRepositoryPort cursoRepository;
        private final AlumnoRepositoryPort alumnoRepository;
        private final InscripcionRepositoryPort inscripcionRepository;

        public InscribirAlumnoUseCase(
                CursoRepositoryPort cursoRepository,
                AlumnoRepositoryPort alumnoRepository,
                InscripcionRepositoryPort inscripcionRepository) {
            this.cursoRepository = cursoRepository;
            this.alumnoRepository = alumnoRepository;
            this.inscripcionRepository = inscripcionRepository;
        }

        /**
         * El mismo flujo funcional que el controlador acoplado, pero con cada
         * responsabilidad en su sitio. Nótese que aquí no hay ResponseEntity,
         * ni códigos 400, ni JSON: los errores son excepciones de negocio que
         * el adaptador web traducirá a HTTP en su frontera.
         */
        public void ejecutar(Long cursoId, Long alumnoId) {

            // Coordinación: cargar los actores a través de los puertos.
            Curso curso = cursoRepository.buscarPorId(cursoId)
                    .orElseThrow(() -> new IllegalStateException("Curso no encontrado"));

            Alumno alumno = alumnoRepository.buscarPorId(alumnoId)
                    .orElseThrow(() -> new IllegalStateException("Alumno no encontrado"));

            // Regla "no inscribirse dos veces": el caso de uso pregunta el dato
            // al puerto y toma la decisión aquí, en la capa de aplicación, no
            // en la web. (En temas posteriores discutiremos si puede empujarse
            // aún más hacia el dominio.)
            boolean yaInscrito = inscripcionRepository.existeInscripcion(cursoId, alumnoId);
            if (yaInscrito) {
                throw new IllegalStateException("El alumno ya está inscrito en el curso");
            }

            // Las reglas "curso cerrado" y "sin plazas" ya NO se comprueban con
            // ifs sueltos: las aplica la propia entidad al ocupar la plaza.
            curso.ocuparPlaza();

            Inscripcion inscripcion = new Inscripcion(curso, alumno);

            // Coordinación final: ordenar la persistencia a través de puertos.
            inscripcionRepository.guardar(inscripcion);
            cursoRepository.guardar(curso);
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // 4. ADAPTADOR DE ENTRADA — esbozo del controlador FINO (sección 17)
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Esbozo del controlador limpio. <b>Capa:</b> adaptador de entrada.
     *
     * <p>En la versión real llevaría {@code @RestController} y
     * {@code @RequestMapping("/tema01/cursos")}, con el mismo endpoint
     * {@code POST /{cursoId}/inscripciones/{alumnoId}} que la versión acoplada.
     * Aquí se deja sin anotaciones a propósito: lo importante del esbozo es ver
     * que su ÚNICA dependencia es el caso de uso, y su único trabajo, traducir
     * HTTP → aplicación y negocio → HTTP.</p>
     *
     * <p>Compárese: el controlador acoplado necesitaba tres repositorios y
     * ~40 líneas; este necesita una dependencia y una línea de lógica.</p>
     */
    public static class InscripcionControllerEsbozo {

        // Única dependencia: el caso de uso. Nada de repositorios, nada de SQL
        // simulado, nada de reglas de negocio.
        private final InscribirAlumnoUseCase inscribirAlumnoUseCase;

        public InscripcionControllerEsbozo(InscribirAlumnoUseCase inscribirAlumnoUseCase) {
            this.inscribirAlumnoUseCase = inscribirAlumnoUseCase;
        }

        /**
         * Todo el trabajo del adaptador: delegar. En la versión real, un
         * manejador de excepciones traduciría las IllegalStateException de
         * negocio a respuestas 400/404, manteniendo la traducción de errores
         * también en la frontera web, que es donde pertenece.
         */
        public String inscribir(Long cursoId, Long alumnoId) {
            inscribirAlumnoUseCase.ejecutar(cursoId, alumnoId);
            return "Alumno inscrito correctamente";
        }
    }
}

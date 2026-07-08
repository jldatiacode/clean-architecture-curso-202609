package com.curso.cleanarchitecture.tema01.acoplado;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.cleanarchitecture.tema01.modelo.AlumnoSimple;
import com.curso.cleanarchitecture.tema01.modelo.CursoSimple;
import com.curso.cleanarchitecture.tema01.modelo.InscripcionSimple;

/**
 * EL EJEMPLO PROBLEMÁTICO del Tema 1 (sección 5 de la teoría): un controlador
 * REST que implementa "inscribir un alumno en un curso" mezclando en un solo
 * método las responsabilidades de tres capas.
 *
 * <p><b>Capa que representa:</b> en teoría, solo la capa web (adaptador de
 * entrada HTTP). En la práctica, como se ve abajo, hace también de capa de
 * aplicación (coordina el flujo), de dominio (decide las reglas de negocio) y
 * casi de capa de persistencia (dirige los guardados). Eso es exactamente lo
 * que NO debe hacer un controlador.</p>
 *
 * <p><b>Reglas de negocio implicadas</b> (sección 5 de la teoría):</p>
 * <ul>
 *   <li>El curso debe existir.</li>
 *   <li>El alumno debe existir.</li>
 *   <li>El curso debe tener plazas disponibles.</li>
 *   <li>El alumno no debe estar ya inscrito.</li>
 *   <li>El curso no debe estar cerrado.</li>
 * </ul>
 *
 * <p><b>Qué enseña:</b> cada bloque del método está comentado indicando qué
 * responsabilidad mezclada representa y qué fila de la tabla de problemas de la
 * sección 6 de la teoría ilustra. Este código FUNCIONA; el objetivo del tema es
 * aprender a ver por qué, aun funcionando, es un mal diseño.</p>
 *
 * <p><b>Dependencias:</b> Spring Web (framework), tres repositorios concretos y
 * los modelos anémicos. Un controlador limpio dependería de UN caso de uso y de
 * nada más (sección 17 de la teoría).</p>
 *
 * <p><b>Nota docente:</b> aunque está anotada con {@code @RestController}, esta
 * clase es material de lectura: la única aplicación arrancable vive en
 * {@code com.curso.cleanarchitecture.proyecto} y solo escanea ese paquete, por
 * lo que este endpoint jamás se registra ni se puede invocar.</p>
 */
@RestController
@RequestMapping("/tema01/cursos")
public class InscripcionControllerAcoplado {

    // PROBLEMA (sección 6: "cambiar la persistencia puede afectar al flujo"):
    // el controlador conoce TRES clases concretas de persistencia. No hay
    // ninguna abstracción entre la web y los datos; si cambia la forma de
    // persistir, cambia el controlador.
    private final CursoRepositoryAcoplado cursoRepository;
    private final AlumnoRepositoryAcoplado alumnoRepository;
    private final InscripcionRepositoryAcoplado inscripcionRepository;

    public InscripcionControllerAcoplado(
            CursoRepositoryAcoplado cursoRepository,
            AlumnoRepositoryAcoplado alumnoRepository,
            InscripcionRepositoryAcoplado inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * Inscribe un alumno en un curso. Un solo método, cuatro responsabilidades.
     *
     * <p>Léase de arriba abajo identificando los bloques: lógica HTTP, reglas
     * de negocio, persistencia y manejo de errores, todos entrelazados. Esa
     * mezcla es la definición práctica de acoplamiento del Tema 1.</p>
     */
    @PostMapping("/{cursoId}/inscripciones/{alumnoId}")
    public ResponseEntity<String> inscribirAlumno(
            @PathVariable Long cursoId,
            @PathVariable Long alumnoId) {

        // ── BLOQUE 1: PERSISTENCIA + MANEJO DE ERRORES ──────────────────────
        // El controlador consulta directamente el "repositorio" (imagina un
        // SELECT ... WHERE id = ?). Regla de negocio implicada: "el curso debe
        // existir". Problema (sección 6): el controlador tiene demasiada
        // responsabilidad; además, lanza una RuntimeException genérica, con lo
        // que el cliente HTTP recibiría un 500 en lugar de un 404. El manejo de
        // errores de negocio y el de errores HTTP están confundidos.
        CursoSimple curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Mismo problema con el alumno: acceso a datos y decisión de error
        // mezclados en la capa web.
        AlumnoSimple alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        // ── BLOQUE 2: REGLA DE NEGOCIO en la capa web ───────────────────────
        // "Un curso cerrado no acepta inscripciones" (Actividad 2 de la teoría)
        // decidida aquí, dentro de un @RestController. Problemas (sección 6):
        // la lógica de negocio está mezclada con la web y depende de Spring.
        // Si mañana la inscripción llega por consola, CSV o Kafka (sección 24),
        // esta comprobación habría que duplicarla. En la versión limpia, esta
        // regla vive DENTRO de la entidad Curso (validarPuedeRecibirInscripcion).
        if (curso.isCerrado()) {
            // Además, la regla devuelve directamente una respuesta HTTP: el
            // negocio ("curso cerrado") y su representación web (400 + texto)
            // quedan soldados. Reutilizar la regla sin HTTP es imposible.
            return ResponseEntity.badRequest().body("El curso está cerrado");
        }

        // ── BLOQUE 3: REGLA DE NEGOCIO en la capa web ───────────────────────
        // "Un curso no puede superar sus plazas". Otra invariante del dominio
        // decidida fuera del dominio. Problema (sección 6): el negocio no está
        // protegido; las reglas importantes están en una clase externa, no en
        // el núcleo. Nada impide que otro endpoint olvide esta comprobación.
        if (curso.getPlazasDisponibles() <= 0) {
            return ResponseEntity.badRequest().body("No hay plazas disponibles");
        }

        // ── BLOQUE 4: PERSISTENCIA + REGLA DE NEGOCIO ───────────────────────
        // La consulta (¿existe la fila?) es persistencia, pero la decisión
        // (si existe, rechazar) es la regla "un alumno no puede inscribirse dos
        // veces". Comprobación y decisión en capas equivocadas: el día que se
        // añada otra vía de entrada, la regla se duplicará o se olvidará.
        boolean yaInscrito = inscripcionRepository.existsByCursoIdAndAlumnoId(cursoId, alumnoId);

        if (yaInscrito) {
            return ResponseEntity.badRequest().body("El alumno ya está inscrito");
        }

        // ── BLOQUE 5: PERSISTENCIA dirigida desde la web ────────────────────
        // El controlador crea el objeto y ordena guardarlo (imagina un INSERT).
        // Problema (sección 6): es difícil de probar; para verificar este flujo
        // hay que levantar Spring, poblar los "repositorios" y lanzar una
        // petición HTTP real, cuando la lógica en sí cabría en un test unitario.
        InscripcionSimple inscripcion = new InscripcionSimple(curso, alumno);
        inscripcionRepository.save(inscripcion);

        // ── BLOQUE 6: REGLA DE NEGOCIO ejecutada a mano + PERSISTENCIA ──────
        // "Ocupar una plaza" es una operación de negocio del Curso, pero como
        // el modelo es anémico, el controlador la hace a golpe de getter/setter
        // y sin volver a validar nada. Obsérvese también que si la aplicación
        // fallara entre el save anterior y este, quedaría una inscripción sin
        // plaza descontada: la coordinación transaccional tampoco tiene dueño.
        curso.setPlazasDisponibles(curso.getPlazasDisponibles() - 1);
        cursoRepository.save(curso);

        // ── BLOQUE 7: LÓGICA HTTP (la única que sí pertenece aquí) ─────────
        // Construir la respuesta web es trabajo legítimo del controlador. El
        // problema del método no es este bloque, sino todos los anteriores.
        return ResponseEntity.ok("Alumno inscrito correctamente");
    }
}

package com.curso.cleanarchitecture.ejercicios.tema01;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p><b>Enunciado:</b> identificar, bloque a bloque, las responsabilidades
 * mezcladas en el método {@link #inscribirAlumno(Long, Long)}. La SOLUCIÓN son
 * los comentarios-marcador: <b>[LÓGICA HTTP]</b>, <b>[REGLA DE NEGOCIO]</b>,
 * <b>[PERSISTENCIA]</b> y <b>[ERROR DE DISEÑO]</b>.</p>
 *
 * <p><b>Dependencias:</b> Spring Web y las clases de apoyo de este mismo
 * fichero (modelos y repositorios simulados), para que el ejercicio sea
 * autocontenido y compile sin tocar nada más.</p>
 *
 */
@RestController
@RequestMapping("/ejercicios/tema01/cursos")
public class AnalisisControladorAcoplado {

    private final CursoRepositorioEjercicio cursoRepository;
    private final AlumnoRepositorioEjercicio alumnoRepository;
    private final InscripcionRepositorioEjercicio inscripcionRepository;

    public AnalisisControladorAcoplado(
            CursoRepositorioEjercicio cursoRepository,
            AlumnoRepositorioEjercicio alumnoRepository,
            InscripcionRepositorioEjercicio inscripcionRepository) {
        this.cursoRepository = cursoRepository;
        this.alumnoRepository = alumnoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    /**
     * Método a analizar. Cada bloque lleva su marcador-solución.
     */
    @PostMapping("/{cursoId}/inscripciones/{alumnoId}")
    public ResponseEntity<String> inscribirAlumno(
            @PathVariable Long cursoId,
            @PathVariable Long alumnoId) {

        
        CursoEjercicio curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        AlumnoEjercicio alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));

        if (curso.isCerrado()) {

            return ResponseEntity.badRequest().body("El curso está cerrado");
        }

        if (curso.getPlazasDisponibles() <= 0) {
            return ResponseEntity.badRequest().body("No hay plazas disponibles");
        }

        boolean yaInscrito = inscripcionRepository.existsByCursoIdAndAlumnoId(cursoId, alumnoId);

        if (yaInscrito) {
            return ResponseEntity.badRequest().body("El alumno ya está inscrito");
        }

        InscripcionEjercicio inscripcion = new InscripcionEjercicio(curso, alumno);
        inscripcionRepository.save(inscripcion);

        curso.setPlazasDisponibles(curso.getPlazasDisponibles() - 1);

        cursoRepository.save(curso);

        return ResponseEntity.ok("Alumno inscrito correctamente");
    }
}

// ═══════════════════════════════════════════════════════════════════════════
// CLASES DE APOYO DEL EJERCICIO (package-private, en este mismo fichero para
// que el paquete sea autocontenido). Reproducen los modelos anémicos y los
// repositorios simulados con Map estático que usa el controlador analizado.
// ═══════════════════════════════════════════════════════════════════════════

/**
 * Modelo anémico de curso para el ejercicio: solo datos. La ausencia de
 * comportamiento es la causa raíz de que las reglas acaben en el controlador.
 */
class CursoEjercicio {

	private Long id;
	private String nombre;
	private int plazasDisponibles;
	private boolean cerrado;

	CursoEjercicio(Long id, String nombre, int plazasDisponibles, boolean cerrado) {
		this.id = id;
		this.nombre = nombre;
		this.plazasDisponibles = plazasDisponibles;
		this.cerrado = cerrado;
	}

	Long getId() {
		return id;
	}

	String getNombre() {
		return nombre;
	}

	int getPlazasDisponibles() {
		return plazasDisponibles;
	}

	// Este setter permite que el controlador ejecute la operación de negocio
	// "ocupar plaza" a mano y sin validación: parte de la solución del análisis.
	void setPlazasDisponibles(int plazasDisponibles) {
		this.plazasDisponibles = plazasDisponibles;
	}

	boolean isCerrado() {
		return cerrado;
	}
}

/** Modelo anémico de alumno para el ejercicio. */
class AlumnoEjercicio {

	private Long id;
	private String nombre;

	AlumnoEjercicio(Long id, String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	Long getId() {
		return id;
	}

	String getNombre() {
		return nombre;
	}
}

/** Modelo anémico de inscripción para el ejercicio: relación sin reglas. */
class InscripcionEjercicio {

	private Long id;
	private CursoEjercicio curso;
	private AlumnoEjercicio alumno;

	InscripcionEjercicio(CursoEjercicio curso, AlumnoEjercicio alumno) {
		this.curso = curso;
		this.alumno = alumno;
	}

	Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

	CursoEjercicio getCurso() {
		return curso;
	}

	AlumnoEjercicio getAlumno() {
		return alumno;
	}
}

/**
 * Repositorio simulado de cursos: "tabla CURSOS" en un Map estático. Nota
 * docente: nunca se escanea como bean (la aplicación arrancable solo escanea
 * com.curso.cleanarchitecture.proyecto).
 */
@Repository
class CursoRepositorioEjercicio {

	private static final Map<Long, CursoEjercicio> TABLA = new HashMap<>();

	static {
		// Tres cursos que permiten disparar cada regla del análisis.
		TABLA.put(1L, new CursoEjercicio(1L, "Clean Architecture con Java", 10, false));
		TABLA.put(2L, new CursoEjercicio(2L, "Introducción a Spring Boot", 5, true));
		TABLA.put(3L, new CursoEjercicio(3L, "Java Avanzado", 0, false));
	}

	/** Imagina aquí: SELECT * FROM cursos WHERE id = ? */
	Optional<CursoEjercicio> findById(Long id) {
		return Optional.ofNullable(TABLA.get(id));
	}

	/** Imagina aquí: UPDATE cursos SET plazas_disponibles = ? WHERE id = ? */
	CursoEjercicio save(CursoEjercicio curso) {
		TABLA.put(curso.getId(), curso);
		return curso;
	}
}

/** Repositorio simulado de alumnos: "tabla ALUMNOS" en memoria. */
@Repository
class AlumnoRepositorioEjercicio {

	private static final Map<Long, AlumnoEjercicio> TABLA = new HashMap<>();

	static {
		TABLA.put(1L, new AlumnoEjercicio(1L, "Ana"));
		TABLA.put(2L, new AlumnoEjercicio(2L, "Luis"));
	}

	/** Imagina aquí: SELECT * FROM alumnos WHERE id = ? */
	Optional<AlumnoEjercicio> findById(Long id) {
		return Optional.ofNullable(TABLA.get(id));
	}
}

/** Repositorio simulado de inscripciones: "tabla INSCRIPCIONES" en memoria. */
@Repository
class InscripcionRepositorioEjercicio {

	private static final Map<String, InscripcionEjercicio> TABLA = new HashMap<>();
	private static final AtomicLong SECUENCIA = new AtomicLong(1);

	/**
	 * Imagina aquí: SELECT COUNT(*) FROM inscripciones WHERE curso_id = ? AND
	 * alumno_id = ?
	 */
	boolean existsByCursoIdAndAlumnoId(Long cursoId, Long alumnoId) {
		return TABLA.containsKey(cursoId + "-" + alumnoId);
	}

	/**
	 * Imagina aquí: INSERT INTO inscripciones (id, curso_id, alumno_id) VALUES (?,
	 * ?, ?)
	 */
	InscripcionEjercicio save(InscripcionEjercicio inscripcion) {
		inscripcion.setId(SECUENCIA.getAndIncrement());
		TABLA.put(inscripcion.getCurso().getId() + "-" + inscripcion.getAlumno().getId(), inscripcion);
		return inscripcion;
	}
}

package com.curso.cleanarchitecture.tema05.demo;

import java.nio.file.Path;

import com.curso.cleanarchitecture.tema05.adapter.in.csv.InscripcionCsvAdapter;
import com.curso.cleanarchitecture.tema05.adapter.in.csv.InscripcionCsvMapper;
import com.curso.cleanarchitecture.tema05.adapter.out.persistence.AlumnoRepositoryInMemoryAdapter;
import com.curso.cleanarchitecture.tema05.adapter.out.persistence.CursoRepositoryInMemoryAdapter;
import com.curso.cleanarchitecture.tema05.adapter.out.persistence.InscripcionRepositoryInMemoryAdapter;
import com.curso.cleanarchitecture.tema05.adapter.out.persistence.NotificacionConsoleAdapter;
import com.curso.cleanarchitecture.tema05.adapter.out.report.InformeImportacionConsoleAdapter;
import com.curso.cleanarchitecture.tema05.base.Alumno;
import com.curso.cleanarchitecture.tema05.base.Curso;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoInputPort;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoUseCase;

/**
 * Ejecución manual del ejercicio sin Spring.
 */
public class DemoImportacionCsvTema05 {

    public static void main(String[] args) {
        CursoRepositoryInMemoryAdapter cursoRepository =
                new CursoRepositoryInMemoryAdapter();
        AlumnoRepositoryInMemoryAdapter alumnoRepository =
                new AlumnoRepositoryInMemoryAdapter();
        InscripcionRepositoryInMemoryAdapter inscripcionRepository =
                new InscripcionRepositoryInMemoryAdapter();

        cargarDatos(cursoRepository, alumnoRepository);

        InscribirAlumnoInputPort useCase = new InscribirAlumnoUseCase(
                cursoRepository,
                alumnoRepository,
                inscripcionRepository,
                new NotificacionConsoleAdapter());

        InscripcionCsvAdapter csvAdapter = new InscripcionCsvAdapter(
                useCase,
                new InscripcionCsvMapper(),
                new InformeImportacionConsoleAdapter());

        Path archivo = args.length > 0
                ? Path.of(args[0])
                : Path.of("src/main/resources/inscripciones_tema5.csv");

        csvAdapter.importar(archivo);
    }

    private static void cargarDatos(CursoRepositoryInMemoryAdapter cursoRepository,
                                     AlumnoRepositoryInMemoryAdapter alumnoRepository) {
        cursoRepository.guardar(new Curso(1L, "Clean Architecture", 2, false));
        cursoRepository.guardar(new Curso(2L, "Java avanzado", 0, false));
        cursoRepository.guardar(new Curso(3L, "Spring Boot", 5, true));

        alumnoRepository.guardar(new Alumno(10L, "Ana", "ana@example.com"));
        alumnoRepository.guardar(new Alumno(11L, "Luis", "luis@example.com"));
        alumnoRepository.guardar(new Alumno(12L, "Marta", "marta@example.com"));
        alumnoRepository.guardar(new Alumno(13L, "Carlos", "carlos@example.com"));
    }
}

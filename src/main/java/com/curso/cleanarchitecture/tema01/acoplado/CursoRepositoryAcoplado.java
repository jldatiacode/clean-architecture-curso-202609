package com.curso.cleanarchitecture.tema01.acoplado;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.curso.cleanarchitecture.tema01.modelo.CursoSimple;

/**
 * Repositorio de cursos SIMULADO: hace de "base de datos" usando un mapa
 * estático en memoria, para que el ejemplo del Tema 1 compile y se entienda
 * sin necesidad de SQL real.
 *
 * <p><b>Capa que representa:</b> persistencia (acceso a datos). En una
 * aplicación real sería un DAO con JDBC o un repositorio Spring Data JPA.</p>
 *
 * <p><b>Qué enseña:</b> este repositorio en sí no es el problema del tema; el
 * problema es QUIÉN lo usa. En la versión acoplada, el controlador REST lo
 * conoce y lo invoca directamente, de modo que la capa web queda atada a la
 * forma concreta de persistir (fila de la tabla de problemas de la sección 6:
 * "cambiar la persistencia puede afectar al flujo"). En la versión limpia, el
 * caso de uso dependerá de un puerto ({@code CursoRepositoryPort}) y esta clase
 * sería solo una implementación intercambiable.</p>
 *
 * <p><b>Dependencias:</b> el modelo anémico del tema y Spring (la anotación
 * {@code @Repository}). Nótese que ya estamos marcando persistencia con el
 * framework: en los temas siguientes discutiremos dónde debe quedar Spring.</p>
 *
 * <p><b>Nota docente:</b> aunque lleva {@code @Repository}, esta clase NUNCA se
 * registra como bean: la aplicación arrancable solo escanea
 * {@code com.curso.cleanarchitecture.proyecto}. Es material de lectura.</p>
 */
@Repository
public class CursoRepositoryAcoplado {

    // "Tabla CURSOS" en memoria. Es estática para simular un almacén compartido
    // por toda la aplicación, igual que lo sería la base de datos.
    // El estado estático mutable es, además, otro enemigo de la testabilidad:
    // los tests se contaminan unos a otros. Lo señalamos, pero en el Tema 1 nos
    // sirve para centrarnos en el problema del acoplamiento.
    private static final Map<Long, CursoSimple> TABLA_CURSOS = new HashMap<>();

    static {
        // Datos de ejemplo alineados con la teoría (sección 26): un curso
        // abierto con plazas, uno cerrado y uno sin plazas, para poder razonar
        // sobre las tres reglas de negocio del tema.
        TABLA_CURSOS.put(1L, new CursoSimple(1L, "Clean Architecture con Java", 10, false));
        TABLA_CURSOS.put(2L, new CursoSimple(2L, "Introducción a Spring Boot", 5, true));
        TABLA_CURSOS.put(3L, new CursoSimple(3L, "Java Avanzado", 0, false));
    }

    /**
     * Busca un curso por su identificador.
     *
     * <p>Imagina aquí una consulta SQL/JDBC:
     * {@code SELECT id, nombre, plazas_disponibles, cerrado FROM cursos WHERE id = ?}
     * seguida del mapeo manual del ResultSet al objeto.</p>
     */
    public Optional<CursoSimple> findById(Long cursoId) {
        return Optional.ofNullable(TABLA_CURSOS.get(cursoId));
    }

    /**
     * Guarda (inserta o actualiza) un curso.
     *
     * <p>Imagina aquí un {@code INSERT INTO cursos ...} o un
     * {@code UPDATE cursos SET plazas_disponibles = ?, cerrado = ? WHERE id = ?}.</p>
     */
    public CursoSimple save(CursoSimple curso) {
        TABLA_CURSOS.put(curso.getId(), curso);
        return curso;
    }
}

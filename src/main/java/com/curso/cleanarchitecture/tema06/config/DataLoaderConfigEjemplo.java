package com.curso.cleanarchitecture.tema06.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.curso.cleanarchitecture.tema06.persistence.AlumnoJpaEntity;
import com.curso.cleanarchitecture.tema06.persistence.CursoJpaEntity;
import com.curso.cleanarchitecture.tema06.persistence.SpringDataAlumnoRepository;
import com.curso.cleanarchitecture.tema06.persistence.SpringDataCursoRepository;

/**
 * Carga de datos iniciales con {@code CommandLineRunner} — el ejemplo de la
 * sección 11 de la teoría ({@code DataLoaderConfig}).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}, así que este runner nunca se ejecuta. En el proyecto
 * real la carga inicial se hace con {@code src/main/resources/data.sql}
 * (junto con {@code spring.jpa.defer-datasource-initialization=true} en
 * {@code application.properties} para que se ejecute DESPUÉS de que
 * Hibernate cree las tablas).</p>
 *
 * <p><b>¿CommandLineRunner o data.sql?</b> Dos formas válidas de lo mismo:</p>
 * <ul>
 *   <li>{@code CommandLineRunner}: código Java que se ejecuta al terminar de
 *       arrancar el contexto. Ventaja: pasa por las entidades y repositorios,
 *       puede tener lógica condicional. Es lo que muestra la teoría.</li>
 *   <li>{@code data.sql}: SQL plano que Spring Boot ejecuta al inicializar el
 *       DataSource. Ventaja: declarativo, visible de un vistazo, sin código.
 *       Es lo elegido en el proyecto real.</li>
 * </ul>
 *
 * <p>En ambos casos es INFRAESTRUCTURA de conveniencia para formación
 * (probar con Postman y mirar la consola H2 sin base vacía). Los datos de
 * verdad, en producción, no se cargan así.</p>
 */
@Configuration
public class DataLoaderConfigEjemplo {

    /**
     * Bean de tipo {@code CommandLineRunner}: Spring Boot ejecuta su método
     * {@code run(String... args)} una vez arrancada la aplicación. La lambda
     * {@code args -> { ... }} ES la implementación de esa interfaz funcional.
     *
     * <p>Fíjate en que trabaja directamente con repositorios Spring Data y
     * entidades JPA, sin pasar por puertos ni casos de uso: es legítimo
     * porque estamos en la capa externa hablando con piezas de la capa
     * externa. Cargar datos de demostración no es un caso de uso del negocio.</p>
     *
     * <p>Nota fina para clase: pasamos ids fijos (1L, 2L, 3L) aunque la
     * columna es IDENTITY, tal cual hace la teoría; con id ya existente JPA
     * hace merge en lugar de insert. Por sutilezas como esta (y por eso de
     * los contadores IDENTITY) el proyecto real prefiere {@code data.sql},
     * que además reinicia los contadores con {@code ALTER TABLE ... RESTART}.</p>
     */
    @Bean
    CommandLineRunner loadData(
            SpringDataCursoRepository cursoRepository,
            SpringDataAlumnoRepository alumnoRepository) {

        return args -> {
            // Cursos de ejemplo (sección 11). El segundo, con 0 plazas, y el
            // tercero, cerrado, permiten probar las reglas de negocio desde
            // Postman: "no hay plazas" y "curso cerrado".
            cursoRepository.save(new CursoJpaEntity(1L, "Clean Architecture con Java", 20, false));
            cursoRepository.save(new CursoJpaEntity(2L, "Spring Boot avanzado", 0, false));
            cursoRepository.save(new CursoJpaEntity(3L, "Arquitectura Hexagonal", 15, true));

            // Alumnos de ejemplo.
            alumnoRepository.save(new AlumnoJpaEntity(1L, "Ana García", "ana@email.com"));
            alumnoRepository.save(new AlumnoJpaEntity(2L, "Luis Pérez", "luis@email.com"));

            // Tras el arranque puedes comprobarlo en la consola H2:
            //   http://localhost:8080/h2-console  (jdbc:h2:mem:cursosdb / sa / sin password)
            //   SELECT * FROM CURSOS;  SELECT * FROM ALUMNOS;
        };
    }
}

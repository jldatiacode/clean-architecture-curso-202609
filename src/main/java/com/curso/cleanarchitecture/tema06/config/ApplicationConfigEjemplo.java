package com.curso.cleanarchitecture.tema06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.curso.cleanarchitecture.tema06.base.AlumnoRepositoryPort;
import com.curso.cleanarchitecture.tema06.base.CursoRepositoryPort;
import com.curso.cleanarchitecture.tema06.base.InscribirAlumnoInputPort;
import com.curso.cleanarchitecture.tema06.base.InscribirAlumnoUseCase;
import com.curso.cleanarchitecture.tema06.base.InscripcionRepositoryPort;

/**
 * Configuración de beans de la capa de aplicación — el ejemplo de la
 * sección 8 de la teoría ({@code ApplicationConfig}).
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación real solo escanea el paquete
 * {@code proyecto}, así que esta {@code @Configuration} compila pero nunca
 * se procesa; allí existe su equivalente escaneada.</p>
 *
 * <p><b>La idea clave del tema en una clase:</b> queremos que
 * {@code InscribirAlumnoUseCase} participe en la aplicación Spring SIN
 * ponerle anotaciones. La alternativa fácil sería anotarlo con
 * {@code @Service}, pero eso acopla la capa de aplicación al framework:
 * ya no compilaría sin Spring en el classpath... del módulo de aplicación.
 * En su lugar, lo construimos DESDE FUERA, en la capa de configuración,
 * con un {@code new} de toda la vida.</p>
 *
 * <p>Así el caso de uso sigue siendo Java puro y Spring queda donde debe:
 * en el exterior, haciendo de "fábrica y pegamento".</p>
 */
@Configuration // Clase de configuración: sus métodos @Bean aportan objetos al contenedor.
public class ApplicationConfigEjemplo {

    /**
     * Crea el bean del caso de uso "a mano".
     *
     * <p>Cómo funciona la inyección aquí: los parámetros del método son
     * puertos (interfaces). Spring busca en el contenedor beans que los
     * implementen y encuentra los adaptadores JPA anotados con
     * {@code @Repository} ({@code CursoRepositoryJpaAdapter}, etc.).
     * Nosotros solo declaramos QUÉ necesitamos; el contenedor resuelve
     * CON QUÉ satisfacerlo.</p>
     *
     * <p>El tipo de retorno es el puerto de entrada
     * ({@code InscribirAlumnoInputPort}), no la clase concreta: los
     * consumidores (el controlador REST del tema 5) dependerán de la
     * interfaz. Y fíjate en el cuerpo: un {@code new} normal y corriente.
     * <b>Así el caso de uso sigue siendo Java puro.</b></p>
     *
     * <p>Bonus para el debate en clase: si mañana quisiéramos envolver el
     * caso de uso con logging o métricas, este método podría devolver un
     * decorador sin tocar ni el caso de uso ni sus consumidores.</p>
     */
    @Bean
    public InscribirAlumnoInputPort inscribirAlumnoInputPort(
            CursoRepositoryPort cursoRepository,
            AlumnoRepositoryPort alumnoRepository,
            InscripcionRepositoryPort inscripcionRepository) {

        return new InscribirAlumnoUseCase(
                cursoRepository,
                alumnoRepository,
                inscripcionRepository
        );
    }
}

package com.curso.cleanarchitecture.tema06;

// Estos imports quedan comentados junto con la anotación y el arranque.
// Se dejan visibles (comentados) para que se vea exactamente qué necesita
// una clase principal de Spring Boot.
// import org.springframework.boot.SpringApplication;
// import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase principal de Spring Boot DE EJEMPLO (sección 5 de la teoría).
 *
 * <p><b>MATERIAL DE LECTURA:</b> esta clase compila pero NO arranca nada.
 * La aplicación real del curso es {@code CleanArchitectureCursosApplication},
 * en el paquete {@code com.curso.cleanarchitecture.proyecto}, y solo escanea
 * ese paquete.</p>
 *
 * <p><b>¿Por qué está comentada la anotación {@code @SpringBootApplication}?</b>
 * Porque no puede (ni debe) haber dos aplicaciones arrancables en el mismo
 * proyecto Maven:</p>
 * <ul>
 *   <li>Al ejecutar {@code mvn spring-boot:run}, el plugin busca UNA clase
 *       principal; con dos, habría que elegir a mano en cada arranque.</li>
 *   <li>{@code @SpringBootApplication} activa el escaneo de componentes desde
 *       el paquete de la clase hacia abajo. Si esta clase estuviera activa,
 *       escanearía {@code tema06} entero y registraría beans y entidades que
 *       son solo material docente, chocando con los del proyecto real
 *       (dos {@code @Entity} apuntando a la misma tabla {@code cursos},
 *       dos adaptadores implementando el mismo puerto, etc.).</li>
 * </ul>
 *
 * <p><b>Qué hace la anotación cuando está activa</b> (en la clase real del
 * paquete {@code proyecto}): {@code @SpringBootApplication} agrupa tres
 * anotaciones:</p>
 * <ul>
 *   <li>{@code @Configuration}: la clase puede definir beans.</li>
 *   <li>{@code @EnableAutoConfiguration}: Spring Boot configura solo lo que
 *       detecta en el classpath (al ver H2 y JPA, crea el DataSource, el
 *       EntityManager, la consola H2...). Aquí está la magia y también el
 *       riesgo: comodidad técnica no es lo mismo que buena arquitectura.</li>
 *   <li>{@code @ComponentScan}: busca componentes desde este paquete hacia
 *       abajo. Por eso la posición de la clase principal en el árbol de
 *       paquetes importa tanto.</li>
 * </ul>
 *
 * <p><b>Lectura arquitectónica:</b> esta clase es la capa MÁS externa de
 * todas. Conoce a Spring, arranca el servidor, dispara la configuración.
 * Nada del dominio depende de ella; ella depende de todo lo demás. Es el
 * "main" de la Screaming Architecture: un detalle de arranque, no el centro
 * del sistema.</p>
 */
// @SpringBootApplication  // <-- COMENTADA A PROPÓSITO: ver javadoc de la clase.
public class EjemploCursosApplication {

    /**
     * Así sería el método main tal cual aparece en la sección 5 de la teoría.
     * Lo dejamos sin la llamada real para que este fichero nunca compita con
     * la aplicación del paquete {@code proyecto}.
     */
    public static void main(String[] args) {
        // Con la anotación activa, la única línea necesaria sería:
        //
        //     SpringApplication.run(EjemploCursosApplication.class, args);
        //
        // Esa llamada crea el contexto de Spring, ejecuta el escaneo de
        // componentes, aplica la autoconfiguración y levanta Tomcat embebido.
        // Todo lo "pesado" de Spring Boot ocurre dentro de esa línea.
        System.out.println(
                "Material docente del tema 6. La aplicación arrancable real es "
                        + "CleanArchitectureCursosApplication (paquete proyecto).");
    }
}

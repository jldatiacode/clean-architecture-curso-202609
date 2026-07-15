package com.curso.cleanarchitecture.tema05.adapter.in.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoInputPort;
import com.curso.cleanarchitecture.tema05.base.InscripcionResponse;

/**
 * Test unitario del controlador SIN arrancar Spring.
 *
 * <p>Idea didáctica: un adaptador fino (que solo convierte, delega y devuelve)
 * se puede probar como cualquier clase Java. No hace falta {@code @SpringBootTest}
 * ni {@code @WebMvcTest}: instanciamos el controlador con {@code new}, le
 * inyectamos por constructor un puerto FAKE escrito a mano, y comprobamos las
 * dos traducciones que son su única responsabilidad:</p>
 * <ul>
 *   <li>Entrada: URL + request HTTP → command interno correcto.</li>
 *   <li>Salida: response interno → DTO HTTP + estado 200.</li>
 * </ul>
 *
 * <p>Esto es posible porque el controlador depende del PUERTO (interfaz) y no
 * de la implementación real del caso de uso. Si el controlador tuviera lógica
 * de negocio, este test se volvería complejo: la sencillez del test delata
 * (o confirma) la sencillez del adaptador.</p>
 *
 * <p>Nota: {@code ResponseEntity} es una clase normal de Spring; usarla no
 * requiere levantar ningún contexto. Aquí no hay servidor, ni JSON, ni red:
 * solo llamadas a métodos.</p>
 */
class InscripcionControllerTest {

    /**
     * Fake del puerto de entrada escrito a mano (sin librerías de mocks):
     * captura el command recibido y devuelve una respuesta preparada.
     * Nos permite verificar QUÉ le pasó el controlador al núcleo.
     */
    private static class InscribirAlumnoInputPortFake implements InscribirAlumnoInputPort {

        InscribirAlumnoCommand commandRecibido;
        InscripcionResponse respuestaPreparada;

        @Override
        public InscripcionResponse ejecutar(InscribirAlumnoCommand command) {
            this.commandRecibido = command;
            return respuestaPreparada;
        }
    }

    @Test
    @DisplayName("El controlador traduce URL + request HTTP al command interno")
    void traduceRequestACommand() {
        // Given: un fake que devolverá una respuesta cualquiera
        InscribirAlumnoInputPortFake inputPort = new InscribirAlumnoInputPortFake();
        inputPort.respuestaPreparada = new InscripcionResponse(1L, 7L, 10L, "ACTIVA");

        // El controlador se construye a mano: sin contexto de Spring.
        InscripcionController controller = new InscripcionController(inputPort);

        // When: simulamos la llamada que haría Spring MVC al recibir
        // POST /tema05/cursos/7/inscripciones con { "alumnoId": 10 }
        controller.inscribir(7L, new InscribirAlumnoRequest(10L));

        // Then: el command que llegó al núcleo combina el cursoId de la URL
        // con el alumnoId del cuerpo. Esa fusión es la "adaptación" de entrada.
        assertNotNull(inputPort.commandRecibido, "El controlador debe delegar en el puerto");
        assertEquals(7L, inputPort.commandRecibido.getCursoId());
        assertEquals(10L, inputPort.commandRecibido.getAlumnoId());
    }

    @Test
    @DisplayName("El controlador traduce el response interno al DTO HTTP con estado 200")
    void traduceResponseInternoAHttp() {
        // Given: el núcleo responderá con este response interno
        InscribirAlumnoInputPortFake inputPort = new InscribirAlumnoInputPortFake();
        inputPort.respuestaPreparada = new InscripcionResponse(42L, 7L, 10L, "ACTIVA");

        InscripcionController controller = new InscripcionController(inputPort);

        // When
        ResponseEntity<InscripcionHttpResponse> respuestaHttp =
                controller.inscribir(7L, new InscribirAlumnoRequest(10L));

        // Then: el código HTTP lo pone el adaptador (el caso de uso no sabe de códigos)...
        assertEquals(200, respuestaHttp.getStatusCode().value());

        // ...y el cuerpo es el DTO EXTERNO con los datos copiados del response interno.
        InscripcionHttpResponse cuerpo = respuestaHttp.getBody();
        assertNotNull(cuerpo);
        assertEquals(42L, cuerpo.getInscripcionId());
        assertEquals(7L, cuerpo.getCursoId());
        assertEquals(10L, cuerpo.getAlumnoId());
        assertEquals("ACTIVA", cuerpo.getEstado());
    }
}

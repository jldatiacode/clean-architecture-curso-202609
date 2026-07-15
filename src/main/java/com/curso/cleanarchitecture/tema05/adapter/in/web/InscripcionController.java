package com.curso.cleanarchitecture.tema05.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema05.base.InscribirAlumnoInputPort;
import com.curso.cleanarchitecture.tema05.base.InscripcionResponse;

/**
 * Adaptador de ENTRADA: controlador REST (sección 5 de la teoría).
 *
 * <p>¿Qué es un adaptador? Una pieza que conecta un puerto con una tecnología
 * concreta. Aquí la tecnología es HTTP/JSON con Spring MVC y el puerto es
 * {@link InscribirAlumnoInputPort}. El controlador hace exactamente tres cosas:</p>
 * <ol>
 *   <li><b>Convertir</b>: URL + JSON → {@link InscribirAlumnoCommand} (lenguaje interno).</li>
 *   <li><b>Delegar</b>: invocar el puerto de entrada. Cero lógica de negocio aquí:
 *       si el curso está cerrado o sin plazas lo decide el dominio, no este método.</li>
 *   <li><b>Devolver</b>: {@link InscripcionResponse} (interno) → {@link InscripcionHttpResponse}
 *       (externo) envuelto en un {@code ResponseEntity} con su código HTTP.</li>
 * </ol>
 *
 * <p>El {@code ResponseEntity} nace y muere en esta capa: el caso de uso no
 * sabe qué es un 200. Si mañana la aplicación se invoca desde un consumidor
 * de Kafka o una CLI, el caso de uso sirve tal cual; solo cambia el adaptador.</p>
 *
 * <p><b>MATERIAL DE LECTURA:</b> la única aplicación Spring arrancable vive en
 * {@code com.curso.cleanarchitecture.proyecto} y SOLO escanea ese paquete, así
 * que este {@code @RestController} compila pero nunca se registra como bean.
 * La versión funcional equivalente (probable con Postman) está en {@code proyecto}.</p>
 */
@RestController
@RequestMapping("/tema05/cursos")
public class InscripcionController {

    /**
     * El controlador depende del PUERTO (interfaz), nunca de
     * {@code InscribirAlumnoUseCase} directamente. Así el adaptador de entrada
     * queda desacoplado de la implementación del caso de uso, y en los tests
     * podemos sustituir el puerto por un fake (ver InscripcionControllerTest).
     */
    private final InscribirAlumnoInputPort inscribirAlumnoInputPort;

    public InscripcionController(InscribirAlumnoInputPort inscribirAlumnoInputPort) {
        this.inscribirAlumnoInputPort = inscribirAlumnoInputPort;
    }

    /**
     * POST /tema05/cursos/{cursoId}/inscripciones
     *
     * <p>Diseño de la API: el curso viaja en la URL (es el recurso padre) y el
     * alumno en el cuerpo. El command interno, en cambio, reúne ambos datos:
     * juntarlos es parte de la traducción que hace el adaptador.</p>
     */
    @PostMapping("/{cursoId}/inscripciones")
    public ResponseEntity<InscripcionHttpResponse> inscribir(
            @PathVariable Long cursoId,
            @RequestBody InscribirAlumnoRequest request) {

        // 1. CONVERTIR: mundo externo (URL + DTO HTTP) → mundo interno (command).
        InscribirAlumnoCommand command =
                new InscribirAlumnoCommand(cursoId, request.getAlumnoId());

        // 2. DELEGAR: el caso de uso decide; el controlador no opina.
        //    Si el núcleo lanza una excepción de negocio, la traducirá a HTTP
        //    el ApiExceptionHandler (sección 10), no este método.
        InscripcionResponse response = inscribirAlumnoInputPort.ejecutar(command);

        // 3. DEVOLVER: response interno → DTO HTTP + código de estado.
        return ResponseEntity.ok(new InscripcionHttpResponse(
                response.getInscripcionId(),
                response.getCursoId(),
                response.getAlumnoId(),
                response.getEstado()
        ));
    }
}

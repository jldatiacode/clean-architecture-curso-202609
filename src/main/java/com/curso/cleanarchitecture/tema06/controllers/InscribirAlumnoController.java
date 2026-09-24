package com.curso.cleanarchitecture.tema06.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.cleanarchitecture.tema06.base.InscribirAlumnoCommand;
import com.curso.cleanarchitecture.tema06.base.InscribirAlumnoInputPort;
import com.curso.cleanarchitecture.tema06.base.InscripcionResponse;

@RestController
@RequestMapping("/api/cursos")
public class InscribirAlumnoController {

    private final InscribirAlumnoInputPort incribirAlumnoInputPort;


    public InscribirAlumnoController(InscribirAlumnoInputPort crearCursoInputPort) {
    	System.out.println("Clase del inputPort"+crearCursoInputPort.getClass().getName());
        this.incribirAlumnoInputPort = crearCursoInputPort;
    }
    
    @GetMapping
    public String comprobar() {
        return "Spring Boot funciona correctamente";
    }
    
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
        InscripcionResponse response = incribirAlumnoInputPort.inscribir(command);

        // 3. DEVOLVER: response interno → DTO HTTP + código de estado.
        return null;
    }
}

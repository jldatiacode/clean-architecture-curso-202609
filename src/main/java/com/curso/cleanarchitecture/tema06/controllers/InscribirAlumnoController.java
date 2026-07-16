package com.curso.cleanarchitecture.tema06.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curso.cleanarchitecture.tema06.base.InscribirAlumnoInputPort;

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
}

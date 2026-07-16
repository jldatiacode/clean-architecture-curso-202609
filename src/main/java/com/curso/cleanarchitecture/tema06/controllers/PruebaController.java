package com.curso.cleanarchitecture.tema06.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prueba")
public class PruebaController {

    @GetMapping
    public String comprobar() {
        return "Spring Boot funciona correctamente";
    }
}

package com.curso.cleanarchitecture.tema06;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(
    scanBasePackages = "com.curso.cleanarchitecture.tema06"
)
@EntityScan(
    basePackages = "com.curso.cleanarchitecture.tema06"
)
@EnableJpaRepositories(
    basePackages = "com.curso.cleanarchitecture.tema06"
)
public class Tema06Application {

    public static void main(String[] args) {
        SpringApplication.run(Tema06Application.class, args);
    }
}
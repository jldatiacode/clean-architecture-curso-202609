/**
 * Objetos de salida de los casos de uso (sección 7 de la teoría).
 *
 * <p>La response de aplicación modela el resultado de un caso de uso sin
 * exponer entidades del dominio hacia fuera. Así, quien invoca el caso de uso
 * (un controlador, un test, un job) recibe datos planos y no puede mutar el
 * estado del dominio por accidente.</p>
 *
 * <p>Igual que con los commands: la response de aplicación NO es el DTO HTTP.
 * El adaptador de entrega decidirá cómo serializarla (JSON, XML, HTML...),
 * pero eso es asunto de otra capa.</p>
 */
package com.curso.cleanarchitecture.tema03.application.response;

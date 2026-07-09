/**
 * Capa de aplicación del Tema 4.
 *
 * <p>Aquí viven los casos de uso y, sobre todo, los CONTRATOS que esta capa
 * define para comunicarse con el exterior:</p>
 *
 * <ul>
 *   <li>{@code command}  → objetos de entrada que reciben los casos de uso.</li>
 *   <li>{@code response} → objetos de salida que devuelven los casos de uso.</li>
 *   <li>{@code port.in}  → puertos de entrada: acciones que la aplicación ofrece.</li>
 *   <li>{@code port.out} → puertos de salida: necesidades de la aplicación hacia fuera.</li>
 *   <li>{@code usecase}  → implementaciones de los puertos de entrada.</li>
 * </ul>
 *
 * <p>Idea central del tema: LA APLICACIÓN DEFINE LAS INTERFACES y la
 * infraestructura las implementa. Es la capa interna la que dicta el contrato,
 * porque es ella quien tiene la necesidad. Esto invierte la dirección natural
 * de la dependencia: en lugar de que la aplicación dependa de JPA, es el
 * adaptador JPA quien depende de la aplicación (implementa sus puertos).</p>
 */
package com.curso.cleanarchitecture.tema04.application;

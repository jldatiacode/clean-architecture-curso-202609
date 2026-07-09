/**
 * Commands: objetos de ENTRADA de los casos de uso.
 *
 * <p>Un command transporta los datos que necesita una acción concreta de la
 * aplicación. Forma parte del contrato del puerto de entrada: el adaptador
 * (un controlador REST, un test, una consola...) construye el command y se lo
 * entrega al input port.</p>
 *
 * <p>Características de estos objetos:</p>
 * <ul>
 *   <li>Inmutables: campos {@code final}, sin setters. Una vez creados no cambian.</li>
 *   <li>Sin lógica de negocio: solo transportan datos (las reglas viven en el
 *       dominio y en los casos de uso).</li>
 *   <li>Sin anotaciones de frameworks: son parte del núcleo, no de la infraestructura.</li>
 * </ul>
 */
package com.curso.cleanarchitecture.tema04.application.command;

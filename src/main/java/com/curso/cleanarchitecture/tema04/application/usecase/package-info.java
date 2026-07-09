/**
 * Casos de uso del Tema 4.
 *
 * <p>Cada caso de uso IMPLEMENTA un puerto de entrada (así el exterior puede
 * invocarlo a través del contrato) y USA puertos de salida (así delega en el
 * exterior lo que no le corresponde: persistir, notificar...).</p>
 *
 * <pre>
 *   Input Port ◀──implementa── UseCase ──usa──▶ Output Ports
 * </pre>
 *
 * <p>Consecuencia práctica: estos casos de uso compilan y se prueban sin
 * Spring, sin base de datos y sin servidor de correo. Solo necesitan que
 * ALGUIEN les entregue implementaciones de sus puertos de salida por
 * constructor (inyección de dependencias manual). En los tests ese alguien
 * son los fakes del paquete {@code tema04.fake}; en el Tema 5 serán los
 * adaptadores reales. El caso de uso no cambia en ningún escenario.</p>
 */
package com.curso.cleanarchitecture.tema04.application.usecase;

/**
 * Tema 4. Interfaces, puertos y contratos entre capas.
 *
 * <p>En este tema formalizamos los CONTRATOS de la aplicación mediante puertos:</p>
 *
 * <ul>
 *   <li><b>Puerto</b>: una interfaz que permite comunicar capas sin acoplarlas a una
 *       tecnología concreta. La aplicación define lo que necesita; la infraestructura
 *       decide cómo implementarlo.</li>
 *   <li><b>Puerto de entrada (input port)</b>: qué acciones OFRECE la aplicación.
 *       Lo consumen los adaptadores de entrada (por ejemplo un controlador REST)
 *       y lo implementan los casos de uso.</li>
 *   <li><b>Puerto de salida (output port)</b>: qué NECESITA la aplicación del exterior
 *       (persistencia, notificaciones...). Lo usan los casos de uso y lo implementan
 *       los adaptadores de infraestructura.</li>
 * </ul>
 *
 * <p>Flujo completo del tema (sección 8 de la teoría):</p>
 * <pre>
 *   REST Controller → Input Port → Use Case → Output Port ← Persistence Adapter
 * </pre>
 *
 * <p>La flecha clave es la última: la infraestructura apunta HACIA la aplicación
 * (implementa sus interfaces), y no al revés. Eso es la inversión de dependencias.</p>
 *
 * <p>Estructura de paquetes tras este tema (sección 12 de la teoría):</p>
 * <pre>
 *   tema04
 *   ├── model                  → entidades de dominio (Curso, Alumno, Inscripcion)
 *   ├── application
 *   │   ├── command            → datos de entrada de los casos de uso
 *   │   ├── response           → datos de salida de los casos de uso
 *   │   ├── port
 *   │   │   ├── in             → puertos de entrada (acciones ofrecidas)
 *   │   │   └── out            → puertos de salida (necesidades hacia fuera)
 *   │   └── usecase            → casos de uso que implementan los input ports
 *   └── fake                   → implementaciones en memoria de los output ports
 * </pre>
 */
package com.curso.cleanarchitecture.tema04;

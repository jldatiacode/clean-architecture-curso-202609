/**
 * <h2>Tema 5 — Adaptadores de SALIDA en memoria (sección 7 de la teoría)</h2>
 *
 * <p>Un adaptador de salida IMPLEMENTA un puerto definido por la aplicación.
 * La dependencia apunta hacia dentro: el adaptador conoce el puerto y el
 * dominio; el núcleo no conoce al adaptador.</p>
 *
 * <p>Empezamos con implementaciones en memoria (mapas) por una razón didáctica
 * y práctica: permiten tener el flujo completo funcionando ANTES de pelearnos
 * con base de datos, esquemas y configuración. Cuando en el Tema 6 conectemos
 * JPA + H2, sustituiremos estos adaptadores por los del subpaquete {@code jpa}
 * <b>sin tocar una línea del caso de uso</b>. Esa sustituibilidad es la prueba
 * de que los puertos están bien diseñados.</p>
 *
 * <p><b>MATERIAL DE LECTURA:</b> la aplicación arrancable vive en
 * {@code com.curso.cleanarchitecture.proyecto} (único paquete escaneado).
 * La versión funcional equivalente de estos adaptadores está allí.</p>
 */
package com.curso.cleanarchitecture.tema05.adapter.out.persistence;

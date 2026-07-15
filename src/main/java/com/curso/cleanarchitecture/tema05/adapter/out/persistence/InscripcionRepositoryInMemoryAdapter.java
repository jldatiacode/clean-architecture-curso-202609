package com.curso.cleanarchitecture.tema05.adapter.out.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.curso.cleanarchitecture.tema05.base.Inscripcion;
import com.curso.cleanarchitecture.tema05.base.InscripcionRepositoryPort;

/**
 * Adaptador de SALIDA en memoria para inscripciones.
 *
 * <p>Añade un matiz respecto a los otros adaptadores: la GENERACIÓN DE IDS.
 * El caso de uso entrega la inscripción sin id (crear identificadores es un
 * detalle de infraestructura) y este adaptador se lo asigna con un contador.
 * En la versión JPA del Tema 6 hará lo mismo una secuencia de la base de
 * datos; el puerto {@link InscripcionRepositoryPort} no cambia.</p>
 */
public class InscripcionRepositoryInMemoryAdapter implements InscripcionRepositoryPort {

    private final Map<Long, Inscripcion> inscripciones = new HashMap<>();

    /** Simula la secuencia/autoincremento de una base de datos. */
    private final AtomicLong secuencia = new AtomicLong(0);

    @Override
    public Inscripcion guardar(Inscripcion inscripcion) {
        // Si la inscripción llega sin id, se lo asignamos (como haría un INSERT
        // con clave autogenerada). Si ya lo trae, la sobrescribimos (UPDATE).
        Inscripcion aGuardar = inscripcion.getId() == null
                ? inscripcion.conId(secuencia.incrementAndGet())
                : inscripcion;

        inscripciones.put(aGuardar.getId(), aGuardar);

        // Devolvemos la versión persistida: así el caso de uso conoce el id
        // sin saber cómo se generó.
        return aGuardar;
    }
}

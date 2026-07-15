package com.curso.cleanarchitecture.tema05.adapter.out.persistence.jpa;

import com.curso.cleanarchitecture.tema05.base.Curso;

/**
 * Mapper de persistencia (sección 8 de la teoría): traduce
 * {@code CursoJpaEntity ↔ Curso} en ambos sentidos.
 *
 * <p>Papel de los mappers en Clean Architecture: cada capa tiene su propio
 * modelo (DTO HTTP en la web, entidad JPA en persistencia, objeto de dominio
 * en el núcleo) y los mappers son las aduanas entre ellos. Gracias a esta
 * clase, el dominio no necesita anotaciones JPA y la entidad no necesita
 * reglas de negocio.</p>
 *
 * <p>Lo escribimos a mano (es trivial y se lee de un vistazo). En proyectos
 * grandes se puede generar con MapStruct, pero el concepto es el mismo:
 * conversión explícita y localizada en la frontera. Si mañana la tabla
 * renombra una columna, el cambio muere aquí y en la entidad; el dominio
 * ni se entera.</p>
 *
 * <p><b>ESBOZO DIDÁCTICO:</b> versión completa en el Tema 6 y en
 * {@code com.curso.cleanarchitecture.proyecto}.</p>
 */
public class CursoPersistenceMapper {

    /** Del mundo de la base de datos al mundo del negocio. */
    public Curso toDomain(CursoJpaEntity entity) {
        return new Curso(entity.getId(), entity.getNombre(),
                entity.getPlazasDisponibles(), entity.isCerrado());
    }

    /** Del mundo del negocio al mundo de la base de datos. */
    public CursoJpaEntity toEntity(Curso curso) {
        return new CursoJpaEntity(curso.getId(), curso.getNombre(),
                curso.getPlazasDisponibles(), curso.isCerrado());
    }
}

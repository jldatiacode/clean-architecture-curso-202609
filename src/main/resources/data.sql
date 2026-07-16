-- ============================================================================
-- DATOS DE EJEMPLO (Tema 6: infraestructura)
-- ----------------------------------------------------------------------------
-- Estos datos se cargan al arrancar la aplicacion para poder probar la API
-- con Postman y consultar las tablas en la consola H2.
--
-- Escenarios preparados a proposito para demostrar las reglas de negocio:
--   * Curso 1: abierto y con plazas          -> la inscripcion funciona.
--   * Curso 2: abierto y con plazas          -> la inscripcion funciona.
--   * Curso 3: abierto pero SIN plazas       -> regla "no hay plazas disponibles".
--   * Curso 4: CERRADO                       -> regla "el curso esta cerrado".
--   * Ana ya esta inscrita en el curso 2     -> regla "ya esta inscrito".
-- ============================================================================

INSERT INTO cursos (id, nombre, plazas_disponibles, cerrado) VALUES
  (1, 'Curso de Java', 20, false),
  (2, 'Curso de Clean Architecture', 14, false),
  (3, 'Curso de Spring Boot', 0, false),
  (4, 'Curso de Arquitectura Hexagonal', 10, true);

INSERT INTO alumnos (id, nombre, email) VALUES
  (1, 'Ana', 'ana@email.com'),
  (2, 'Luis', 'luis@email.com'),
  (3, 'Marta', 'marta@email.com');

-- Ana (1) ya esta inscrita en el Curso de Clean Architecture (2).
-- Por eso ese curso tiene 14 plazas: tenia 15 y una esta ocupada.
INSERT INTO inscripciones
    (id, curso_id, alumno_id, estado)
VALUES
    (1, 2, 1, 'ACTIVA');

-- Las columnas id son IDENTITY (las genera la base de datos).
-- Como hemos insertado ids fijos, reiniciamos los contadores para que los
-- proximos INSERT de la aplicacion no choquen con los ids ya usados.
ALTER TABLE cursos ALTER COLUMN id RESTART WITH 100;
ALTER TABLE alumnos ALTER COLUMN id RESTART WITH 100;
ALTER TABLE inscripciones ALTER COLUMN id RESTART WITH 100;

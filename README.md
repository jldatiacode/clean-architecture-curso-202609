# Clean Architecture aplicada con Java — Proyecto del curso

Proyecto Eclipse/Maven que sirve como material práctico de un curso de 16 horas sobre **Clean Architecture aplicada con Java**. Contiene ejemplos progresivos por tema y un **proyecto integrador funcional**: un sistema de gestión de cursos e inscripciones con Spring Boot, JPA y H2.

## Qué se pretende enseñar

- Por qué el acoplamiento hace el software difícil de mantener y probar.
- Cómo proteger las reglas de negocio en un dominio de Java puro.
- Cómo diseñar casos de uso (interactores) independientes del framework.
- Cómo desacoplar capas con puertos de entrada y salida (inversión de dependencias).
- Cómo conectar el exterior mediante adaptadores (REST, persistencia, notificaciones).
- Cómo usar Spring Boot, JPA y H2 como detalles de la capa externa.
- Cómo probar cada capa con la técnica adecuada.

## Requisitos

- **Java 17** (o superior).
- Maven no es necesario si usas el wrapper incluido (`./mvnw` / `mvnw.cmd`).
- Eclipse (o IntelliJ/VS Code; el proyecto es Maven estándar).

## Cómo abrirlo en Eclipse

1. `File > Import... > Maven > Existing Maven Projects`.
2. Selecciona la carpeta `clean-architecture` (la que contiene `pom.xml`).
3. Finish. Eclipse descargará las dependencias automáticamente.

## Cómo ejecutar los tests

```bash
./mvnw test
```

Verás ~76 tests: los de dominio y casos de uso corren en milisegundos (sin Spring); los de integración levantan H2.

Para un test concreto:

```bash
./mvnw test -Dtest=CursoTest
./mvnw test -Dtest=InscripcionIntegrationTest
```

## Cómo arrancar la aplicación

```bash
./mvnw spring-boot:run
```

Arranca `CleanArchitectureCursosApplication` (paquete `proyecto`) en `http://localhost:8080` con datos de ejemplo cargados desde `data.sql`.

## Consola H2

Con la aplicación arrancada: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

| Campo | Valor |
|---|---|
| JDBC URL | `jdbc:h2:mem:cursosdb` |
| User Name | `sa` |
| Password | (vacío) |

Consultas útiles: `SELECT * FROM CURSOS;` · `SELECT * FROM ALUMNOS;` · `SELECT * FROM INSCRIPCIONES;`

## Probar la API con Postman

Datos precargados: cursos 1 (Java, abierto), 2 (Clean Architecture, abierto), 3 (Spring Boot, **sin plazas**), 4 (Arquitectura Hexagonal, **cerrado**); alumnos 1 (Ana, ya inscrita en el curso 2), 2 (Luis), 3 (Marta).

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/cursos` | Crear curso |
| GET | `/api/cursos` | Listar todos los cursos |
| GET | `/api/cursos/disponibles` | Cursos abiertos con plazas |
| POST | `/api/alumnos` | Registrar alumno |
| GET | `/api/alumnos` | Listar alumnos |
| POST | `/api/inscripciones` | Inscribir alumno en curso |
| DELETE | `/api/inscripciones/{id}` | Cancelar inscripción |
| GET | `/api/alumnos/{id}/inscripciones` | Inscripciones de un alumno |

Ejemplos:

```http
POST http://localhost:8080/api/inscripciones
Content-Type: application/json

{ "cursoId": 1, "alumnoId": 2 }
```

Respuesta `201 Created`:

```json
{ "inscripcionId": 100, "cursoId": 1, "alumnoId": 2, "fechaInscripcion": "2026-07-04", "estado": "ACTIVA" }
```

Escenarios de error preparados a propósito:

- `{ "cursoId": 4, ... }` → `400` "No se puede inscribir en un curso cerrado".
- `{ "cursoId": 3, ... }` → `400` "No hay plazas disponibles en el curso".
- `{ "cursoId": 2, "alumnoId": 1 }` → `400` "El alumno ya está inscrito en este curso".
- `{ "cursoId": 99, ... }` → `404` "No existe el curso con id 99".

## Estructura del proyecto

```text
src/main/java/com/curso/cleanarchitecture
├── tema01 .. tema07     Ejemplos didácticos por tema (material de LECTURA)
└── proyecto             Proyecto integrador: la única aplicación arrancable
```

**Importante**: la aplicación Spring Boot solo escanea el paquete `proyecto`. Las clases de `temaXX` compilan (y sus tests corren), pero no registran beans ni endpoints: son material de estudio. Así evitamos colisiones y hay una sola API funcional.

### Qué contiene y enseña cada tema

| Paquete | Contenido | Qué enseña |
|---|---|---|
| `tema01` | `acoplado/` (controlador que mezcla HTTP + negocio + persistencia), `modelo/` (clases anémicas), `separado/` (primera separación conceptual) | El problema: acoplamiento, responsabilidades mezcladas, código difícil de probar |
| `tema02` | `domain/model` (Curso, Alumno, Email, Inscripcion), `domain/exception` — Java puro, con tests | Entidades ricas, reglas de negocio, objetos de valor, excepciones de dominio |
| `tema03` | `application/` (command, response, puertos de salida, `InscribirAlumnoUseCase`), `fake/` (repos en memoria), con tests (fakes y Mockito) | Casos de uso e interactores; regla de negocio vs regla de aplicación |
| `tema04` | `port/in` y `port/out` completos, use cases implementando input ports, fakes, con tests | Puertos, contratos e inversión de dependencias |
| `tema05` | `adapter/in/web` (controller, DTOs, exception handler), `adapter/out/persistence` (en memoria + esbozo JPA), con test | Adaptadores de entrada/salida, mappers, DTO externo vs modelo interno |
| `tema06` | Entidades JPA, repositorios Spring Data, adaptadores JPA, configuración de beans y carga de datos (versiones de ejemplo) | Spring Boot/JPA/H2 como capa externa reemplazable |
| `tema07` | `package-info` con la estrategia de pruebas; los tests viven en `src/test/.../proyecto` | Qué probar en cada capa; pirámide de testing |

### El proyecto integrador (`proyecto`)

```text
proyecto
├── domain
│   ├── model          Curso, Alumno, Inscripcion, EstadoInscripcion
│   ├── valueobject    Email
│   └── exception      ReglaNegocioException, EntidadNoEncontradaException
├── application
│   ├── command / response
│   ├── port/in        8 puertos de entrada (uno por acción)
│   ├── port/out       CursoRepositoryPort, AlumnoRepositoryPort,
│   │                  InscripcionRepositoryPort, NotificacionPort
│   └── usecase        8 interactores (Java puro, sin anotaciones)
├── adapter
│   ├── in/web         Controladores REST, DTOs HTTP, ApiExceptionHandler
│   └── out
│       ├── persistence/inmemory   Adaptadores en memoria (tests/demos)
│       ├── persistence/jpa        Entidades JPA + Spring Data + mappers
│       └── notificacion           NotificacionConsoleAdapter
└── infrastructure
    └── config         ApplicationConfig: crea los use cases como beans
```

Flujo de una petición:

```text
HTTP → Controller → Input Port → Use Case → Dominio → Output Port → Adaptador JPA → H2
```

Reglas de negocio implementadas: curso cerrado no acepta inscripciones · curso sin plazas no acepta inscripciones · ocupar/liberar plaza al inscribir/cancelar · inscripción cancelada no se cancela dos veces · sin inscripciones duplicadas · nombre de curso obligatorio (≥ 3 caracteres) · email con formato mínimo y único.

## Cómo se relacionan los temas y orden recomendado

1. **tema01**: ver el problema (controlador acoplado).
2. **tema02**: construir el dominio en Java puro y probarlo.
3. **tema03**: coordinar el dominio con casos de uso.
4. **tema04**: formalizar contratos con puertos (entrada/salida).
5. **tema05**: conectar el exterior con adaptadores.
6. **tema06**: enchufar Spring Boot, JPA y H2 en el borde.
7. **tema07**: estrategia de pruebas por capa.
8. **proyecto**: recorrer la versión integrada, arrancarla, probarla con Postman y ejecutar sus tests.

Cada tema es autocontenido (se puede compilar y leer de forma aislada) y el modelo evoluciona igual que en la teoría del curso: en el tema 2 `Inscripcion` referencia objetos completos; a partir del tema 3 referencia ids (decisión comentada en el propio código).

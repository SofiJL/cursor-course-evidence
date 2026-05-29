# Prompts Log

## Day 01

### Prompt 01
`@labs/day-01/Backend/README.md`
Entiende cuáles son los pasos secuenciales que están descritos en `@labs/day-01/Backend/specs/01_setup.md`, y plafinica cuáles son las acciones que debes hacer para lograr el paso número 1.

Reglas:
1. No seguirás con los siguientes pasos hasta que yo te lo indique
2. Planificarás las acciones que debes hacer para yo revisar cómo se va a ejecutar, cuando te diga la palabra ejecutar es que las utilizarás.

#### Resultados obtenidos

- Se revisaron los 9 pasos secuenciales de `labs/day-01/Backend/specs/01_setup.md` y se planificó únicamente el **PASO 1** (estructura básica del proyecto Platziflix).
- Se generó el plan [Paso 1 Setup Backend](.cursor/plans/paso_1_setup_backend_907bb0fb.plan.md) con acciones pendientes de ejecución hasta indicar "ejecutar".
- **Alcance del paso 1:** crear/verificar en `labs/day-01/Backend/` este árbol:
  - `app/` con `__init__.py`, `main.py`, `core/config.py`, `db/base.py` (y `__init__.py` en cada subpaquete)
  - `pyproject.toml` y `README.md` en la raíz del backend
- **Criterio de éxito:** el árbol existe, los `__init__.py` permiten importar `app`, `app.core` y `app.db`; sin lógica de FastAPI, dependencias ni SQLAlchemy en este paso.
- **Fuera de alcance (pasos 2–9):** dependencias en `pyproject.toml`, `Settings`, endpoints `/` y `/health`, `uv sync`/uvicorn, Docker, configuración de base de datos en `base.py`.
- **Hallazgo en el repo:** la estructura del paso 1 ya existe; `main.py`, `config.py` y `base.py` contienen código de pasos posteriores. Al ejecutar el plan, lo esperado es **auditoría sin cambios destructivos** (no revertir código de pasos 2–9).
- **Acciones planificadas al ejecutar:** (1) confirmar raíz `Backend/`, (2) auditar 9 paths, (3) crear solo faltantes con stubs mínimos, (4) verificar imports de paquetes sin levantar servidor.
- **Próximo paso sugerido:** planificar o ejecutar el **PASO 2** (`pyproject.toml` con dependencias) cuando lo indiques.
- **Estado de ejecución:** planificado; no ejecutado en el repositorio.

### Prompt 02
@labs/day-01/Backend/specs/01_setup.md
Analiza y planifica el paso 2 antes de su ejecución

#### Resultados obtenidos

- Se analizó el **PASO 2** de `labs/day-01/Backend/specs/01_setup.md`: configurar dependencias mínimas en `pyproject.toml` (solo declaración; sin instalar ni ejecutar la app).
- Se generó el plan [Paso 2 Dependencias Backend](.cursor/plans/paso_2_dependencias_backend_e8660a41.plan.md) con acciones pendientes de ejecución hasta indicar "ejecutar".
- **Alcance del paso 2:** editar/verificar `pyproject.toml` con estas 6 dependencias:
  - `fastapi>=0.104.0`, `uvicorn[standard]>=0.24.0`, `sqlalchemy>=2.0.0`, `psycopg2-binary>=2.9.0`, `pydantic-settings>=2.0.0`, `python-dotenv>=1.0.0`
- **Fuera de alcance (pasos posteriores):** `uv sync` y pruebas locales (paso 5), `Settings` en `config.py` (paso 3), endpoints (paso 4), Docker (pasos 6–7), SQLAlchemy en `base.py` (paso 9).
- **Próximo paso sugerido:** planificar o ejecutar el **PASO 3** (`app/core/config.py`) cuando lo indiques.
- **Estado de ejecución:** planificado; no ejecutado en código ni con `uv sync`.

### Prompt 03

@labs/day-01/Backend/specs/01_setup.md 
Planifica y analiza lo que debes hacer para cumplir con el paso número 3

#### Resultados obtenidos

- Se analizó el **PASO 3** de `labs/day-01/Backend/specs/01_setup.md`: configuración de la aplicación en `app/core/config.py` con **pydantic-settings**.
- Se generó el plan [Paso 3 Config Backend](.cursor/plans/paso_3_config_backend_02ff01c0.plan.md) con acciones pendientes de ejecución hasta indicar "ejecutar".
- **Alcance del paso 3:** un solo archivo `app/core/config.py` con:
  - Clase `Settings(BaseSettings)` y `SettingsConfigDict` para leer variables de entorno (y opcionalmente `.env`)
  - Campos `PROJECT_NAME`, `VERSION`, `DATABASE_URL` (PostgreSQL con `postgresql+psycopg2://...`)
  - Instancia exportada `settings = Settings()`
- **Prerrequisitos:** pasos 1 y 2 cumplidos (`app/core/` existe; `pydantic-settings` y `python-dotenv` en `pyproject.toml`).
- **Fuera de alcance (pasos posteriores):** endpoints en `main.py` (paso 4), `uv sync`/uvicorn (paso 5), Docker (pasos 6–7), SQLAlchemy en `base.py` (paso 9).
- **Variables de entorno soportadas:** `PROJECT_NAME`, `VERSION`, `DATABASE_URL`.
- **Próximo paso sugerido:** planificar o ejecutar el **PASO 4** (`app/main.py` — FastAPI + `/` y `/health`) cuando lo indiques.
- **Estado de ejecución:** planificado; no ejecutado en el repositorio.

### Prompt 04

No estoy convencida de este paso que enlistaste en el plan @c:\Users\Ana Sofiacursor\plans\paso_2_dependencias_backend_e8660a41.plan.md Crear clase Settings usando pydantic-settings

@Browser Busca cúal es la configuración optima para un proyecto de FastAPI

#### Resultados obtenidos

- **Aclaración:** `Settings` con pydantic-settings no pertenece al plan del **PASO 2** (solo declara dependencias en `pyproject.toml`); corresponde al **PASO 3** (`app/core/config.py`). El paso 2 incluye la librería `pydantic-settings` como dependencia; el paso 3 la usa en código.
- **Validación con documentación oficial:** FastAPI recomienda [Settings and Environment Variables](https://fastapi.tiangolo.com/advanced/settings/) usando `BaseSettings` de `pydantic-settings`, lectura de variables de entorno y opcionalmente archivo `.env`.
- **Estado del proyecto:** `app/core/config.py` ya sigue el patrón básico oficial (`Settings` + `SettingsConfigDict` + `settings = Settings()`), alineado con el spec del curso (`PROJECT_NAME`, `VERSION`, `DATABASE_URL`).
- **Nivel básico (suficiente para el lab / paso 3):** `BaseSettings`, `env_file=".env"`, `extra="ignore"`, valores por defecto para desarrollo local, instancia global `settings` consumida desde `main.py`.
- **Nivel avanzado (producción y tests, opcional después):** `get_settings()` con `@lru_cache`, inyección vía `Depends`, `SecretStr` para credenciales, `.env.example` en repo y `.env` fuera de control de versiones, convención `snake_case` en campos del modelo.
- **Conclusión:** no es necesario abandonar `pydantic-settings`; la implementación actual es correcta para el curso. Mejoras como `lru_cache` + `Depends` son evolución recomendada por FastAPI, no requisito del paso 3 del spec.
- **Decisión:** mantener el enfoque del paso 3 para el lab; considerar refactor a `get_settings()` cuando haya tests o despliegue en producción.
- **Estado de ejecución:** consulta y análisis; sin cambios en el repositorio.

## Day 02

### Prompt 01

### Buen prompt

Analiza y plafinica cuáles son las acciones que debes hacer para lograr la siguiente tarea:
Llama al endpoint [http://localhost:8000/courses](http://localhost:8000/courses) que trae el listado de cursos e imprima una lista de los cursos que retorne en esta pagina @Frontend/src/app/page.tsx quiero que tengan un diseño de tarjeta y la pagina debe tener un titulo principal "Platzi Flix". En mi stack estoy usando nextjs 15 con app routes y css modules con sass, haz validación de types de typescript.

#### Resultados obtenidos

Plan de acciones y resumen para documentación
Parte 1 — Plan de acciones
Contexto del API
El backend expone GET [http://localhost:8000/courses](http://localhost:8000/courses) y devuelve un array JSON directo:

[
  {
    "id": 1,
    "name": "Curso de React",
    "description": "Curso de React",
    "thumbnail": "[https://via.placeholder.com/150](https://via.placeholder.com/150)",
    "slug": "curso-de-react"
  }
]
Acciones por capa
Acción	Detalle	Estado
1 Definir tipos TypeScript
Crear CourseListItem en src/types/index.ts con los campos del contrato del API
2 Validar respuesta en runtime
Crear parseCoursesList() con type guard en src/lib/validateCourses.ts
3 Fetch en Server Component
Función getCourses() async en page.tsx con fetch y cache: "no-store"
4 Manejo de errores
Capturar fallos de conexión, HTTP no OK y datos inválidos
5 Título principal
Renderizar Platzi Flix en el header
6 Componente de tarjeta
Crear/ajustar Course.tsx con imagen, nombre y descripción
7 Estilos con SASS
Grid responsivo en page.module.scss y tarjetas en Course.module.scss
8 Listado de cursos
Mapear el array con .map() y envolver cada tarjeta en 
9 Estados alternativos
Mensaje si no hay cursos; pantalla de error si el backend no responde
10 Pruebas
Tests de parseCoursesList y del componente Course
11 Verificación manual
Backend + frontend levantados y probados en el navegador

Flujo de implementación
page.tsx (Server Component)
    │
    ├─► fetch("[http://localhost:8000/courses](http://localhost:8000/courses)")
    │
    ├─► parseCoursesList(data: unknown) → CourseListItem[]
    │
    └─► courses.map() → CourseCard + Link → /course/[slug]

### Prompt 02

### Buen prompt

@Backend/app/specs/01_setup.md 
Analiza y planifica los pasos para ejecutar desde el paso 6, no ejecutes nada hasta que yo te lo indique expresamente.

#### Resultados obtenidos

Paso	Descripción (spec)	Estado	Observaciones
6 Crear Dockerfile
Archivo en Backend/Dockerfile
7 Crear docker-compose.yml
Archivo en Backend/docker-compose.yml
8 Verificar funcionamiento en Docker
Pendiente de verificación formal
Contenedores ya levantados con make start; falta documentar pruebas de /, /health, /docs
9 Configurar app/db/base.py
Engine, sesiones, Base y get_db() implementados

Los pasos 1–5 del setup ya están completados (estructura, dependencias, config, FastAPI básico, verificación local con uv). El proyecto evolucionó más allá del setup mínimo (endpoints de cursos, migraciones Alembic, seed, servicios, etc.), pero los pasos 6–9 siguen centrados en Docker y la conexión a base de datos.
Orden de ejecución recomendado (al retomar)
Revisar Dockerfile (PASO 6) — sin cambios esperados
Revisar docker-compose.yml y decidir puerto host DB: 5432 (spec) o 5433 (evitar conflicto local)
docker-compose up --build o make start (PASO 8)
Probar /, /health, /docs
Confirmar app/db/base.py (PASO 9) — sin cambios esperados

### Prompt 03

### Mal prompt

En @before-rules modifica el encabezado de la pantalla principal 

#### Resultados obtenidos

He actualizado el encabezado de la pantalla principal en labs/day-02/before-rules/Frontend.
Cambio principal en src/app/page.tsx: el título del  pasó de Platzi Flix a:
Platzi Flix — Ejemplo pruebas
Nota: En esa carpeta no había código fuente (src/ estaba vacío); solo quedaba el build en .next. Reconstruí los archivos de la página principal a partir de ese build para poder aplicar el cambio. Si tenías otros archivos locales (rutas de curso, reproductor, tests, etc.), conviene restaurarlos desde tu copia o backup.

Para ver el cambio con el backend en marcha:

cd labs/day-02/before-rules/Frontend
npm run dev
Luego abre [http://localhost:3000/](http://localhost:3000/) y deberías ver el encabezado Platzi Flix — Ejemplo pruebas.
El encabezado de la pantalla principal en labs/day-02/before-rules/Frontend quedó como Platzi Flix — Ejemplo pruebas en src/app/page.tsx.

Como no había código fuente en src/, reconstruí los archivos de la página principal y añadí package.json y tsconfig.json para que puedas levantar el proyecto con npm install y npm run dev en esa carpeta (con el backend en localhost:8000).

### Prompt 04

### Mal prompt

En @labs/day-02/before-rules corrige los errores.

#### Resultados obtenidos

Voy a revisar el proyecto en labs/day-02/before-rules para identificar y corregir los errores.
Corregí los errores en labs/day-02/before-rules/Frontend:
Build fallaba en prerender — La página intentaba llamar al API en tiempo de build sin backend. Añadí export const dynamic = 'force-dynamic' en page.tsx para renderizar bajo demanda.
ESLint roto — Faltaba el módulo run-parallel. Lo agregué en devDependencies y reinstalé dependencias.
Aviso de  — Reemplacé  por next/image en Course.tsx.
Manejo de errores — Creé src/app/error.tsx para cuando falle la carga de cursos.
Avisos de Sass — Quité prependData de next.config.ts (Next lo convertía a @import deprecado) y usé @use directo en los .module.scss.
El último npm run build terminó correctamente (exit 0). Para comprobar en local:
cd labs/day-02/before-rules/Frontend
npm run dev
Con el backend en [http://localhost:8000](http://localhost:8000) la página principal debería cargar sin errores.

### Prompt 05

### Prompt corregido

Analiza y planifica los pasos para correguir el error al mostrar en pantalla el encabezado de @labs/day-02/before-rules/Frontend/src/app/page.tsx 

#### Resultados obtenidos

En labs/day-02/before-rules/Frontend, el encabezado “Platzi Flix — Ejemplo pruebas” debe mostrarse siempre, incluso si falla GET [http://localhost:8000/courses](http://localhost:8000/courses). Mueve el  a un layout de ruta, deja el fetch solo en la sección de cursos, actualiza error.tsx para mantener el diseño y verifica con backend apagado y encendido.

Paso 1 — Reproducir y confirmar el síntoma
cd labs/day-02/before-rules/Frontend
npm run dev
Abrir [http://localhost:3000/](http://localhost:3000/) sin backend en :8000
Confirmar: no aparece “Platzi Flix — Ejemplo pruebas”, solo el mensaje de error
Levantar backend y repetir: el encabezado debería verse
Criterio de éxito del diagnóstico: el header solo falla cuando getCourses() lanza error.

Paso 2 — Separar UI estática de la carga de datos (recomendado)
Objetivo: que el encabezado no dependa del fetch.
Mover < header> (y opcionalmente gridBg) a (home)/layout.tsx
Dejar en page.tsx solo el < main> con el listado
En Next.js, si falla page.tsx, el layout.tsx del segmento padre sigue renderizándose

Paso 3 — Ajustar error.tsx para coherencia visual
Reutilizar clases de page.module.scss o extraer estilos compartidos
Mostrar el error solo en < main>, no reemplazar toda la estructura
Mantener botón “Reintentar”

Paso 4 — Endurecer getCourses() (opcional)
Mensajes de error claros (ya existen)
Valorar no usar throw para errores de red y mostrar estado vacío con aviso, si el curso lo permite
Solo si el requisito es “siempre ver header aunque no haya datos”
Paso 5 — Verificación manual

## Day 03

### Prompt 01

Genera el backend completo de TaskFlow en este workspace siguiendo estrictamente la documentación y reglas ya presentes.

## Antes de escribir código — lectura obligatoria

1. Lee @labs/day-03/initial-version/specs/00_contracts.md — contratos JSON, endpoints, reglas de negocio
2. Lee @labs/day-03/initial-version/specs/01_setup.md  — estructura de carpetas, bootstrap y convenciones de setup
3. Aplica las reglas de @labs/day-03/initial-version/.cursor/taskflow-project.mdc  y @labs/day-03/initial-version/.cursor/taskflow-backend.mdc

No improvises requisitos: si algo no está en esos archivos, pregunta antes de asumir.

## Alcance de esta sesión

- **Solo backend:** Java 21, Spring Boot 3, Maven
- **Persistencia en memoria** (ConcurrentHashMap). Prohibido JPA, H2 o BD real
- **No frontend** en esta sesión

## Entregables

Implementar en el directorio indicado por `01_setup.md`:

1. Proyecto Maven compilable con `mvn clean compile`
2. Arquitectura por capas bajo `com.example.taskflow`:
  - controller, service, repository, model, dto, mapper, exception
3. Los 5 endpoints REST según `00_contracts.md`:
  - POST /tasks (201)
  - GET /tasks (listado resumido)
  - GET /tasks/{id} (detalle)
  - PUT /tasks/{id}/status
  - DELETE /tasks/{id}
4. Reglas de negocio: título obligatorio, status inicial PENDING, estados PENDING/IN_PROGRESS/COMPLETED, fechas automáticas, 404 si tarea no existe
5. JSON con snake_case en API (`created_at`, `updated_at`)
6. GlobalExceptionHandler con errores `{ "message": "..." }`
7. Tests: TaskServiceTest (unitarios) y TaskControllerTest (integración)
8. README.md mínimo: cómo ejecutar (`mvn spring-boot:run`) y ejemplos curl

## Restricciones

- Funciones pequeñas; una responsabilidad por capa
- Dependencias mínimas: web, validation, test
- No crear commits git salvo que lo pida explícitamente
- No agregar archivos de documentación extra fuera del README
- Cambios focalizados; código fácil de revisar manualmente

## Verificación final

Al terminar, ejecuta y reporta resultado de:

- `mvn clean test`
- `mvn spring-boot:run` (smoke test manual de POST y GET /tasks con curl o equivalente)
Si algún test falla o el contrato JSON no coincide con `00_contracts.md`, corrígelo antes de dar por terminado.

#### Resultados obtenidos

Resumen de pasos — Backend TaskFlow

1. Lectura y planificación

Revisión de 00_contracts.md (contratos JSON, endpoints, reglas de negocio)
Revisión de 01_setup.md (estructura, capas, orden de implementación)
Aplicación de reglas en .cursor/taskflow-project.mdc y taskflow-backend.mdc
2. Bootstrap del proyecto
Creación de taskflow/ con Maven + Spring Boot 3.4.5 + Java 21
Dependencias: web, validation, test (sin JPA/H2)
TaskflowApplication.java y application.properties (puerto 8080)
3. Modelo de dominio
TaskStatus enum: PENDING, IN_PROGRESS, COMPLETED
Task entidad interna con camelCase (createdAt, updatedAt)
4. Capa de persistencia
TaskRepository con ConcurrentHashMap<Long, Task> y AtomicLong para IDs
Métodos: save, findById, findAll, existsById, deleteById
Listado ordenado por id ascendente
5. DTOs y mapper
Request: CreateTaskRequest, UpdateStatusRequest
Response: TaskResponse, TaskSummaryResponse, DeleteTaskResponse, ErrorResponse
TaskMapper para convertir Task ↔ DTOs
Snake_case en JSON con @JsonProperty("created_at") / @JsonProperty("updated_at")
6. Reglas de negocio (Service)
Crear: título obligatorio, status PENDING, fechas automáticas
Listar: resumen sin descripción ni fechas
Detalle / actualizar / eliminar: 404 si no existe
Actualizar status: validar enum, actualizar updatedAt
7. API REST (Controller)
Método	Ruta	HTTP
POST /tasks 201
GET /tasks 200
GET /tasks/{id} 200
PUT /tasks/{id}/status 200
DELETE /tasks/{id} 200
8. Manejo de errores
TaskNotFoundException → 404
InvalidStatusException → 400
Validación @Valid → 400 ({ "message": "..." })
GlobalExceptionHandler centralizado
9. Tests
TaskServiceTest — 9 tests unitarios (mock del repository)
TaskControllerTest — 9 tests de integración (contratos JSON y códigos HTTP)
10. Documentación y verificación
README.md con instrucciones de ejecución y ejemplos curl
mvn clean compile — OK
mvn clean test — 18 tests, todos pasaron
Smoke test con servidor en :8080 — POST, GET y DELETE verificados

### Prompt 02

Contexto: Backend TaskFlow (Spring Boot 3.4, Java 21) en labs/day-03/initial-version/taskflow.
La API unifica errores con ErrorResponse: { "message": "..." } vía GlobalExceptionHandler.

Problema: Si el cliente llama GET/PUT/DELETE /tasks/{id} con un id no numérico (ej. /tasks/abc),
Spring lanza MethodArgumentTypeMismatchException y responde con el JSON por defecto de Spring,
no con ErrorResponse. Eso rompe la consistencia del contrato de errores.

Tarea: Implementa la mejora de errores unificados para IDs de ruta inválidos.
Antes de ejecutar codigo considera lo siguiente: LA NUEVA VERSION DEL CODIGO GENERADO CON EL PROBLEMA PLANTEADO RESULTO AGREGALO EN LA CARPETA @labs/day-03/reviewed-version 

Requisitos:

1. En GlobalExceptionHandler.java, agrega @ExceptionHandler para MethodArgumentTypeMismatchException.
  - HTTP 400 Bad Request
  - Cuerpo: ErrorResponse con mensaje claro, ej. "Invalid task id: abc" (usa el valor recibido si existe)
2. En TaskControllerTest.java, agrega un test:
  - GET /tasks/abc → status 400 y jsonPath("$.message") con el mensaje esperado
  - Importa GlobalExceptionHandler si hace falta (ya está en @Import)
3. En taskflow/README.md, añade una nota breve en decisiones técnicas o "Mejoras detectadas"
  explicando el caso y un ejemplo curl.

Restricciones:

- No agregar dependencias nuevas (solo spring-boot-starter-web y validation existentes).
- No cambiar contratos funcionales de specs/00_contracts.md.
- Mantener el estilo y convenciones del proyecto (.cursor/taskflow-backend.mdc).
- Cambio mínimo: solo los archivos necesarios.

Verificación:

- Ejecuta mvn clean test en labs/day-03/initial-version/taskflow y confirma que todos los tests pasan.
- Indica el comando curl para probar manualmente GET /tasks/abc.

#### Resultados obtenidos

1. Análisis del problema

Se identificó que GET/PUT/DELETE /tasks/{id} con un id no numérico (ej. abc) provocaba MethodArgumentTypeMismatchException.
Spring respondía con su JSON por defecto, rompiendo el contrato unificado { "message": "..." } de ErrorResponse.
2. Preparación del entorno
Se copió el proyecto completo desde labs/day-03/initial-version/taskflow/ hacia labs/day-03/reviewed-version/taskflow/.
initial-version quedó intacta como versión base; la corrección vive en reviewed-version.
3. Cambio en GlobalExceptionHandler.java
Se importó MethodArgumentTypeMismatchException.
Se añadió @ExceptionHandler que responde:
HTTP 400 Bad Request
Body: { "message": "Invalid task id: abc" } (usa el valor recibido)
4. Cambio en TaskControllerTest.java
Se agregó el test getTaskById_returns400WhenIdIsNotNumeric().
Verifica: GET /tasks/abc → status 400 y jsonPath("$.message") = "Invalid task id: abc".
GlobalExceptionHandler ya estaba importado con @Import.
5. Actualización de README.md
Se añadió la sección Mejoras detectadas en decisiones técnicas.
Documenta el caso de IDs inválidos y un ejemplo curl.
6. Verificación
Se ejecutó mvn clean test en reviewed-version/taskflow.
Resultado: BUILD SUCCESS — 19 tests (10 controller + 9 service), 0 fallos.
7. Prueba manual sugerida
curl [http://localhost:8080/tasks/abc](http://localhost:8080/tasks/abc)

### Prompt 03

Contexto: En labs/day-03/reviewed-version/taskflow implementé la mejora de errores
unificados para IDs de ruta inválidos (copiado desde initial-version).
Archivos clave a revisar:
@labs/day-03/reviewed-version/taskflow/src/main/java/com/example/taskflow/exception/GlobalExceptionHandler.java
@labs/day-03/reviewed-version/taskflow/src/test/java/com/example/taskflow/controller/TaskControllerTest.java
@labs/day-03/reviewed-version/taskflow/README.md
@labs/day-03/initial-version/.cursor/taskflow-backend.mdc
Tarea: Explica el código generado por la mejora anterior. No modifiques archivos.
Estructura de la respuesta (en español, claro y conciso):

1. **Problema que resuelve**
  - Qué ocurre con GET /tasks/abc antes vs después del cambio.
  - Por qué importa para el contrato { "message": "..." }.
2. **Flujo técnico**
  - Desde la petición HTTP hasta la respuesta 400.
  - Qué es MethodArgumentTypeMismatchException y cuándo la lanza Spring.
  - Papel de @RestControllerAdvice y @ExceptionHandler.
3. **Código concreto**
  - Explica línea por línea (o por bloque) el método handleTypeMismatch.
  - Por qué HTTP 400 y no 404.
  - Cómo se construye el mensaje "Invalid task id: ...".
4. **Relación con el resto del handler**
  - Diferencia con TaskNotFoundException (404) y MethodArgumentNotValidException (400).
  - Tabla breve: excepción → HTTP → ejemplo de mensaje.
5. **Tests**
  - Qué verifica getTaskById_returns400WhenIdIsNotNumeric.
  - Por qué @Import(GlobalExceptionHandler) es necesario en @WebMvcTest.
6. **Verificación manual**
  - Comando curl y respuesta esperada.
  - Comparar con GET /tasks/99 (tarea inexistente).
7. **Resumen para documentación del curso** (máx. 5 viñetas)
  - Listo para pegar en docs/prompts-log.md → sección "Resultados obtenidos" del Prompt 03.

Restricciones:

- No proponer refactors ni nuevas features.
- No usar jerga sin definir; si usas un término de Spring, explícalo en una frase.
- Citar rutas de archivo cuando menciones clases o métodos.

#### Resultados obtenidos



## **Problema que resuelve**

La API de TaskFlow unifica todos los errores en el formato `{ "message": "..." }` mediante `ErrorResponse` y `GlobalExceptionHandler`. Sin la mejora, una petición como `GET /tasks/abc` fallaba **antes** de llegar al servicio: Spring intentaba convertir `"abc"` a `Long` en `@PathVariable Long id` del `TaskController`, lanzaba una excepción y respondía con el **JSON genérico de Spring** (`timestamp`, `status`, `error`, `path`), no con `ErrorResponse`.

**Después del cambio**, esa misma petición devuelve **400 Bad Request** con:

{ "message": "Invalid task id: abc" }

Esto importa porque el contrato de errores queda **consistente** en todos los casos: el cliente siempre puede leer `message` sin distinguir si el fallo vino de validación, de negocio o de un parámetro mal formado.

---

## **Flujo técnico**

1. El cliente envía `GET /tasks/abc`.
2. Spring enruta la petición a `TaskController.getTaskById(@PathVariable Long id)`.
3. Spring convierte el segmento de ruta `"abc"` al tipo `Long`. Como no es numérico, **no entra al método del controller** y lanza `MethodArgumentTypeMismatchException` (excepción de Spring que indica que un parámetro no pudo convertirse al tipo esperado).
4. `@RestControllerAdvice` en `GlobalExceptionHandler` intercepta excepciones de controllers de forma global (como un “filtro” centralizado de errores HTTP).
5. `@ExceptionHandler(MethodArgumentTypeMismatchException.class)` delega en `handleTypeMismatch`.
6. El método devuelve `ResponseEntity` con status **400** y cuerpo `ErrorResponse`.

Lo mismo aplica a `PUT /tasks/abc/status` y `DELETE /tasks/abc`, porque todos usan `@PathVariable Long id`.
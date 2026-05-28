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
No estoy convencida de este paso que enlistaste en el plan @c:\Users\Ana Sofia\.cursor\plans\paso_2_dependencias_backend_e8660a41.plan.md Crear clase Settings usando pydantic-settings

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

## Day 01

### Prompt 01
### Buen prompt
Analiza y plafinica cuáles son las acciones que debes hacer para lograr la siguiente tarea:
Llama al endpoint http://localhost:8000/courses que trae el listado de cursos e imprima una lista de los cursos que retorne en esta pagina @Frontend/src/app/page.tsx quiero que tengan un diseño de tarjeta y la pagina debe tener un titulo principal "Platzi Flix". En mi stack estoy usando nextjs 15 con app routes y css modules con sass, haz validación de types de typescript.

#### Resultados obtenidos
Plan de acciones y resumen para documentación
Parte 1 — Plan de acciones
Contexto del API
El backend expone GET http://localhost:8000/courses y devuelve un array JSON directo:

[
  {
    "id": 1,
    "name": "Curso de React",
    "description": "Curso de React",
    "thumbnail": "https://via.placeholder.com/150",
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
Mapear el array con .map() y envolver cada tarjeta en <Link>
9 Estados alternativos
Mensaje si no hay cursos; pantalla de error si el backend no responde
10 Pruebas
Tests de parseCoursesList y del componente Course
11 Verificación manual
Backend + frontend levantados y probados en el navegador

Flujo de implementación
page.tsx (Server Component)
    │
    ├─► fetch("http://localhost:8000/courses")
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
Cambio principal en src/app/page.tsx: el título del <header> pasó de Platzi Flix a:
Platzi Flix — Ejemplo pruebas
Nota: En esa carpeta no había código fuente (src/ estaba vacío); solo quedaba el build en .next. Reconstruí los archivos de la página principal a partir de ese build para poder aplicar el cambio. Si tenías otros archivos locales (rutas de curso, reproductor, tests, etc.), conviene restaurarlos desde tu copia o backup.

Para ver el cambio con el backend en marcha:

cd labs/day-02/before-rules/Frontend
npm run dev
Luego abre http://localhost:3000/ y deberías ver el encabezado Platzi Flix — Ejemplo pruebas.
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
Aviso de <img> — Reemplacé <img> por next/image en Course.tsx.
Manejo de errores — Creé src/app/error.tsx para cuando falle la carga de cursos.
Avisos de Sass — Quité prependData de next.config.ts (Next lo convertía a @import deprecado) y usé @use directo en los .module.scss.
El último npm run build terminó correctamente (exit 0). Para comprobar en local:
cd labs/day-02/before-rules/Frontend
npm run dev
Con el backend en http://localhost:8000 la página principal debería cargar sin errores.

### Prompt 05
### Prompt corregido
Analiza y planifica los pasos para correguir el error al mostrar en pantalla el encabezado de @labs/day-02/before-rules/Frontend/src/app/page.tsx 

#### Resultados obtenidos
En labs/day-02/before-rules/Frontend, el encabezado “Platzi Flix — Ejemplo pruebas” debe mostrarse siempre, incluso si falla GET http://localhost:8000/courses. Mueve el <header> a un layout de ruta, deja el fetch solo en la sección de cursos, actualiza error.tsx para mantener el diseño y verifica con backend apagado y encendido.

Paso 1 — Reproducir y confirmar el síntoma
cd labs/day-02/before-rules/Frontend
npm run dev
Abrir http://localhost:3000/ sin backend en :8000
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
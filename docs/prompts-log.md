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


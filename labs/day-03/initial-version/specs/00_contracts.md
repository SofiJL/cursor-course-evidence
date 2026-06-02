# TaskFlow

Sistema pequeño para administración de tareas personales.
Permite crear tareas, consultar tareas registradas, actualizar estados y eliminar tareas.

El objetivo es practicar generación controlada de código con Cursor, revisión humana y documentación técnica.

## Stacks

### Frontend

* Typescript
* React
* CSS Modules

### Backend

* Java 21
* Spring Boot
* Maven

### Base de datos

* En memoria
* Collections / Map

---

# Requerimientos funcionales

El sistema debe permitir:

1. Crear tareas
2. Consultar lista de tareas
3. Consultar detalle de tarea
4. Actualizar estado de tarea
5. Eliminar tareas

---

# Reglas de negocio

* El título de la tarea es obligatorio.
* El estado inicial de una tarea será `PENDING`.
* Una tarea solo puede tener los estados:

  * PENDING
  * IN_PROGRESS
  * COMPLETED
* No se debe permitir actualizar tareas inexistentes.
* Las fechas deben generarse automáticamente.

---

# Contratos

## Entidades

1. Task

---

# Contratos JSON

## Task

```json
{
    "id": 1,
    "title": "Preparar documentación",
    "description": "Generar documentación técnica del proyecto",
    "status": "PENDING",
    "created_at": "2026-05-28T10:00:00",
    "updated_at": "2026-05-28T10:00:00"
}
```

---

# Endpoints

## POST /tasks

Crear una nueva tarea.

### Request

```json
{
    "title": "Preparar documentación",
    "description": "Generar documentación técnica"
}
```

### Response

```json
{
    "id": 1,
    "title": "Preparar documentación",
    "description": "Generar documentación técnica",
    "status": "PENDING",
    "created_at": "2026-05-28T10:00:00",
    "updated_at": "2026-05-28T10:00:00"
}
```

---

## GET /tasks

Obtener listado de tareas.

### Response

```json
[
    {
        "id": 1,
        "title": "Preparar documentación",
        "status": "PENDING"
    },
    {
        "id": 2,
        "title": "Revisar prompts",
        "status": "IN_PROGRESS"
    }
]
```

---

## GET /tasks/{id}

Obtener detalle de una tarea.

### Response

```json
{
    "id": 1,
    "title": "Preparar documentación",
    "description": "Generar documentación técnica",
    "status": "PENDING",
    "created_at": "2026-05-28T10:00:00",
    "updated_at": "2026-05-28T10:00:00"
}
```

---

## PUT /tasks/{id}/status

Actualizar estado de tarea.

### Request

```json
{
    "status": "COMPLETED"
}
```

### Response

```json
{
    "id": 1,
    "title": "Preparar documentación",
    "description": "Generar documentación técnica",
    "status": "COMPLETED",
    "created_at": "2026-05-28T10:00:00",
    "updated_at": "2026-05-28T12:30:00"
}
```

---

## DELETE /tasks/{id}

Eliminar tarea.

### Response

```json
{
    "message": "Task deleted successfully"
}
```

---

# Arquitectura esperada

El backend debe seguir estructura por capas:

```txt
src/main/java/com/example/taskflow
├── controller
├── service
├── repository
├── dto
├── model
├── mapper
└── exception
```

---

# Consideraciones técnicas

* No utilizar persistencia real.
* Usar almacenamiento temporal en memoria.
* Mantener funciones pequeñas y legibles.
* Aplicar responsabilidad única.
* Agregar validaciones básicas.
* Evitar dependencias innecesarias.
* Generar código fácil de revisar manualmente.

---

# Objetivo de la práctica

Esta práctica busca:

* Generar código controlado con Cursor.
* Revisar manualmente el resultado generado.
* Detectar posibles mejoras.
* Aplicar refactor y correcciones mediante prompts.
* Documentar el flujo de trabajo y aprendizaje obtenido.

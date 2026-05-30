# Bug Report — Status en minúsculas

**Estado:** Resuelto  
**Fecha detección:** 2026-05-29  
**Fecha corrección:** 2026-05-29  
**Servicio:** TaskFlow (`labs/day-04/source-code/taskflow`)

## Descripción

Al actualizar el status de una tarea con un valor válido en minúsculas, el servidor respondía **400 Bad Request** en lugar de aceptar la petición.

## Endpoint afectado

`PUT /tasks/{id}/status`

## Pasos para reproducir (antes del fix)

1. Crear una tarea: `POST /tasks` con body `{"title":"Test","description":"Test"}`
2. Actualizar status en minúsculas: `PUT /tasks/1/status` con body `{"status":"completed"}`

## Comportamiento anterior

- **Request:** `PUT /tasks/1/status` → `{"status":"completed"}`
- **Response:** `400 Bad Request`
- **Body:** `{"message":"Invalid status: completed"}`

## Causa raíz

`TaskService.parseStatus()` usaba `TaskStatus.valueOf(status.trim())`, sensible a mayúsculas. El enum define `PENDING`, `IN_PROGRESS`, `COMPLETED`.

## Solución aplicada

Normalizar el input con `.trim().toUpperCase()` antes de `TaskStatus.valueOf()` en `TaskService.java` línea 84.

```java
return TaskStatus.valueOf(status.trim().toUpperCase());
```

## Verificación

| Verificación | Resultado |
|--------------|-----------|
| Test `updateTaskStatus_acceptsLowercaseStatus` | PASS |
| `mvn clean test` (20 tests) | BUILD SUCCESS |
| Manual: `PUT /tasks/1/status` + `"completed"` | **200 OK** |
| Manual: `PUT /tasks/1/status` + `"invalid"` | **400 Bad Request** |
| Regresión: `"COMPLETED"` en mayúsculas | Sigue funcionando |

## Test añadido

- `TaskServiceTest.updateTaskStatus_acceptsLowercaseStatus` — verifica que `"completed"` se acepta y persiste como `TaskStatus.COMPLETED`.

## Criterios de aceptación

- [x] Test `updateTaskStatus_acceptsLowercaseStatus` pasa
- [x] Tests existentes siguen pasando (regresión)
- [x] Prueba manual HTTP con `"completed"` devuelve 200
- [x] Valores inválidos siguen devolviendo 400

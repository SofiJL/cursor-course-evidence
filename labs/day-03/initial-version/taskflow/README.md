# TaskFlow Backend

API REST para administración de tareas personales. Persistencia en memoria (`ConcurrentHashMap`).

## Requisitos

- Java 21
- Maven 3.9+

## Ejecutar

```bash
cd taskflow
mvn spring-boot:run
```

La API queda disponible en `http://localhost:8080`.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/tasks` | Crear tarea (201) |
| GET | `/tasks` | Listado resumido |
| GET | `/tasks/{id}` | Detalle completo |
| PUT | `/tasks/{id}/status` | Actualizar estado |
| DELETE | `/tasks/{id}` | Eliminar tarea |

## Ejemplos con curl

```bash
# Crear tarea
curl -X POST http://localhost:8080/tasks \
  -H "Content-Type: application/json" \
  -d '{"title":"Preparar documentación","description":"Generar documentación técnica"}'

# Listar tareas (resumen: id, title, status)
curl http://localhost:8080/tasks

# Detalle de tarea
curl http://localhost:8080/tasks/1

# Actualizar estado
curl -X PUT http://localhost:8080/tasks/1/status \
  -H "Content-Type: application/json" \
  -d '{"status":"COMPLETED"}'

# Eliminar tarea
curl -X DELETE http://localhost:8080/tasks/1
```

## Tests

```bash
mvn clean test
```

## Decisiones técnicas

- **Persistencia:** `ConcurrentHashMap` en memoria; los datos se pierden al reiniciar el servidor.
- **JSON:** `created_at` y `updated_at` con `@JsonProperty` en los DTOs de respuesta.
- **Listado:** ordenado por `id` ascendente.
- **Fechas:** `LocalDateTime` sin zona horaria, según contrato de la especificación.

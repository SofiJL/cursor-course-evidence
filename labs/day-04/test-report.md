# Reporte de pruebas — TaskFlow (day-04)

**Fecha:** 2026-05-29 (post-fix bug status minúsculas)  
**Proyecto:** `labs/day-04/source-code/taskflow`  
**Comando ejecutado:**

```bash
mvn clean test
```

**Entorno:**
- Java 21.0.7 (Eclipse Adoptium)
- Maven 3.x (Apache NetBeans bundle)
- Spring Boot 3.4.5
- JUnit 5 + Mockito + MockMvc

---

## Resumen

| Métrica | Valor |
|---------|-------|
| Tests ejecutados | 20 |
| Exitosos | 20 |
| Fallidos | 0 |
| Errores | 0 |
| Omitidos | 0 |
| Tiempo total | 20.3 s |
| **Resultado** | **BUILD SUCCESS** |

---

## TaskServiceTest (unitarios)

**Paquete:** `com.example.taskflow.service`  
**Tests:** 10 | **Fallos:** 0 | **Tiempo:** 0.388 s

| Test | Estado |
|------|--------|
| `createTask_setsPendingStatusAndDates` | PASS |
| `getAllTasks_returnsSummaries` | PASS |
| `getTaskById_returnsTaskWhenFound` | PASS |
| `getTaskById_throwsWhenNotFound` | PASS |
| `updateTaskStatus_updatesStatusAndUpdatedAt` | PASS |
| `updateTaskStatus_throwsWhenTaskNotFound` | PASS |
| `updateTaskStatus_acceptsLowercaseStatus` | PASS *(nuevo)* |
| `updateTaskStatus_throwsWhenStatusInvalid` | PASS |
| `deleteTask_removesTaskWhenExists` | PASS |
| `deleteTask_throwsWhenNotFound` | PASS |

---

## TaskControllerTest (contratos HTTP)

**Paquete:** `com.example.taskflow.controller`  
**Tests:** 10 | **Fallos:** 0 | **Tiempo:** 9.882 s

| Test | Estado |
|------|--------|
| `createTask_returns201WithFullContract` | PASS |
| `createTask_returns400WhenTitleMissing` | PASS |
| `getAllTasks_returnsSummaryList` | PASS |
| `getTaskById_returnsFullDetail` | PASS |
| `getTaskById_returns404WhenNotFound` | PASS |
| `getTaskById_returns400WhenIdIsNotNumeric` | PASS |
| `updateTaskStatus_returnsUpdatedTask` | PASS |
| `updateTaskStatus_returns400WhenStatusInvalid` | PASS |
| `deleteTask_returnsSuccessMessage` | PASS |
| `deleteTask_returns404WhenNotFound` | PASS |

---

## Verificación manual HTTP (post-fix)

| Petición | Status | Resultado |
|----------|--------|-----------|
| `POST /tasks` | 201 | Tarea creada |
| `PUT /tasks/1/status` + `"completed"` | **200** | Status actualizado a `COMPLETED` |
| `PUT /tasks/1/status` + `"invalid"` | **400** | `{"message":"Invalid status: invalid"}` |

---

## Conclusión

Bug de status en minúsculas corregido. La suite completa (20 tests) pasa y la verificación manual confirma que `PUT /tasks/{id}/status` acepta valores válidos en cualquier capitalización.

**Artefactos:** `labs/day-04/source-code/taskflow/target/surefire-reports/`  
**Bug report:** `labs/day-04/bug-report.md`

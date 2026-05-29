# TaskFlow (Backend)

Sistema pequeño para administración de tareas personales.
Permite crear tareas, consultar tareas registradas, actualizar estados y eliminar tareas.

## Flujo técnico

1. El cliente envía GET /tasks/abc.
2. Spring enruta la petición a TaskController.getTaskById(@PathVariable Long id).
3. Spring convierte el segmento de ruta "abc" al tipo Long. Como no es numérico, no entra al método del controller y lanza MethodArgumentTypeMismatchException.
4. @RestControllerAdvice en GlobalExceptionHandler intercepta excepciones de controllers de forma global (como un “filtro” centralizado de errores HTTP).
5. @ExceptionHandler(MethodArgumentTypeMismatchException.class)` delega en handleTypeMismatch.
6. El método devuelve ResponseEntity con status 400 y cuerpo ErrorResponse.

Lo mismo aplica a PUT /tasks/abc/status y DELETE /tasks/abc, porque todos usan @PathVariable Long id.

## Revisiones

Se reviso la estructura de carpetas generadas, que existiera la carpeta controller, service, repository, dtos, models y excepciones.

Se revisó que se generaran las clases especificadas en el archivo 01_setup.md en las carpetas especificadas.

En la raíz del proyecto se revisó que el archivo pom.xml contuviera las dependencias especificadas en 01_setup.md.

Se revisó que las reglas de negocio especificadas se generaran en la capa de servicio, como se especificó.

Se revisó que los DTOs de respuesta tuvieran los campos correspondientes especificados.

Se revisó que los estados de respuesta fueran los especificados en el archivo y siguieran la nomenclatura.

Se revisó que los endpoints generados retornaran la respuesta especificada.

Se revisó que la clase personalizada para el manejo de excepciones existiera.

Se revisó la implementación y el manejo de la persistencia en memoria.

Se revisó la cobertura de las pruebas generadas
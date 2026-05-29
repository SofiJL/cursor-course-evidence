# Lessons Learned 

## Day 01


- Es posible añadir contexto referenciando archivos por medio del arroba.
- Cursor responde mejor cuando el prompt incluye contexto técnico.
- Las reglas ayudan a controlar el estilo y orientación del código.
- Las reglas pueden ser aplicadas a extensiones de archivos especificas, o aplicadas manualmente.
- Es importante revisar manualmente el código generado.
- La carpeta specs debe contener la documentación sobre cada iteración del proyecto.
- Mientras más específico es el prompt, más consistente es el resultado generado.
- No todo el código generado sigue buenas prácticas automáticamente.

## Day 02

- Los prompts que son más ambiguos producen respuestas igual de ambiguas.
- Se pueden crear reglas a nivel del proyecto pero también a nivel del usuario.
- Las reglas reducen la necesidad de repetir instrucciones en cada prompt, pues se pueden reutilizar.
- Es importante definir explícitamente estándares de calidad esperados.
- El código que es generado con más reglas suele ser más fácil de leer y consistente.
- Aunque se incluyan más reglas, sigue siendo necesaria la validación manual del código.
- Los prompts que no siguen una estructura correcta y que tienen menos contexto, suelen tardar más en ser ejecutados.
- Es mejor pedirle a Cursor que planifique e identifique las actividades que va a realizar antes de ejecutarlas.


## Day 03

- Un prompt que referencia specs/, contratos y reglas .mdc permite generar un backend completo sin improvisar requisitos.
- Las reglas por capa reducen inconsistencias (snake_case, formato de errores, dependencias permitidas).
- La revisión humana detectó casos que los tests no cubrían.
- Cursor puede ayudar a generar prompts más estructurados.
- Los prompts generados automáticamente deben revisarse y ajustarse manualmente.
- Los prompts demasiado genéricos producen resultados inconsistentes.
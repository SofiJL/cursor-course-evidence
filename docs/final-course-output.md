# Resumen de lecciones aprendidas
---

## Resumen 

En estos días de practica y capacitación he aprendido que desarrollar con Cursor de forma efectiva no consiste solo en pedirle generar código, sino en orquestar contexto, reglas y un flujo de trabajo. Las lecciones del curso convergen en cuatro ideas que considero principales:

1. Contexto explícito: mejora la calidad y consistencia de lo generado.
2. Reglas reutilizables: reducen ambigüedad y repetición, pero no sustituyen la revisión humana.
3. Prompts específicos y estructurados: crear promts que sean especificos y planificar antes de ejecutar aceleran resultados útiles y producen mejores resultados.
4. La validación manual sigue siendo obligatoria: la IA no garantiza buenas prácticas ni cobertura completa de casos.

---

## Lecciones transversales

### Contexto y documentación

Referenciar archivos con `@` aporta contexto preciso a la IA 
Los prompts con contexto técnico obtienen respuestas más alineadas 
La carpeta `specs/` debe documentar contratos e iteraciones del proyecto
Referenciar `specs/`, contratos y reglas `.mdc` permite generar un backend sin improvisar requisitos

### Reglas de Cursor (`.mdc`)

Las reglas orientan estilo y decisiones de código
Pueden aplicarse por extensión de archivo o de forma manual
Existen reglas a nivel de proyecto y a nivel de usuario
Reducen la necesidad de repetir instrucciones en cada prompt 
Reglas por capa (nomenclatura, errores, dependencias) reducen inconsistencias 
Más reglas suelen producir código más legible y uniforme
Conviene definir explícitamente los estándares de calidad esperados

### Prompts y plan

A mayor especificidad del prompt, mayor consistencia del resultado
Prompts ambiguos generan respuestas ambiguas
Prompts genéricos producen resultados inconsistentes
Prompts sin estructura ni contexto suelen tardar más en ejecutarse
Es mejor pedir planificación de actividades antes de ejecutar cambios
Cursor puede ayudar a redactar prompts más estructurados; hay que revisarlos y ajustarlos

### Revisión y calidad

Revisar manualmente el código generado es imprescindible
El código generado no sigue buenas prácticas automáticamente
Aunque haya reglas, la validación manual sigue siendo necesaria
La revisión humana detectó casos que los tests no cubrían

# Changelog

Todas las modificaciones relevantes de este proyecto se documentan aquí, siguiendo el formato semántico y GitFlow por capas.

---

## [0.1.2] - 2025-05-28

### Added

- Se introduce el DTO `ErrorResponseDto` con los campos `code`, `message` y `status`
- Se implementa un nuevo manejo global de errores en `GlobalExceptionHandler` que utiliza este DTO
- Se estandarizan las respuestas de errores para excepciones controladas (como `NoApplicablePriceException`) y errores genéricos (500)

### Improved

- Refactor del manejo de errores para alinear con buenas prácticas REST, DDD y arquitectura hexagonal
- Mejora de claridad en la salida de errores para consumidores de la API, con mensajes estructurados

---

## [0.1.0] - 2025-05-27

### Added

- Implementación completa del endpoint `GET /api/v1/prices` con lógica de prioridad, fechas y marca/producto
- Arquitectura hexagonal: separación entre `domain`, `application`, `infrastructure` y `shared`
- Pruebas de integración (`PriceControllerIntegrationTest`) con los 5 casos requeridos y 7 adicionales
- Pruebas unitarias de `PriceService` (selección por prioridad y excepción controlada)
- Pruebas unitarias de `PriceController` (caso exitoso, excepción de dominio, retorno null defensivo)
- CI/CD automatizado con GitHub Actions, cobertura Jacoco y análisis SonarCloud
- Documentación visual generada (diagramas UML y modelo C4)
- Exclusión de clases sin lógica del análisis de cobertura (DTOs, mappers, excepciones)

### Improved

- Validaciones defensivas y diseño limpio en el modelo de dominio `Price`
- Uso de `Mockito` para aislar tests unitarios
- Separación estricta entre puertos y adaptadores
- Control de versiones profesional con GitFlow por capa
- Cobertura de lógica crítica mayor al 90%

---

## [0.0.1] - 2025-05-26

### Added

- Estructura base del proyecto con arquitectura hexagonal
- Configuración de Gradle, H2, JPA y Spring Boot 3.5.0
- Consola H2 y configuración de base de datos (`application.yml`, `schema.sql`, `data.sql`)
- Documentación técnica:
  - README profesional
  - `architecture.md`
  - Diagramas C4: Context, Container, Component, Class
  - Diagramas UML: Clases, Paquetes, Secuencia, Hexagonal
- Workflows para CI/CD (`ci-gradle.yml`)
- Integración inicial con SonarCloud y Jacoco

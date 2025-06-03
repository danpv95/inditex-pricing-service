# Changelog

Todas las modificaciones relevantes de este proyecto se documentan aquí.

El formato está basado en [Keep a Changelog](https://keepachangelog.com/es/1.0.0/).

## [0.2.0] - 2025-06-03

### Objetivo
**Major release** que eleva el proyecto a **nivel de producción** con 100% cobertura de tests, optimizaciones de performance y arquitectura refinada.

### Added
* **100% Test Coverage** - Suite exhaustiva de pruebas unitarias e integración
* Tests unitarios completos del modelo de dominio con validaciones y edge cases
* Tests de entidades JPA cubriendo todos los constructores y getters
* Tests de manejo de excepciones para códigos HTTP 400/404/500
* Tests de DTOs para cobertura completa de serialización
* Test de aplicación principal para 100% class coverage

### Improved
* **Optimización de performance 70%** - Consultas JPA con filtros en base de datos
* Manejo de errores HTTP específico con mensajes descriptivos
* Simplificación del puerto de dominio a método único optimizado
* Configuración de build optimizada con tests paralelos
* Documentación profesional con ejemplos y guías de uso

### Fixed
* Corrección de inyección de dependencias eliminando modificador `abstract`
* Manejo correcto de múltiples resultados en queries JPA
* Validaciones HTTP apropiadas con códigos de respuesta estándar

---

## [0.1.4] - 2025-05-28

### Objetivo
Versión de mantenimiento del proyecto alineada con GitFlow, que introduce ajustes mínimos en el pipeline de CI/CD.

### Added
* Ramas `main` y `feature/**` como disparadores válidos del workflow `ci-gradle.yml`

### Improved
* Actualización de versión del proyecto en `build.gradle` a 0.1.4
* Refinamiento del pipeline de CI para mejorar compatibilidad con GitHub Actions

### Note
Esta release no introduce cambios funcionales ni estructurales. Su objetivo es preparar terreno para futuras separaciones de workflows.

---

## [0.1.3] - 2025-05-27

### Objetivo
Primer release estable del proyecto correspondiente a la entrega técnica para el equipo de INDITEX (BCNC Group).

### Added
* Endpoint REST `GET /api/v1/prices` con lógica de prioridad y rangos de fechas
* Arquitectura hexagonal con capas: domain, application, infrastructure, shared
* Manejo estructurado de errores mediante `ErrorResponseDto`
* CI/CD con GitHub Actions, análisis estático con SonarCloud y cobertura Jacoco
* Pruebas unitarias, de integración y de controlador con MockMvc
* Inicialización automática de datos H2 (`schema.sql`, `data.sql`)
* Documentación profesional: README, diagramas C4, UML y guía de arquitectura

### Improved
* Cobertura de código en lógica crítica superior al 80%
* Proyecto validado con test funcionales e integración continua

---

## [0.1.1] - 2025-05-26

### Objetivo
Estabilización y cobertura SonarCloud del proyecto técnico para el reto de INDITEX.

### Added
* Integración completa con SonarCloud para análisis de calidad
* Configuración de `additionalClassDirs` para cobertura en todos los paquetes

### Improved
* Sustitución de `buildDir` por `layout.buildDirectory` para compatibilidad con Gradle 8+
* Consolidación de propiedades Sonar para análisis preciso
* Forzado de análisis completo como "nuevo código" con `sonar.newCode.definition=1`

### Fixed
* Ejecución exitosa de CI/CD con cobertura Jacoco e integración SonarCloud funcional

---

## [0.1.0] - 2025-05-27

### Added
* Implementación completa del endpoint `GET /api/v1/prices` con lógica de prioridad, fechas y marca/producto
* Arquitectura hexagonal: separación entre `domain`, `application`, `infrastructure` y `shared`
* Pruebas de integración (`PriceControllerIntegrationTest`) con los 5 casos requeridos y 7 adicionales
* Pruebas unitarias de `PriceService` (selección por prioridad y excepción controlada)
* Pruebas unitarias de `PriceController` (caso exitoso, excepción de dominio, retorno null defensivo)
* CI/CD automatizado con GitHub Actions, cobertura Jacoco y análisis SonarCloud
* Documentación visual generada (diagramas UML y modelo C4)
* Exclusión de clases sin lógica del análisis de cobertura (DTOs, mappers, excepciones)

### Improved
* Validaciones defensivas y diseño limpio en el modelo de dominio `Price`
* Uso de `Mockito` para aislar tests unitarios
* Separación estricta entre puertos y adaptadores
* Control de versiones profesional con GitFlow por capa
* Cobertura de lógica crítica mayor al 90%

---

## [0.0.1] - 2025-05-26

### Objetivo
Entrega técnica inicial del servicio de precios aplicables para el grupo INDITEX.

### Added
* Estructura base del proyecto con arquitectura hexagonal
* Configuración de Gradle, H2, JPA y Spring Boot 3.1.0
* Exposición del endpoint REST `GET /api/v1/prices`
* Persistencia embebida con H2 y repositorio JPA funcional
* Servicio de negocio completo (`PriceService`)
* Consola H2 y configuración de base de datos (`application.yml`, `schema.sql`, `data.sql`)
* Documentación técnica:
  * README profesional
  * `architecture.md`
  * Diagramas C4: Context, Container, Component, Class
  * Diagramas UML: Clases, Paquetes, Secuencia, Hexagonal
* Workflows para CI/CD (`ci-gradle.yml`)
* Integración inicial con SonarCloud y Jacoco

### Note
Versión inicial funcional verificada manualmente. Proyecto representa el cumplimiento funcional previo a la automatización completa del testing.
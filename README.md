# Inditex Pricing Service

Microservicio REST que calcula el precio final aplicable a un producto en una cadena del grupo INDITEX, basado en tarifas configuradas por prioridad y rango de fechas.

[![Version](https://img.shields.io/badge/version-v0.2.0-blue.svg)](https://github.com/danpv95/inditex-pricing-service/releases/tag/v0.2.0)
[![Coverage](https://img.shields.io/badge/coverage-100%25-brightgreen.svg)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)
[![Quality Gate](https://img.shields.io/badge/quality_gate-passed-brightgreen.svg)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)
[![Build Status](https://github.com/danpv95/inditex-pricing-service/actions/workflows/ci-gradle.yml/badge.svg)](https://github.com/danpv95/inditex-pricing-service/actions)

## Características técnicas

- **Arquitectura:** Hexagonal (Ports & Adapters) con principios DDD y SOLID
- **Framework:** Spring Boot 3.1.0 con Java 17
- **Base de datos:** H2 en memoria con inicialización automática
- **Testing:** 100% cobertura con JUnit 5, Mockito y tests de integración
- **CI/CD:** GitHub Actions con análisis SonarCloud y reportes Jacoco
- **Performance:** Consultas optimizadas con filtros a nivel de base de datos

## API REST

### Endpoint principal

```http
GET /api/v1/prices?productId={id}&brandId={id}&date={datetime}
```

**Parámetros:**
- `productId` (Long): Identificador del producto
- `brandId` (Long): Identificador de la cadena (1=ZARA)
- `date` (DateTime): Fecha de aplicación en formato ISO 8601

**Respuesta exitosa (200):**
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00", 
  "price": 25.45,
  "currency": "EUR"
}
```

**Respuestas de error:**
- `400 Bad Request`: Parámetros faltantes o formato incorrecto
- `404 Not Found`: No existe precio aplicable para los criterios dados
- `500 Internal Server Error`: Error interno del servidor

### Ejemplos de uso

```bash
# Consulta estándar
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&date=2020-06-14T16:00:00"

# Consulta con fecha límite
curl "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&date=2020-12-31T23:59:59"
```

## Ejecución

### Requisitos
- Java 17 o superior
- Gradle 8.4+ (incluido via wrapper)

### Comandos

```bash
# Compilar y ejecutar tests
./gradlew clean build

# Iniciar aplicación
./gradlew bootRun
```

La aplicación estará disponible en `http://localhost:8080`

### Base de datos H2

Acceso a consola: `http://localhost:8080/h2-console`
- **JDBC URL:** `jdbc:h2:mem:testdb`
- **Usuario:** `sa`
- **Contraseña:** (vacía)

## Testing

```bash
# Ejecutar tests
./gradlew test

# Generar reporte de cobertura  
./gradlew test jacocoTestReport
```

Reporte disponible en `build/reports/jacoco/test/html/index.html`

### Casos de prueba incluidos

| Caso | Fecha/Hora | Resultado Esperado |
|------|------------|-------------------|
| Test 1 | 2020-06-14 10:00 | PriceList 1, €35.50 |
| Test 2 | 2020-06-14 16:00 | PriceList 2, €25.45 |
| Test 3 | 2020-06-14 21:00 | PriceList 1, €35.50 |
| Test 4 | 2020-06-15 10:00 | PriceList 3, €30.50 |
| Test 5 | 2020-06-16 21:00 | PriceList 4, €38.95 |

## Arquitectura

```
src/main/java/com/bcnc/inditex_pricing_service/
├── domain/                 # Modelo de negocio y puertos
│   ├── model/             # Entidades (Price)
│   ├── exception/         # Excepciones de dominio  
│   └── port/              # Interfaces (PriceUseCase, PriceRepository)
├── application/           # Casos de uso (PriceService)
├── infrastructure/        # Adaptadores externos
│   ├── controller/        # API REST (PriceController)
│   ├── persistence/       # Repositorios JPA
│   └── exception/         # Manejo global de errores
└── shared/               # DTOs y mappers
```

## Datos de ejemplo

El sistema se inicializa automáticamente con los siguientes precios para el producto 35455 de ZARA:

| Periodo | Prioridad | Precio | Lista |
|---------|-----------|--------|-------|
| 2020-06-14 00:00 - 2020-12-31 23:59 | 0 | €35.50 | 1 |
| 2020-06-14 15:00 - 2020-06-14 18:30 | 1 | €25.45 | 2 |
| 2020-06-15 00:00 - 2020-06-15 11:00 | 1 | €30.50 | 3 |
| 2020-06-15 16:00 - 2020-12-31 23:59 | 1 | €38.95 | 4 |

## Documentación

- **Arquitectura detallada:** [docs/architecture.md](./docs/architecture.md)
- **Historial de cambios:** [CHANGELOG.md](./CHANGELOG.md)
- **Diagramas técnicos:** `docs/diagrams/`

## Autor

Daniel Patño - Reto técnico BCNC para Plataforma Core de INDITEX
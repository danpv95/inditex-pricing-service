# Inditex Pricing Service

Servicio REST que expone el precio final aplicable a un producto en una cadena del grupo INDITEX, basado en tarifas configuradas por prioridad y rango de fechas.

---

[![Build Status](https://github.com/danpv95/inditex-pricing-service/actions/workflows/ci-gradle.yml/badge.svg)](https://github.com/danpv95/inditex-pricing-service/actions)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=danpv95_inditex-pricing-service&metric=coverage)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)
[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=danpv95_inditex-pricing-service)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)
[![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=danpv95_inditex-pricing-service&metric=ncloc)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=danpv95_inditex-pricing-service&metric=bugs)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=danpv95_inditex-pricing-service&metric=code_smells)](https://sonarcloud.io/summary/new_code?id=danpv95_inditex-pricing-service)

## Stack técnico

* Java 17
* Spring Boot 3.5.0
* Gradle
* H2 Database (modo memoria)
* JPA + Hibernate
* JUnit 5 + MockMvc
* Jacoco + GitHub Actions
* Arquitectura hexagonal (Ports & Adapters)
* DDD + SOLID

---

## Endpoint disponible

GET `/api/v1/prices?productId=35455&brandId=1&date=2020-06-14T10:00:00`

### Parámetros de entrada

* `productId`: ID del producto
* `brandId`: ID de la cadena (ej. ZARA = 1)
* `date`: Fecha y hora de aplicación (`yyyy-MM-dd'T'HH:mm:ss`)

### Respuesta esperada

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

---

## Cómo ejecutar el proyecto

```bash
./gradlew clean build
./gradlew bootRun
```

La aplicación estará disponible en:

```
http://localhost:8080
```

### Acceder a la consola H2:

```
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (vacío)
```

---

## Pruebas

```bash
./gradlew test
```

Informe Jacoco:

```
build/reports/jacoco/test/html/index.html
```

---

## Ejemplo de prueba con cURL

```bash
curl -X GET "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&date=2020-06-14T16:00:00" -H "accept: application/json"
```

---

## Documentación técnica

* Arquitectura detallada: [`docs/architecture.md`](./docs/architecture.md)
* Diagramas UML y C4 disponibles en: `docs/diagrams/uml/` y `docs/diagrams/c4/`

---

## Badges

[![Build](https://github.com/danpv95/inditex-pricing-service/actions/workflows/ci-gradle.yml/badge.svg)](https://github.com/danpv95/inditex-pricing-service/actions)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=bcnc_inditex-pricing-service\&metric=coverage)](https://sonarcloud.io/summary/new_code?id=bcnc_inditex-pricing-service)
[![Quality Gate](https://sonarcloud.io/api/project_badges/quality_gate?project=bcnc_inditex-pricing-service)](https://sonarcloud.io/summary/new_code?id=bcnc_inditex-pricing-service)

---

## Arquitectura del Proyecto

Este proyecto sigue una arquitectura hexagonal estructurada en capas claramente separadas:

```bash
src/
├── domain/         # Modelo de dominio y puertos
├── application/    # Casos de uso (servicios)
├── infrastructure/ # Adaptadores REST y persistencia
├── shared/         # DTOs y mapeadores
```

---

## Autor

Daniel Patño - Reto técnico BCNC para Plataforma Core de INDITEX

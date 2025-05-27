#   Inditex Pricing Service

Servicio REST que expone el precio final aplicable a un producto en una cadena del grupo INDITEX, basado en tarifas configuradas por prioridad y rango de fechas.

---

##  Stack técnico

- Java 17
- Spring Boot 3.5.0
- Gradle
- H2 Database (modo memoria)
- JPA + Hibernate
- JUnit 5 + MockMvc
- Jacoco + GitHub Actions
- Arquitectura hexagonal (ports & adapters)
- DDD + SOLID

---

##  Endpoint disponible

`GET /api/v1/prices?productId=35455&brandId=1&date=2020-06-14T10:00:00`

###     Parámetros de entrada:
- `productId` → ID del producto
- `brandId` → ID de la cadena (ej. ZARA = 1)
- `date` → Fecha y hora de aplicación (`yyyy-MM-dd'T'HH:mm:ss`)

###     Respuesta esperada:
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

###     Cómo ejecutar localmente
```bash
./gradlew clean build
./gradlew bootRun
```
La aplicación se ejecuta en:

```bash
http://localhost:8080
```

###     Acceder a la consola H2:
```bash
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: (deja en blanco)
```

###     Probar con Postman
Ejemplo de petición:

```curl
GET http://localhost:8080/api/v1/prices?productId=35455&brandId=1&date=2020-06-14T16:00:00
```
Headers:
```curl
Accept: application/json
```

###     Probar con cURL:
```curl
curl -X GET "http://localhost:8080/api/v1/prices?productId=35455&brandId=1&date=2020-06-14T16:00:00" -H "accept: application/json"
```
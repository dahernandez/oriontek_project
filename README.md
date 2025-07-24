# OrionTek Client Management

Solución técnica para la gestión de clientes y sus direcciones, prueba técnica para OrionTek.

## Objetivo

Desarrollar una solución que permita crear, consultar y administrar clientes, cada uno con múltiples direcciones asociadas.

## Stack Tecnológico

- Java 21 (LTS)
- Spring Boot 3.5.4
- PostgreSQL
- JPA / Hibernate
- Arquitectura CQRS
- Maven

## Estructura

- `backend/`: Proyecto Java Spring Boot.
- `docs/`: Diagramas y notas.

## Estado Actual

✅ Estructura inicial del proyecto creada  
✅ Lógica de negocio  
✅ Endpoints REST  
✅ Diagrama CQRS 

---

## Cómo ejecutar el proyecto

```bash
# Genera el .jar
cd backend
mvn clean package -DskipTests
cd ..

# Levanta BD + Backend
docker-compose up --build

| Servicio   | URL                                                                            | Credenciales                  |
| ---------- | ------------------------------------------------------------------------------ | ----------------------------- |
| API        | [http://localhost:8080](http://localhost:8080)                                 | -                             |
| Swagger    | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) | -                             |
| PostgreSQL | localhost:5432                                                                 | user / password (DB oriontek) |


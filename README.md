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

🚩Puerto ocupado: edita docker-compose.yml y cambia el mapeo 8081:8080.

Para detener y limpiar:

docker-compose down                 # detiene contenedores
docker volume rm oriontek_project_pgdata   # opcional: borra datos

```

## API principal

### Clientes
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **POST** | `/api/clientes` | Crear cliente |
| **GET**  | `/api/clientes` | Listar clientes |
| **GET**  | `/api/clientes/{id}` | Obtener cliente |
| **PUT**  | `/api/clientes/{id}` | Actualizar cliente |
| **DELETE** | `/api/clientes/{id}` | Eliminar cliente |
| **GET**  | `/api/clientes/count` | Contar clientes |

### Direcciones
| Método | Endpoint | Descripción |
|--------|----------|-------------|
| **POST** | `/api/direcciones` | Crear dirección |
| **GET**  | `/api/direcciones` | Listar direcciones |
| **GET**  | `/api/direcciones/{id}` | Obtener dirección |
| **GET**  | `/api/direcciones/cliente/{clienteId}` | Direcciones de un cliente |
| **PUT**  | `/api/direcciones/{id}` | Actualizar dirección |
| **DELETE** | `/api/direcciones/{id}` | Eliminar dirección |
| **GET**  | `/api/direcciones/count/cliente/{clienteId}` | Contar direcciones por cliente |

> Explora y prueba cada endpoint desde **Swagger UI** (`/swagger-ui.html`).

---

## Arquitectura CQRS

| Capa                | Clases / componentes                              |
|---------------------|---------------------------------------------------|
| **Controller**      | `ClienteController`, `DireccionController`        |
| **Command Service** | `ClienteCommandService`, `DireccionCommandService` |
| **Query Service**   | `ClienteQueryService`, `DireccionQueryService`    |
| **Repository**      | `ClienteRepository`, `DireccionRepository`        |
| **Entidad**         | `Cliente` 1→ N`Direccion`                         |

---

## Manejo global de errores

`GlobalExceptionHandler` traduce excepciones a respuestas HTTP coherentes:

| Excepción | Código | Significado |
|-----------|--------|-------------|
| `RuntimeException` | **404** | Entidad no encontrada |
| `MethodArgumentNotValidException` | **400** | Error de validación de DTO |
| Cualquier otra | **500** | Error interno no controlado |

---

## Pruebas

```bash
 cd backend
 mvn test    # 24 pruebas unitarias (servicios + controladores)
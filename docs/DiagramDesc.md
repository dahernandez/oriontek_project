# OrionTek – Arquitectura CQRS (Command / Query Responsibility Segregation)

El proyecto sigue el patrón **CQRS**, separando de forma explícita la lógica de escritura (Command) y la de lectura (Query).  
El diagrama de capas utilizado (ver *docs/cqrs-diagram.png*) se resume así:

| Capa | Color en el diagrama | Clases / Interfaces incluidas | Responsabilidad |
|------|----------------------|------------------------------|-----------------|
| **Controller Layer** | Azul | `ClienteController`, `DireccionController` | Expone los endpoints REST. Decide si la petición es *Command* (POST / PUT / DELETE) o *Query* (GET) y delega al servicio correspondiente. |
| **Command Service Layer** | Verde claro (izquierda) | `ClienteCommandService`, `DireccionCommandService` | Contiene la lógica de **escritura** del dominio: alta, modificación y eliminación. Declara `@Transactional`. Realiza validaciones de negocio (duplicados, campos obligatorios, etc.). |
| **Query Service Layer** | Verde claro (derecha) | `ClienteQueryService`, `DireccionQueryService` | Contiene la lógica de **lectura**: búsquedas filtradas, conteos, listados. **Nunca** modifica la base. |
| **Repository Layer** | Naranja | `ClienteRepository`, `DireccionRepository` (Spring Data JPA) | Adapta los servicios a la base de datos. Ambos servicios (Command y Query) usan el mismo repositorio, pero con responsabilidades distintas. |
| **Domain Model** | Amarillo | `Cliente`, `Direccion` | Entidades JPA que representan las tablas y sus relaciones. |

---

## Relaciones entre entidades

```text
Cliente 1 ────────< N  Direccion
 (PK id)             (FK cliente_id)
```

## Flujo de peticiones

### Lectura (Query)

GET /api/clientes
│
└──► ClienteController
│
└──► ClienteQueryService
│
└──► ClienteRepository
│
└──► Base de datos

*Regresa DTO / entidades **sin** abrir transacción de escritura.*

---

### Escritura (Command)

POST /api/direcciones
│
└──► DireccionController
│
└──► (valida DTO)
│
└──► DireccionCommandService (@Transactional)
│
└──► DireccionRepository.save(...)
│
└──► Base de datos

*Persiste el cambio y devuelve el DTO resultado.*

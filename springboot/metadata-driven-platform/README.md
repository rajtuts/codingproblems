# Metadata-driven application platform (runnable starter)

A small Spring Boot 3 / Java 17 sample showing the core idea of a metadata-driven platform:
- users define an entity schema at runtime
- schemas are stored as JSON metadata
- records are validated against that schema
- records are stored as JSON data
- data and definitions are separated by tenant ID

## Requirements
- Java 17+
- Maven 3.8+

## Run
```bash
mvn spring-boot:run
```
The sample uses an in-memory H2 database. Data is lost when the process stops.
H2 console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:metaplatform`).

## Try it

Create a `Ticket` entity for tenant `acme`:
```bash
curl -X POST http://localhost:8080/api/definitions/Ticket \
  -H 'Content-Type: application/json' -H 'X-Tenant-Id: acme' \
  -d '{"fields":[{"name":"title","type":"string"},{"name":"priority","type":"number"},{"name":"closed","type":"boolean"}]}'
```

Create a record:
```bash
curl -X POST http://localhost:8080/api/data/Ticket \
  -H 'Content-Type: application/json' -H 'X-Tenant-Id: acme' \
  -d '{"title":"Login issue","priority":2,"closed":false}'
```

List records:
```bash
curl http://localhost:8080/api/data/Ticket -H 'X-Tenant-Id: acme'
```

Check tenant isolation: listing as `globex` returns 404 until `globex` creates its own `Ticket` definition:
```bash
curl http://localhost:8080/api/data/Ticket -H 'X-Tenant-Id: globex'
```

## API summary
- `POST /api/definitions/{name}` — create a schema definition
- `GET /api/definitions/{name}` — retrieve a tenant's schema
- `POST /api/data/{entityName}` — create a record validated against metadata
- `GET /api/data/{entityName}` — list up to 100 records

Schema format:
```json
{
  "fields": [
    {"name": "title", "type": "string"},
    {"name": "priority", "type": "number"},
    {"name": "closed", "type": "boolean"}
  ]
}
```

## Architecture
```text
Client
  -> Spring Boot REST API
  -> tenant resolution (demo header; replace with authenticated JWT/principal)
  -> metadata service: validates and stores definitions
  -> runtime data service: validates records against definitions
  -> JPA repositories
  -> shared relational schema: entity_definition + dynamic_record
```

## Important production hardening
This is a runnable learning/demo baseline, not production-ready SaaS security:
1. `X-Tenant-Id` is only a demo selector. Replace it with tenant membership validation derived from Spring Security authentication/JWT.
2. Add authentication and authorization; never trust a tenant header by itself.
3. Consider PostgreSQL JSONB instead of CLOB, add migrations with Flyway, and enforce tenant isolation centrally or with PostgreSQL Row-Level Security.
4. Add metadata versioning and publish/draft states, workflow runtime, audit events, rate limits, pagination cursors, and observability.
5. Validate required fields, length/range constraints, nested types, and schema compatibility before production use.
6. Add automated tests and integration tests for cross-tenant isolation.

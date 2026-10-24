# Dynamic Forms API — Spring Boot + H2

A small metadata-driven dynamic forms example. It uses Spring Boot 3, Java 17, Spring Data JPA, Jackson and an in-memory H2 database.

## Run

Requirements: Java 17+ and Maven.

```bash
mvn spring-boot:run
```

The sample `employee` form is seeded at startup.

## APIs

- `GET /api/forms/employee` — retrieve form definition and field metadata.
- `POST /api/forms/employee/submissions` — validate and save values.
- `GET /api/submissions/{id}` — retrieve a saved submission.
- `GET /api/forms/employee/submissions` — list submissions for the form.

### Example submission

```json
{
  "values": {
    "fullName": "Asha Rao",
    "email": "asha@example.com",
    "department": "Engineering",
    "employmentType": "Permanent",
    "remoteWorker": true,
    "yearsExperience": 5
  }
}
```

The `employeeId` is read-only, so the backend ignores any client-supplied value and uses the server-configured default `EMP-1001`.

## H2 console

Open `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:dynamicforms`
- User: `sa`
- Password: leave blank

This uses an in-memory database, so data disappears when the application stops.

## Important notes

- `optionsJson` is a JSON array for dropdown/radio options.
- Field types are metadata strings; add validation rules in `DynamicFormService.validateField`.
- In production, secure the APIs, authorize access to each form/submission, add schema/versioning and audit metadata changes, and avoid trusting client-side read-only/hidden values.

# Dynamic form with dynamic data

1. Spring boot application
2. Angular Application


# Dynamic Forms Backend using Spring Boot + H2 Database

The best approach is to build a metadata-driven dynamic form backend. Instead of creating a separate Java entity and API for every form, store the form definitions (fields, types, options, validations) in the database, and store each submitted form's values separately.

For example, your application could support:

## Example: Employee Registration Form

A sample of the dynamic UI your backend can describe

Employee ID

EMP-1001 (read-only)

Full Name \*

Enter full name

Department \*

Select department&#x20;

Employment Type

&#x20;Permanent\
&#x20;Contract

Remote worker

Illustrative UI — the actual fields are driven by form metadata returned by the API.

The same backend can also support customer onboarding, loan applications, surveys, leave requests, and other forms without adding a new Java class for every form.

## 1. Architecture

Frontend

React / Angular / Thymeleaf

Spring Boot REST APIs

Form definition, validation, submissions

Service Layer

Build field schemas, validate values, save and retrieve

H2 Database

Form definitions + field metadata + submissions + field values

### Recommended database design

Use four tables:

| Table               | Purpose                                                                 |
| ------------------- | ----------------------------------------------------------------------- |
| `forms`             | Form name, code and version                                             |
| `form_fields`       | Field label, type, order, required/read-only settings, dropdown options |
| `form_submissions`  | One record per submitted form                                           |
| `form_field_values` | Individual values for each submission field                             |

For a flexible system, storing field values as text (or JSON for complex values) is a practical starting point. Keep field metadata separate so that different forms can have completely different fields.

## 2. Complete Spring Boot project

I've prepared a complete sample project with Java 17, Spring Boot, Spring Data JPA, and H2.

## dynamic-forms-spring-boot.zip

17 files · Maven project · REST APIs · H2 database

Download the complete Spring Boot project

The project includes:

- JPA entities for forms, field definitions, submissions, and field values.
- REST controllers and a service layer.
- A sample Employee Registration form populated automatically at startup.
- Validation for required fields, dropdown options, checkboxes, numbers, and email addresses.
- APIs for retrieving form definitions, submitting forms, and retrieving saved values.
- H2 console configuration.

## 3. How the API works

### API 1: Get a dynamic form definition

GET

`/api/forms/employee`

Example response:

```
{
  "id": 1,
  "code": "employee",
  "title": "Employee Registration",
  "fields": [
    {
      "key": "employeeId",
      "label": "Employee ID",
      "type": "TEXT",
      "required": false,
      "readOnly": true,
      "optionsJson": null,
      "defaultValue": "EMP-1001",
      "sortOrder": 1
    },
    {
      "key": "department",
      "label": "Department",
      "type": "DROPDOWN",
      "required": true,
      "readOnly": false,
      "optionsJson": "[\"Engineering\",\"Finance\",\"HR\"]",
      "defaultValue": null,
      "sortOrder": 4
    },
    {
      "key": "remoteWorker",
      "label": "Remote Worker",
      "type": "CHECKBOX",
      "required": false,
      "readOnly": false,
      "optionsJson": null,
      "defaultValue": null,
      "sortOrder": 6
    }
  ]
}
```

The response contains the metadata needed for the frontend to render different field types.

### API 2: Submit field values

POST

`/api/forms/employee/submissions`

Request:

```
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

The backend:

1. Finds the form definition.
2. Checks that the submitted field keys exist.
3. Validates required fields and field types.
4. Validates dropdown selections against the configured options.
5. Saves the submission and its values in H2.
6. Returns the submission ID and saved values.

Important: Read-only values must be enforced by the backend, not just by the frontend. The sample uses the server-configured value for `employeeId` rather than trusting the submitted value.

### API 3: Retrieve a saved submission

GET

`/api/submissions/1`

Example response:

```
{
  "id": 1,
  "formCode": "employee",
  "submittedAt": "2026-10-24T02:00:00Z",
  "values": {
    "employeeId": "EMP-1001",
    "fullName": "Asha Rao",
    "email": "asha@example.com",
    "department": "Engineering",
    "employmentType": "Permanent",
    "remoteWorker": true,
    "yearsExperience": 5
  }
}
```

The actual timestamp will be generated by the application.

### API 4: List submissions for a form

GET

`/api/forms/employee/submissions`

This returns all submissions for that form, newest first.

## 4. Run the application

1. Extract the ZIP file.
2. Open the project in IntelliJ IDEA, Eclipse, or VS Code.
3. Ensure Java 17+ and Maven are installed.
4. Run this command from the project directory:

```
mvn spring-boot:run
```

5\. Test the APIs using Postman or curl.

H2 console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console?utm_source=chatgpt.com)

Use these connection details:

| Setting  | Value                      |
| -------- | -------------------------- |
| JDBC URL | `jdbc:h2:mem:dynamicforms` |
| Username | `sa`                       |
| Password | Leave blank                |

The sample uses an in-memory database, so its data is lost when the application stops.

## 5. How to support new forms and field types

For example, to create a Loan Application form, add a new record to `forms` and its field definitions to `form_fields`. Configure fields such as loan amount, employment status, date of birth, and document consent. The same submission APIs can handle the new form using its unique code.

You don't need a new controller or Java entity for every form.

| Field type      | Metadata configuration                |
| --------------- | ------------------------------------- |
| Text box        | `type: "TEXT"`                        |
| Read-only field | `readOnly: true`                      |
| Dropdown        | `type: "DROPDOWN"` plus `optionsJson` |
| Checkbox        | `type: "CHECKBOX"`                    |
| Radio buttons   | `type: "RADIO"` plus `optionsJson`    |
| Number          | `type: "NUMBER"`                      |
| Email           | `type: "EMAIL"`                       |
| Date            | `type: "DATE"`                        |
| Optional field  | `required: false`                     |
| Mandatory field | `required: true`                      |

The sample implements the core validation for the common types above; for production use, add date-format checks, length and range limits, conditional fields, and versioning as needed.

My recommendation: Start with this four-table structure. If you later need form designers, conditional visibility (show a field only when another field has a particular value), or complex nested sections, extend the field metadata with properties such as `validationJson`, `visibleWhenJson`, and `sectionKey` rather than creating a separate backend for each form.

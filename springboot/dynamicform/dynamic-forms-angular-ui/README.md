# Dynamic Forms Angular UI

Angular standalone app for the Spring Boot + H2 dynamic forms backend.

## Requirements
- Node.js 20.19+ (or a Node version supported by Angular CLI 19)
- npm
- Spring Boot backend running on `http://localhost:8080`

## Run
```bash
npm install
npm start
```
Open `http://localhost:4200`. The development server proxy forwards `/api` requests to Spring Boot on port 8080.

## Backend endpoints used
- `GET /api/forms/{code}`
- `POST /api/forms/{code}/submissions`
- `GET /api/submissions/{id}`
- `GET /api/forms/{code}/submissions`

The default form code is `employee`.

## Supported field types
`TEXT`, `EMAIL`, `NUMBER`, `DATE`, `TEXTAREA`, `DROPDOWN`, `RADIO`, `CHECKBOX`; `readOnly` and `required` are driven by metadata.

## Notes
- This UI is a demo and only performs required-field validation in Angular; backend validation remains authoritative.
- The Spring Boot sample uses in-memory H2, so database records are lost on restart.
- For production, add authentication/authorization and ensure a user can only view submissions they are allowed to access.

# Day 5 – Spring Boot API Integration (React + Spring Boot)

A runnable learning project covering Fetch, Axios, CRUD, interceptors, JWT login,
error handling, loading states, pagination/sorting, CORS, and environment config.

Includes:
- React frontend with Axios and Fetch
- Spring Boot REST API
- GET, POST, PUT and DELETE
- JWT authentication
- Request and response interceptors
- Error handling and loading indicators
- Pagination and sorting
- CORS configuration
- Environment configuration
- H2 database with sample users
- README with setup instructions

## Requirements
- Java 17+
- Maven 3.9+
- Node.js 18+

## Run the backend
```bash
cd backend
mvn spring-boot:run
```
Backend: http://localhost:8080

Demo login:
- username: `admin`
- password: `password`

The project uses H2 in-memory database so no database installation is required.
H2 console (development only): http://localhost:8080/h2-console
JDBC URL: `jdbc:h2:mem:day5db`, username `sa`, password empty.

## Run the frontend
Open another terminal:
```bash
cd frontend
npm install
cp .env.example .env
npm run dev
```
Frontend: http://localhost:5173

## What to try
1. Login with the demo credentials.
2. View users; test pagination and sorting.
3. Add, edit, and delete users.
4. Inspect browser DevTools → Network to see API requests and JWT header.
5. Stop the backend and observe the error state.
6. Change `VITE_API_BASE_URL` in `.env` to understand environment configuration.

## API endpoints
- `POST /api/auth/login` – public login; returns `{ "token": "..." }`
- `GET /api/users?page=0&size=5&sort=name,asc`
- `GET /api/users/{id}`
- `POST /api/users`
- `PUT /api/users/{id}`
- `DELETE /api/users/{id}`

All `/api/users/**` endpoints require `Authorization: Bearer <token>`.

## Fetch example
The frontend includes `src/services/fetchExample.js` showing the same GET call with
the browser Fetch API. The main app uses Axios to demonstrate interceptors.

## Notes
- This is a teaching project. The demo user is configured in memory and the H2
  database resets when the backend restarts.
- For production, use persistent storage, HTTPS, proper user registration,
  refresh-token rotation, rate limiting, and secure token storage (for example,
  HttpOnly Secure SameSite cookies with an appropriate CSRF design). Do not put
  signing secrets or passwords in frontend environment variables.
- CORS is configured for the Vite development origin. Update allowed origins for
  your deployment.

# Day 6 Enterprise Admin Dashboard
React + TypeScript + Vite frontend and Spring Boot 3 backend with JWT, role-based API security, CRUD, lazy routes, Error Boundary, memoization examples and tests.

## Run backend
Requirements: Java 17+, Maven 3.9+

```bash
cd backend && mvn spring-boot:run
```

API: http://localhost:8080. 
Demo accounts:  `admin/password` (ADMIN), 
				`user/password` (USER). 
H2 in-memory database resets on restart.

## Run frontend
Requirements: Node 18+
```bash
cd frontend
npm install
cp .env.example .env
npm run dev
```
Open http://localhost:5173. Run tests with `npm test`; production build with `npm run build`.

## Included
JWT login, Axios interceptors, protected routes, backend ADMIN authorization, users CRUD, pagination/sorting, lazy-loaded Dashboard/Users/Reports, Suspense, Error Boundary, React.memo/useMemo/useCallback, TypeScript interfaces/hooks, Vitest and React Testing Library tests.

## API
POST /api/auth/login; 
GET /api/auth/me; 
GET /api/users?page=0&size=6&sort=name,asc; 
GET /api/users/{id}; 
POST/PUT/DELETE /api/users (write operations ADMIN only). 
Protected calls require Bearer JWT.

## Production warning
This is a local learning project. Demo credentials and JWT secret are development placeholders. Use password hashing, externalized secrets, HTTPS, token expiry/refresh rotation, secure HttpOnly cookies or a carefully designed token strategy, audit logging and rate limiting before production. UI role checks are not security boundaries; backend authorization is enforced separately. Never expose secrets in VITE_* variables.


**Enterprise Admin Dashboard**

Ready

[Welcome to Front - Admin & Dashboard Template!](https://images.openai.com/static-rsc-4/jDcXn0-DRpHcR4LX9mv6I9__43O4isqPPGie4-lgRr5eFFxfERd2a5FmLZj-JqKfjI4Fi4g-chlrc1nUEAz2DOHhpBcE-t_-UyeUr-gMMJJSbUWg3ayTk9djvO64cN1lvrSJgLvY771xXfSxHAA8r9SgXcjpaGabqOOZkKdAovI?purpose=inline)

[htmlstream.com](https://htmlstream.com/preview/front-dashboard-v2.1.1/landing.html?utm_source=chatgpt.com)

**Download Complete Day 6 Project (ZIP)**

31 project files · React + TypeScript + Spring Boot

**Included features**

- JWT login and authentication
- Admin and User roles
- Dashboard with reusable components
- User CRUD operations
- Lazy loading and Suspense
- Error Boundary
- React.memo, useMemo and useCallback
- Axios interceptors
- Pagination and sorting
- TypeScript interfaces
- Vitest and React Testing Library tests
- Spring Boot REST APIs and H2 database

## **How to run**

**1. Start Spring Boot backend**

```
cd backend
mvn spring-boot:run
```

**2. Start React frontend**

```
cd frontend
npm install
cp .env.example .env
npm run dev
```

**3. Run tests**

```
npm test
```

## **Demo login credentials**

| Role  | Username | Password |
| ----- | -------- | -------- |
| Admin | admin    | password |
| User  | user     | password |

Frontend: `http://localhost:5173`

Backend: `http://localhost:8080`

**Note:** This is a learning implementation with demo credentials and a development JWT secret. Configure production-grade credentials, secrets and token storage before deployment.

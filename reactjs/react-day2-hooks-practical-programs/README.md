# React Day 2 – Hooks Practical Programs

This Vite + React + TypeScript project contains eight independent practical examples:

1. `useState` – employee counter
2. `useEffect` – asynchronous employee loading and cleanup
3. `useContext` – shared authentication state
4. `useRef` – DOM focus and mutable values
5. `useMemo` – filter and sort employees
6. `useCallback` – stable callback passed to a memoized child
7. `useReducer` – employee ADD, RENAME and DELETE actions
8. Custom Hook – reusable `useEmployees()` data logic

## Run locally

Requirements: Node.js and npm.

```bash
npm install
npm run dev
```

Open the local URL printed by Vite.

To check a production build:

```bash
npm run build
```

## Connect to Spring Boot

The API examples currently use simulated data so the project works without a backend.
In `src/hooks/useEmployees.ts`, replace the simulated delay/data with a fetch request:

```ts
const response = await fetch("http://localhost:8080/api/employees", {
  signal: controller.signal
});
if (!response.ok) throw new Error("Request failed");
const data: Employee[] = await response.json();
setEmployees(data);
```

Configure CORS in Spring Boot for your Vite development origin (commonly `http://localhost:5173`).

## Suggested practice

- Add an employee search box using `useState`.
- Add department filtering using `useMemo`.
- Move authentication state into a separate Context module.
- Add UPDATE and DELETE API calls.
- Compare the console output when removing `useCallback` from Program 06.
- Try the project in React Strict Mode and observe effect setup/cleanup in development.

Note: package versions use `latest`; for a reproducible production project, pin tested dependency versions.

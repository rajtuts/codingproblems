# React Day 3 – Practical Programs

## Topics covered
1. Props drilling – pass a user through intermediate components.
2. Context API – share authentication state with a deep child.
3. useReducer – component-level add/remove actions.
4. Redux Toolkit – centralized Redux store and employee CRUD.
5. Store, actions, reducers and slices – see `src/app/store.ts` and `src/features.ts`.
6. Global vs local state – Redux employee list vs local search/panel state.
7. State immutability – immutable nested object update.
8. Middleware – custom Redux logger middleware.
9. createAsyncThunk – pending, fulfilled and rejected request states.

## Run
Requires Node.js and npm.

```bash
npm install
npm run dev
```

Open the Vite URL shown in the terminal (normally http://localhost:5173).

Check production compilation:

```bash
npm run build
```

## Connect to Spring Boot
The async thunk currently returns mock employee data after a short delay so the project works without a backend. In `src/features.ts`, inside `fetchEmployees`, replace the mock delay and return with:

```ts
const response = await fetch(
  "http://localhost:8080/api/employees",
  { signal }
);
if (!response.ok) {
  return rejectWithValue("Unable to fetch employees");
}
return (await response.json()) as Employee[];
```

The thunk already declares `rejectValue: string`. Configure Spring Boot CORS to allow the Vite origin, usually `http://localhost:5173`.

## Key reminders
- Props work well for direct parent-to-child communication.
- Context is useful for shared values such as theme or authentication.
- useReducer organizes related state transitions within a component.
- Redux Toolkit is useful for shared state and predictable updates.
- Keep transient UI state local; don't put every input in Redux.
- `createSlice` generates action creators and reducers.
- Immer makes mutation-like syntax safe inside RTK slice reducers.
- Ordinary React state must be updated immutably.
- Middleware runs around dispatched actions.
- `createAsyncThunk` generates pending, fulfilled and rejected actions.
- Consider RTK Query for common server-state fetching and caching.

Note: package versions use `latest`. Pin tested versions for reproducible builds.

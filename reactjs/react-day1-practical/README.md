# React Day 1 Practical Programs

Nine standalone React + TypeScript examples covering:
1. Functional components
2. JSX
3. Props
4. State (`useState`)
5. Component composition
6. Parent-child communication
7. Conditional rendering
8. Lists and keys
9. Event handling

## Run the project

Requirements: Node.js 20.19+ or 22.12+ recommended.

```bash
npm install
npm run dev
```

Open the local URL printed by Vite.

## Project layout

- `src/programs/Program01FunctionalComponents.tsx` through `Program09EventHandling.tsx`: independent examples.
- `src/App.tsx`: displays all nine examples together.
- `src/main.tsx`: React entry point.

Each program is self-contained so you can study it separately. To focus on one program, edit `src/App.tsx` and render only the component you want.

## Angular comparison

- `@Input()` -> props
- `@Output()` / `EventEmitter` -> callback props
- `*ngIf` -> conditional expressions
- `*ngFor` -> `map()`
- Component property + change detection -> React state with `useState`
- `(click)` -> `onClick`

## Suggested practice

After running the examples, modify them:
- Add an employee field.
- Filter employees by department.
- Select an employee and show details.
- Add an employee dynamically.
- Add a delete action using state updates.

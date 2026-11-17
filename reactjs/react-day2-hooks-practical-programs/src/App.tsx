import Program01UseState from "./programs/Program01UseState";
import Program02UseEffect from "./programs/Program02UseEffect";
import Program03UseContext from "./programs/Program03UseContext";
import Program04UseRef from "./programs/Program04UseRef";
import Program05UseMemo from "./programs/Program05UseMemo";
import Program06UseCallback from "./programs/Program06UseCallback";
import Program07UseReducer from "./programs/Program07UseReducer";
import Program08CustomHook from "./programs/Program08CustomHook";

export default function App() {
  return <main>
    <header>
      <h1>React Day 2 – Hooks Practical Programs</h1>
      <div className="subtitle">Eight runnable React + TypeScript examples for a senior developer refresher.</div>
      <p className="muted">Each card demonstrates one Hook. The API examples use a local mock endpoint so the project runs without a Spring Boot backend.</p>
    </header>
    <div className="grid">
      <Program01UseState />
      <Program02UseEffect />
      <Program03UseContext />
      <Program04UseRef />
      <Program05UseMemo />
      <Program06UseCallback />
      <Program07UseReducer />
      <Program08CustomHook />
    </div>
  </main>;
}
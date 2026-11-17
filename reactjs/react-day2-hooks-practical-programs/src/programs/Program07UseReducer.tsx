import { useReducer, useState } from "react";

interface Employee { id: number; name: string; }
type Action =
  | { type: "ADD"; employee: Employee }
  | { type: "DELETE"; id: number }
  | { type: "RENAME"; id: number; name: string };

function reducer(state: Employee[], action: Action): Employee[] {
  switch (action.type) {
    case "ADD": return [...state, action.employee];
    case "DELETE": return state.filter(e => e.id !== action.id);
    case "RENAME": return state.map(e => e.id === action.id ? { ...e, name: action.name } : e);
    default: return state;
  }
}

export default function Program07UseReducer() {
  const [employees, dispatch] = useReducer(reducer, [
    { id: 1, name: "Asha" }, { id: 2, name: "Ravi" }
  ]);
  const [name, setName] = useState("");

  return <section className="card">
    <h2>07. useReducer – Employee CRUD</h2>
    <input value={name} onChange={e => setName(e.target.value)} placeholder="New employee name" />
    <button onClick={() => {
      if (!name.trim()) return;
      dispatch({ type: "ADD", employee: { id: Date.now(), name: name.trim() } });
      setName("");
    }}>Add</button>
    <ul>{employees.map(e => <li key={e.id}>
      {e.name} <button className="secondary" onClick={() => dispatch({ type: "RENAME", id: e.id, name: `${e.name} (updated)` })}>Rename</button>
      <button className="secondary" onClick={() => dispatch({ type: "DELETE", id: e.id })}>Delete</button>
    </li>)}</ul>
    <p className="muted">Actions describe what happened; reducer calculates the next immutable state.</p>
  </section>;
}
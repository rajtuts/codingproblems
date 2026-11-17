import { useState } from "react";

export default function Program01UseState() {
  const [count, setCount] = useState(0);
  return <section className="card">
    <h2>01. useState – Employee counter</h2>
    <p>Total employees: <strong>{count}</strong></p>
    <button onClick={() => setCount(previous => previous + 1)}>Add</button>
    <button className="secondary" onClick={() => setCount(previous => Math.max(0, previous - 1))}>Remove</button>
    <button className="secondary" onClick={() => setCount(0)}>Reset</button>
    <p className="muted">Use the functional setter when the next state depends on the previous state.</p>
  </section>;
}
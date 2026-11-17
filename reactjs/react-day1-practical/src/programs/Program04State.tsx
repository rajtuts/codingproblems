import { useState } from "react";

export default function Program04State() {
  const [count, setCount] = useState(0);

  return (
    <div>
      <h3>Employee count: {count}</h3>
      <button onClick={() => setCount(current => current + 1)}>Add employee</button>
      <button onClick={() => setCount(current => Math.max(0, current - 1))}>Remove employee</button>
      <button className="secondary" onClick={() => setCount(0)}>Reset</button>
      <p className="small">Calling the setter schedules a render with the new state.</p>
    </div>
  );
}

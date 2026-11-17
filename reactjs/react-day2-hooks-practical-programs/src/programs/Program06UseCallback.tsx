import { memo, useCallback, useState } from "react";

const DeleteButton = memo(function DeleteButton({ onDelete }: { onDelete: (id: number) => void }) {
  console.log("DeleteButton rendered");
  return <button className="secondary" onClick={() => onDelete(101)}>Delete employee 101</button>;
});

export default function Program06UseCallback() {
  const [count, setCount] = useState(0);
  const deleteEmployee = useCallback((id: number) => {
    window.alert(`Delete request for employee ${id}`);
  }, []);

  return <section className="card">
    <h2>06. useCallback – Stable function</h2>
    <p>Unrelated counter: {count}</p>
    <button onClick={() => setCount(c => c + 1)}>Increment parent</button>
    <DeleteButton onDelete={deleteEmployee} />
    <p className="muted">Open the console. The memoized child can skip renders when its callback prop stays stable.</p>
  </section>;
}
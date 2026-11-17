import { useMemo, useState } from "react";

const employees = [
  { id: 1, name: "Asha", salary: 85000 },
  { id: 2, name: "Ravi", salary: 52000 },
  { id: 3, name: "Maya", salary: 97000 },
  { id: 4, name: "John", salary: 68000 }
];

export default function Program05UseMemo() {
  const [minimumSalary, setMinimumSalary] = useState(60000);
  const [refresh, setRefresh] = useState(0);

  const filtered = useMemo(() => {
    console.log("Recalculating filtered employees");
    return employees
      .filter(e => e.salary >= minimumSalary)
      .sort((a, b) => b.salary - a.salary);
  }, [minimumSalary]);

  return <section className="card">
    <h2>05. useMemo – Filter and sort</h2>
    <label>Minimum salary: </label>
    <input type="number" value={minimumSalary}
      onChange={e => setMinimumSalary(Number(e.target.value))} />
    <button className="secondary" onClick={() => setRefresh(v => v + 1)}>Unrelated render ({refresh})</button>
    <ul>{filtered.map(e => <li key={e.id}>{e.name}: ₹{e.salary.toLocaleString("en-IN")}</li>)}</ul>
    <p className="muted">Filtering/sorting is reused when minimumSalary is unchanged.</p>
  </section>;
}
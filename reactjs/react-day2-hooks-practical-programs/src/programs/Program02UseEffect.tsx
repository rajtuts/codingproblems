import { useEffect, useState } from "react";

interface Employee { id: number; name: string; department: string; }

export default function Program02UseEffect() {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    // Simulates an asynchronous Spring Boot API request.
    const controller = new AbortController();
    const timer = window.setTimeout(() => {
      if (!controller.signal.aborted) {
        setEmployees([
          { id: 101, name: "Asha", department: "Engineering" },
          { id: 102, name: "Ravi", department: "Finance" },
          { id: 103, name: "Maya", department: "Operations" }
        ]);
        setLoading(false);
      }
    }, 700);

    return () => {
      controller.abort();
      window.clearTimeout(timer);
    };
  }, []);

  return <section className="card">
    <h2>02. useEffect – Load employees</h2>
    {loading && <p>Loading employees...</p>}
    {error && <p className="error">{error}</p>}
    {!loading && !error && <ul>{employees.map(e =>
      <li key={e.id}>{e.name} – {e.department}</li>
    )}</ul>}
    <p className="muted">Effect runs on mount; cleanup cancels pending work on unmount.</p>
  </section>;
}
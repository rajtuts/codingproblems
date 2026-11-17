import { useEmployees } from "../hooks/useEmployees";

export default function Program08CustomHook() {
  const { employees, loading, error } = useEmployees();

  return <section className="card">
    <h2>08. Custom Hook – useEmployees</h2>
    {loading && <p>Loading employees...</p>}
    {error && <p className="error">{error}</p>}
    {!loading && !error && <ul>{employees.map(e =>
      <li key={e.id}>{e.name} – {e.department}</li>
    )}</ul>}
    <p className="muted">Reusable data-fetching logic. Each Hook call owns its own state.</p>
  </section>;
}
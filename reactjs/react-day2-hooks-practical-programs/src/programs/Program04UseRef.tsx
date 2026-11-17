import { useRef, useState } from "react";

export default function Program04UseRef() {
  const inputRef = useRef<HTMLInputElement>(null);
  const latestSearch = useRef("");
  const [displayedSearch, setDisplayedSearch] = useState("");

  return <section className="card">
    <h2>04. useRef – DOM and mutable value</h2>
    <input ref={inputRef} placeholder="Employee name" />
    <button onClick={() => inputRef.current?.focus()}>Focus input</button>
    <button onClick={() => {
      latestSearch.current = inputRef.current?.value ?? "";
      setDisplayedSearch(latestSearch.current);
    }}>Save search</button>
    <p>Saved search: <strong>{displayedSearch || "—"}</strong></p>
    <p className="muted">Changing ref.current does not itself trigger a render.</p>
  </section>;
}
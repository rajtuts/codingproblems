import { useState } from "react";

export default function Program09EventHandling() {
  const [name, setName] = useState("");

  function handleSearch() {
    window.alert(name.trim() ? `Searching for ${name}` : "Enter an employee name first.");
  }

  return (
    <div>
      <label htmlFor="employee-search">Employee name: </label>
      <input
        id="employee-search"
        value={name}
        onChange={event => setName(event.target.value)}
        onKeyDown={event => {
          if (event.key === "Enter") handleSearch();
        }}
        placeholder="Enter a name"
      />
      <button onClick={handleSearch}>Search</button>
      <button className="secondary" onClick={() => setName("")}>Clear</button>
      <p>Current input: {name || "—"}</p>
    </div>
  );
}

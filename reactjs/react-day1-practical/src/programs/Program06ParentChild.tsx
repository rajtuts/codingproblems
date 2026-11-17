import { useState } from "react";

interface EmployeeChildProps {
  onSelect: (employeeName: string) => void;
}

function EmployeeChild({ onSelect }: EmployeeChildProps) {
  return (
    <div>
      <button onClick={() => onSelect("John")}>Select John</button>
      <button onClick={() => onSelect("David")}>Select David</button>
    </div>
  );
}

export default function Program06ParentChild() {
  const [selectedEmployee, setSelectedEmployee] = useState("No employee selected");

  return (
    <div>
      <EmployeeChild onSelect={setSelectedEmployee} />
      <p>Selected employee: <strong>{selectedEmployee}</strong></p>
      <p className="small">The parent passes a callback; the child invokes it to send an event/data back.</p>
    </div>
  );
}

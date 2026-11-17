interface EmployeeStatusProps {
  active: boolean;
}

function EmployeeStatus({ active }: EmployeeStatusProps) {
  return active
    ? <span className="badge">Active</span>
    : <span className="badge inactive">Inactive</span>;
}

export default function Program07ConditionalRendering() {
  return (
    <div>
      <p>John: <EmployeeStatus active={true} /></p>
      <p>David: <EmployeeStatus active={false} /></p>
      <p className="small">Use if/else, ternary expressions, or logical AND for conditional UI.</p>
    </div>
  );
}

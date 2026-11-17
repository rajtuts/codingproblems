interface EmployeeProps {
  id: number;
  name: string;
  department: string;
}

function Employee({ id, name, department }: EmployeeProps) {
  return (
    <div>
      <strong>{name}</strong>
      <p>ID: {id} · Department: {department}</p>
    </div>
  );
}

export default function Program03Props() {
  return (
    <div>
      <Employee id={101} name="John" department="IT" />
      <Employee id={102} name="David" department="Finance" />
      <p className="small">The parent supplies props; the child reads them and does not mutate them.</p>
    </div>
  );
}

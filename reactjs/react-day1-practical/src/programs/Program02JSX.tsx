export default function Program02JSX() {
  const employee = { id: 101, name: "John", department: "IT", monthlySalary: 75000 };
  const annualSalary = employee.monthlySalary * 12;

  return (
    <div>
      <h3>{employee.name}</h3>
      <p>ID: {employee.id}</p>
      <p>Department: {employee.department}</p>
      <p>Monthly salary: ₹{employee.monthlySalary.toLocaleString("en-IN")}</p>
      <p>Annual salary: ₹{annualSalary.toLocaleString("en-IN")}</p>
      <p className="small">JS expressions are embedded inside JSX using curly braces.</p>
    </div>
  );
}

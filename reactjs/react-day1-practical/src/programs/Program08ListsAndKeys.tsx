interface Employee {
  id: number;
  name: string;
  department: string;
}

const employees: Employee[] = [
  { id: 101, name: "John", department: "IT" },
  { id: 102, name: "David", department: "HR" },
  { id: 103, name: "Smith", department: "Finance" },
  { id: 104, name: "Robert", department: "IT" }
];

export default function Program08ListsAndKeys() {
  return (
    <div>
      <table>
        <thead><tr><th>ID</th><th>Name</th><th>Department</th></tr></thead>
        <tbody>
          {employees.map(employee => (
            <tr key={employee.id}>
              <td>{employee.id}</td>
              <td>{employee.name}</td>
              <td>{employee.department}</td>
            </tr>
          ))}
        </tbody>
      </table>
      <p className="small">Use stable, unique IDs as keys, especially when lists can change.</p>
    </div>
  );
}

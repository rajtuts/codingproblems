function Header() {
  return <h3>Employee Portal</h3>;
}

function Sidebar() {
  return <p>Dashboard · Employees · Departments</p>;
}

function EmployeeTable() {
  return (
    <table>
      <thead><tr><th>Name</th><th>Department</th></tr></thead>
      <tbody>
        <tr><td>John</td><td>IT</td></tr>
        <tr><td>David</td><td>Finance</td></tr>
      </tbody>
    </table>
  );
}

function Footer() {
  return <p className="small">Employee Portal · Demo</p>;
}

export default function Program05Composition() {
  return (
    <div>
      <Header />
      <Sidebar />
      <EmployeeTable />
      <Footer />
    </div>
  );
}

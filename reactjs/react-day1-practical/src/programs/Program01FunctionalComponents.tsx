function EmployeeCard() {
  return (
    <div>
      <h3>John Smith</h3>
      <p>Employee ID: 101</p>
      <p>Department: IT</p>
    </div>
  );
}

export default function Program01FunctionalComponents() {
  return (
    <section>
      <p>A functional component is a JavaScript/TypeScript function that returns JSX.</p>
      <EmployeeCard />
      <EmployeeCard />
    </section>
  );
}

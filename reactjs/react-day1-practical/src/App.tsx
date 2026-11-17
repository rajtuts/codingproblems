import Program01FunctionalComponents from "./programs/Program01FunctionalComponents";
import Program02JSX from "./programs/Program02JSX";
import Program03Props from "./programs/Program03Props";
import Program04State from "./programs/Program04State";
import Program05Composition from "./programs/Program05Composition";
import Program06ParentChild from "./programs/Program06ParentChild";
import Program07ConditionalRendering from "./programs/Program07ConditionalRendering";
import Program08ListsAndKeys from "./programs/Program08ListsAndKeys";
import Program09EventHandling from "./programs/Program09EventHandling";

const programs = [
  { title: "1. Functional components", Component: Program01FunctionalComponents },
  { title: "2. JSX", Component: Program02JSX },
  { title: "3. Props", Component: Program03Props },
  { title: "4. State with useState", Component: Program04State },
  { title: "5. Component composition", Component: Program05Composition },
  { title: "6. Parent-child communication", Component: Program06ParentChild },
  { title: "7. Conditional rendering", Component: Program07ConditionalRendering },
  { title: "8. Lists and keys", Component: Program08ListsAndKeys },
  { title: "9. Event handling", Component: Program09EventHandling }
];

export default function App() {
  return (
    <>
      <header>
        <h1>React Day 1 – Practical Programs</h1>
        <p>Nine independent React + TypeScript examples for Angular developers.</p>
      </header>
      <main>
        <div className="grid">
          {programs.map(({ title, Component }) => (
            <section className="card" key={title}>
              <h2>{title}</h2>
              <Component />
            </section>
          ))}
        </div>
      </main>
    </>
  );
}

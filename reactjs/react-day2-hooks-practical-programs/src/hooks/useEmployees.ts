import { useEffect, useState } from "react";

export interface Employee { id: number; name: string; department: string; }
interface Result { employees: Employee[]; loading: boolean; error: string | null; }

export function useEmployees(): Result {
  const [employees, setEmployees] = useState<Employee[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const controller = new AbortController();

    async function load() {
      try {
        // Replace this simulated request with:
        // const response = await fetch("/api/employees", { signal: controller.signal });
        // if (!response.ok) throw new Error("Request failed");
        // const data: Employee[] = await response.json();
        await new Promise(resolve => window.setTimeout(resolve, 500));
        if (!controller.signal.aborted) {
          setEmployees([
            { id: 11, name: "Asha", department: "Engineering" },
            { id: 12, name: "Ravi", department: "Finance" },
            { id: 13, name: "Maya", department: "Operations" }
          ]);
        }
      } catch {
        if (!controller.signal.aborted) setError("Unable to load employees");
      } finally {
        if (!controller.signal.aborted) setLoading(false);
      }
    }

    load();
    return () => controller.abort();
  }, []);

  return { employees, loading, error };
}
// Same API using browser Fetch API (included for Day 5 comparison).
export async function fetchUsersExample() {
  const token = localStorage.getItem("day5_token");
  const response = await fetch(
    `${import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"}/api/users`,
    { headers: token ? { Authorization: `Bearer ${token}` } : {} }
  );
  if (!response.ok) throw new Error(`Request failed: ${response.status}`);
  return response.json();
}

import { createContext, useContext, useState, type ReactNode } from "react";

interface User { username: string; role: string; }
interface AuthContextValue { user: User | null; login: () => void; logout: () => void; }
const AuthContext = createContext<AuthContextValue | null>(null);

function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null);
  const value: AuthContextValue = {
    user,
    login: () => setUser({ username: "Jordan", role: "ADMIN" }),
    logout: () => setUser(null)
  };
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

function UserPanel() {
  const auth = useContext(AuthContext);
  if (!auth) throw new Error("UserPanel must be inside AuthProvider");
  return <div>
    <p>{auth.user ? `${auth.user.username} (${auth.user.role})` : "Guest – not logged in"}</p>
    {auth.user
      ? <button className="secondary" onClick={auth.logout}>Logout</button>
      : <button onClick={auth.login}>Login as Admin</button>}
  </div>;
}

export default function Program03UseContext() {
  return <section className="card">
    <h2>03. useContext – Authentication</h2>
    <AuthProvider><UserPanel /></AuthProvider>
    <p className="muted">Context avoids passing the user through every intermediate component.</p>
  </section>;
}
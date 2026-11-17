import { useEffect, useState } from "react";
import { login, getUsers, createUser, updateUser, deleteUser } from "./services/userService";

const emptyForm = { name: "", email: "" };

function Login({ onLogin, error, loading }) {
  const [username, setUsername] = useState("admin");
  const [password, setPassword] = useState("password");
  return <main className="login card">
    <h1>Welcome back</h1><p className="muted">Sign in to manage users.</p>
    <form onSubmit={e => { e.preventDefault(); onLogin(username, password); }}>
      <label>Username<input value={username} onChange={e => setUsername(e.target.value)} /></label>
      <label>Password<input type="password" value={password} onChange={e => setPassword(e.target.value)} /></label>
      {error && <p className="error">{error}</p>}
      <button disabled={loading}>{loading ? "Signing in…" : "Sign in"}</button>
    </form>
    <p className="hint">Demo: admin / password</p>
  </main>;
}

export default function App() {
  const [token, setToken] = useState(localStorage.getItem("day5_token"));
  const [users, setUsers] = useState([]);
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [sort, setSort] = useState("name,asc");
  const [form, setForm] = useState(emptyForm);
  const [editingId, setEditingId] = useState(null);
  const [loading, setLoading] = useState(false);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState("");
  const [loginError, setLoginError] = useState("");

  async function handleLogin(username, password) {
    setLoading(true); setLoginError("");
    try {
      const response = await login(username, password);
      localStorage.setItem("day5_token", response.data.token);
      setToken(response.data.token);
    } catch (e) {
      setLoginError(e.response?.data?.message || "Login failed. Check credentials.");
    } finally { setLoading(false); }
  }

  async function loadUsers() {
    setLoading(true); setError("");
    try {
      const response = await getUsers(page, 5, sort);
      setUsers(response.data.content);
      setTotalPages(response.data.totalPages);
    } catch (e) {
      setError(e.response?.data?.message || e.message || "Unable to load users.");
      if (e.response?.status === 401) logout();
    } finally { setLoading(false); }
  }

  useEffect(() => { if (token) loadUsers(); }, [token, page, sort]);

  function logout() {
    localStorage.removeItem("day5_token"); setToken(null); setUsers([]);
  }

  async function submitUser(e) {
    e.preventDefault(); setSaving(true); setError("");
    try {
      if (editingId) await updateUser(editingId, form);
      else await createUser(form);
      setForm(emptyForm); setEditingId(null);
      await loadUsers();
    } catch (e) {
      setError(e.response?.data?.message || "Could not save user.");
    } finally { setSaving(false); }
  }

  function startEdit(user) {
    setEditingId(user.id); setForm({ name: user.name, email: user.email });
    window.scrollTo({ top: 0, behavior: "smooth" });
  }

  async function removeUser(id) {
    if (!window.confirm("Delete this user?")) return;
    setError("");
    try { await deleteUser(id); await loadUsers(); }
    catch (e) { setError(e.response?.data?.message || "Could not delete user."); }
  }

  if (!token) return <Login onLogin={handleLogin} error={loginError} loading={loading} />;

  return <main className="container">
    <header className="topbar">
      <div><p className="eyebrow">DAY 5 · SPRING BOOT INTEGRATION</p><h1>User management</h1>
        <p className="muted">React + Axios + Spring Boot REST API</p></div>
      <button className="secondary" onClick={logout}>Log out</button>
    </header>

    {error && <div className="alert">{error}</div>}

    <section className="card">
      <h2>{editingId ? "Edit user" : "Add a user"}</h2>
      <form className="user-form" onSubmit={submitUser}>
        <label>Name<input required value={form.name} onChange={e => setForm({...form, name:e.target.value})} placeholder="Enter full name" /></label>
        <label>Email<input required type="email" value={form.email} onChange={e => setForm({...form, email:e.target.value})} placeholder="name@example.com" /></label>
        <div className="actions">
          <button disabled={saving}>{saving ? "Saving…" : editingId ? "Update user" : "Add user"}</button>
          {editingId && <button type="button" className="secondary" onClick={() => {setEditingId(null);setForm(emptyForm);}}>Cancel</button>}
        </div>
      </form>
    </section>

    <section className="card">
      <div className="section-heading"><div><h2>Users</h2><p className="muted">Browse, sort, edit and delete records.</p></div>
        <label className="sort">Sort by<select value={sort} onChange={e => {setSort(e.target.value);setPage(0);}}>
          <option value="name,asc">Name · A–Z</option><option value="name,desc">Name · Z–A</option>
          <option value="email,asc">Email · A–Z</option><option value="email,desc">Email · Z–A</option>
        </select></label>
      </div>
      {loading ? <div className="loading">Loading users…</div> : users.length === 0 ?
        <p className="muted">No users found.</p> :
        <div className="table-wrap"><table><thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Actions</th></tr></thead>
          <tbody>{users.map(user => <tr key={user.id}><td>#{user.id}</td><td>{user.name}</td><td>{user.email}</td>
            <td><div className="row-actions"><button className="small secondary" onClick={() => startEdit(user)}>Edit</button>
            <button className="small danger" onClick={() => removeUser(user.id)}>Delete</button></div></td></tr>)}</tbody>
        </table></div>}
      <div className="pagination"><button className="secondary" disabled={page === 0 || loading} onClick={() => setPage(p => p-1)}>Previous</button>
        <span>Page {totalPages === 0 ? 0 : page+1} of {totalPages}</span>
        <button className="secondary" disabled={page+1 >= totalPages || loading} onClick={() => setPage(p => p+1)}>Next</button></div>
    </section>
    <footer>API calls use Axios interceptors to attach the JWT bearer token.</footer>
  </main>;
}

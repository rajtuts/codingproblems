import api from "./api";

export const login = (username, password) =>
  api.post("/api/auth/login", { username, password });
export const getUsers = (page = 0, size = 5, sort = "name,asc") =>
  api.get("/api/users", { params: { page, size, sort } });
export const createUser = (user) => api.post("/api/users", user);
export const updateUser = (id, user) => api.put(`/api/users/${id}`, user);
export const deleteUser = (id) => api.delete(`/api/users/${id}`);

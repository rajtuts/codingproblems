import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || "http://localhost:8080",
  timeout: 10000,
  headers: { "Content-Type": "application/json" }
});

// Request interceptor: attach JWT to protected requests.
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("day5_token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// Response interceptor: central place for common response handling.
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem("day5_token");
      // App checks token state on next render; login screen is shown on reload.
    }
    return Promise.reject(error);
  }
);

export default api;

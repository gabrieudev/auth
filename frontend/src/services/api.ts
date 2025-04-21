import axios from "axios";
import { ApiResponse } from "@/types";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL,
  timeout: 10000,
  headers: {
    "Content-Type": "application/json",
  },
  withCredentials: true,
});

api.interceptors.response.use(
  (response) => {
    return response.data;
  },
  (error) => {
    if (error.response) {
      return Promise.reject(error.response.data as ApiResponse<string>);
    }
    return Promise.reject({
      data: "Erro inesperado",
      timestamp: new Date(),
      status: 500,
    });
  }
);

export default api;

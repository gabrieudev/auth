import { ApiResponse } from "@/types/apiResponse";
import api from "./api";

export async function login(email: string, password: string): Promise<string> {
  try {
    const response = await api.post<string>("/auth/login", {
      email,
      password,
    });
    const fiveMinutesFromNow = new Date(Date.now() + 4 * 60 * 1000);
    localStorage.setItem("tokenExpiresAt", fiveMinutesFromNow.toISOString());
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function logout(): Promise<string> {
  try {
    const response = await api.post<string>("/auth/logout");
    localStorage.removeItem("tokenExpiresAt");
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function refreshTokens(): Promise<string> {
  try {
    const response = await api.post<string>("/auth/refresh");
    const fiveMinutesFromNow = new Date(Date.now() + 4 * 60 * 1000);
    localStorage.setItem("tokenExpiresAt", fiveMinutesFromNow.toISOString());
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

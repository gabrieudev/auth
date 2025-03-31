import api from "./api";
import { ApiResponse } from "@/types/apiResponse";

export async function login(email: string, password: string): Promise<string> {
  try {
    const response = await api.post<string>("/auth/login", {
      email,
      password,
    });
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function logout(): Promise<string> {
  try {
    const response = await api.post<string>("/auth/logout");
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function isAuthenticated(): Promise<boolean> {
  try {
    await api.post<string>("/auth/refresh");

    return true;
  } catch {
    return false;
  }
}

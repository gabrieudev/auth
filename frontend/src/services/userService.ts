import api from "./api";
import { ApiResponse } from "@/types/apiResponse";
import { User } from "@/types/user";

export async function signup(
  firstName: string,
  lastName: string,
  email: string,
  password: string
): Promise<User> {
  try {
    const response = await api.post<User>("/users", {
      firstName,
      lastName,
      email,
      password,
    });
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function getMe(): Promise<User> {
  try {
    const response = await api.get<User>("/users/me");
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function forgotPassword(): Promise<string> {
  try {
    const response = await api.post<string>("/users/forgot-password");
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function resetPassword(code: string, password: string) {
  try {
    const response = await api.post<string>("/users/reset-password", {
      code,
      password,
    });
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

export async function resendEmailConfirmation(userId: string): Promise<string> {
  try {
    const response = await api.post<string>(`/users/${userId}/email`);
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

import api from "./api";
import { ApiResponse, ResetPassword, User, Signup } from "@/types";

export async function signup({
  firstName,
  lastName,
  email,
  password,
}: Signup): Promise<User> {
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

export async function resetPassword({
  code,
  password,
}: ResetPassword): Promise<string> {
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

export async function updateUser(user: Partial<User>): Promise<User> {
  try {
    const response = await api.put<User>("/users", user);
    return response.data;
  } catch (error) {
    const err = error as ApiResponse<string>;
    throw new Error(err.data || "Erro inesperado");
  }
}

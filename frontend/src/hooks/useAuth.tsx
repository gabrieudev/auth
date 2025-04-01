import { getMe } from "@/services/userService";
import { refreshTokens } from "@/services/authService";
import { ApiResponse } from "@/types/apiResponse";
import { User } from "@/types/user";
import { useState, useEffect } from "react";

export function useAuth() {
  const [user, setUser] = useState<User | null>(null);
  const [loadingUser, setLoadingUser] = useState(true);
  const [loadingAuth, setLoadingAuth] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const validateToken = (tokenExpiresAt: string): boolean => {
    const now = new Date();
    const expiresAt = new Date(tokenExpiresAt);
    return now < expiresAt;
  };

  useEffect(() => {
    const tokenExpiresAt = localStorage.getItem("tokenExpiresAt");

    const refresh = async () => {
      try {
        await refreshTokens();
        setIsAuthenticated(true);
      } catch (err) {
        const error = err as ApiResponse<string>;
        setError(error.data || "Erro inesperado");
      }
    };

    const fetchUser = async () => {
      try {
        const data = await getMe();
        setUser(data);
      } catch (err) {
        const error = err as ApiResponse<string>;
        setError(error.data || "Erro inesperado");
      } finally {
        setLoadingUser(false);
      }
    };

    if (!tokenExpiresAt) {
      setIsAuthenticated(false);
    } else {
      setIsAuthenticated(validateToken(tokenExpiresAt));
    }

    if (!isAuthenticated) {
      refresh();
    }

    setLoadingAuth(false);

    fetchUser();
  }, [isAuthenticated]);

  return { user, error, isAuthenticated, loadingUser, loadingAuth };
}

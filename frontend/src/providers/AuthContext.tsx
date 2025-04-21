import { User, Credentials } from "@/types";
import {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from "react";
import {
  login as authLogin,
  logout as authLogout,
  refreshTokens,
} from "@/services/authService";
import { getMe } from "@/services/userService";

type AuthContextType = {
  user: User | null;
  isLoading: boolean;
  signIn: (credentials: { email: string; password: string }) => Promise<void>;
  signOut: () => void;
};

type AuthProviderProps = {
  children: ReactNode;
};

const validateToken = (): boolean => {
  const tokenExpiresAt = localStorage.getItem("tokenExpiresAt");
  const now = new Date();
  const expiresAt = new Date(tokenExpiresAt || "");
  return now < expiresAt;
};

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: AuthProviderProps) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const initializeAuth = async () => {
      try {
        if (!validateToken()) {
          await refreshTokens();
        }
        const userData = await getMe();
        setUser(userData);
      } catch {
        authLogout();
        setUser(null);
      } finally {
        setIsLoading(false);
      }
    };

    initializeAuth();
  }, []);

  const signIn = async (credentials: Credentials): Promise<void> => {
    try {
      await authLogin(credentials);
      const data = await getMe();
      setUser(data);
    } catch (err) {
      console.error(err);
    }
  };

  const signOut = () => {
    authLogout();
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, isLoading, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === null)
    throw new Error("useAuth deve ser usado dentro de um AuthProvider");
  return context;
};

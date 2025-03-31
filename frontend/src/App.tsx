import {
  BrowserRouter,
  Routes,
  Route,
  Navigate,
  Outlet,
} from "react-router-dom";
import Home from "./pages/Home";
import SignIn from "./pages/SignIn";
import React, { useEffect, useState } from "react";
import { HelmetProvider } from "react-helmet-async";
import SignUp from "./pages/SignUp";
import MainLayout from "./layouts/MainLayout";
import Dashboard from "./pages/Dashboard";
import { isTokenValid, refreshTokens } from "./services/authService";
import ResetPassword from "./pages/ResetPassword";
import { Loader2 } from "lucide-react";

const PrivateRoute: React.FC = () => {
  const [auth, setAuth] = useState<boolean | null>(null);
  const tokenExpiresAt = localStorage.getItem("tokenExpiresAt");

  useEffect(() => {
    const checkAuth = async () => {
      if (!tokenExpiresAt) {
        setAuth(false);
      }
      if (isTokenValid(tokenExpiresAt || "")) {
        setAuth(true);
      } else {
        await refreshTokens();
        setAuth(true);
      }
    };
    checkAuth();
  }, [tokenExpiresAt]);

  if (auth === null) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <Loader2 className="animate-spin" />
      </div>
    );
  }

  return auth ? <Outlet /> : <Navigate to="/signin" />;
};

const PublicRoute: React.FC = () => {
  const [auth, setAuth] = useState<boolean | null>(null);
  const tokenExpiresAt = localStorage.getItem("tokenExpiresAt");

  useEffect(() => {
    const checkAuth = async () => {
      if (!tokenExpiresAt) {
        setAuth(false);
      }
      if (isTokenValid(tokenExpiresAt || "")) {
        setAuth(true);
      } else {
        await refreshTokens();
        setAuth(true);
      }
    };
    checkAuth();
  }, [tokenExpiresAt]);

  if (auth === null) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <Loader2 className="animate-spin" />
      </div>
    );
  }

  return auth ? <Navigate to="/dashboard" /> : <Outlet />;
};

const App: React.FC = () => {
  return (
    <HelmetProvider>
      <BrowserRouter>
        <Routes>
          <Route element={<MainLayout />}>
            <Route element={<PrivateRoute />}>
              <Route path="/dashboard" element={<Dashboard />} />
              <Route path="/reset-password" element={<ResetPassword />} />
            </Route>
          </Route>
          <Route element={<PublicRoute />}>
            <Route path="/" element={<Home />} />
            <Route path="/signin" element={<SignIn />} />
            <Route path="/signup" element={<SignUp />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </HelmetProvider>
  );
};

export default App;

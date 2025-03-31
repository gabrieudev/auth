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
import { isAuthenticated } from "./services/authService";
import ResetPassword from "./pages/ResetPassword";

const PrivateRoute: React.FC = () => {
  const [auth, setAuth] = useState<boolean | null>(null);

  useEffect(() => {
    const checkAuth = async () => {
      const authenticated = await isAuthenticated();
      setAuth(authenticated);
    };
    checkAuth();
  }, []);

  if (auth === null) {
    return <div>Carregando...</div>;
  }

  return auth ? <Outlet /> : <Navigate to="/signin" />;
};

const PublicRoute: React.FC = () => {
  const [auth, setAuth] = useState<boolean | null>(null);

  useEffect(() => {
    const checkAuth = async () => {
      const authenticated = await isAuthenticated();
      setAuth(authenticated);
    };
    checkAuth();
  }, []);

  if (auth === null) {
    return <div>Carregando...</div>;
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

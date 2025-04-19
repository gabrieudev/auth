import React from "react";
import { HelmetProvider } from "react-helmet-async";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import MainLayout from "./layouts/MainLayout";
import Dashboard from "./pages/Dashboard";
import Home from "./pages/Home";
import ResetPassword from "./pages/ResetPassword";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import { AuthProvider } from "./providers/AuthContext";
import { PrivateRoute } from "./components/PrivateRoute";
import { PublicRoute } from "./components/PublicRoute";

const App: React.FC = () => {
  return (
    <AuthProvider>
      <HelmetProvider>
        <Router>
          <Routes>
            <Route path="/" element={<Home />} />

            <Route
              path="/signin"
              element={
                <PublicRoute>
                  <SignIn />
                </PublicRoute>
              }
            />

            <Route
              path="/signup"
              element={
                <PublicRoute>
                  <SignUp />
                </PublicRoute>
              }
            />

            <Route element={<MainLayout />}>
              <Route
                path="/dashboard"
                element={
                  <PrivateRoute>
                    <Dashboard />
                  </PrivateRoute>
                }
              />

              <Route
                path="/reset-password"
                element={
                  <PrivateRoute>
                    <ResetPassword />
                  </PrivateRoute>
                }
              />
            </Route>
          </Routes>
        </Router>
      </HelmetProvider>
    </AuthProvider>
  );
};

export default App;

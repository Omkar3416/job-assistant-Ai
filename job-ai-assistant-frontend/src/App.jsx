import {
  BrowserRouter,
  Routes,
  Route
} from "react-router-dom";

import JobPreferencePage
    from "./pages/JobPreferencePage";

import LoginPage from "./pages/LoginPage";
import DashboardPage from "./pages/DashboardPage";
import ProtectedRoute from "./components/ProtectedRoute";
import ProfilePage from "./pages/ProfilePage";
import { Navigate } from "react-router-dom";
import ResumePage from "./pages/ResumePage";

function App() {
  return (
      <BrowserRouter>

        <Routes>

          <Route
              path="/"
              element={<LoginPage />}
          />

          <Route
              path="/dashboard"
              element={
                <ProtectedRoute>
                  <DashboardPage />
                </ProtectedRoute>
              }
          />
            <Route
                path="/profile"
                element={
                    <ProtectedRoute>
                        <ProfilePage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/job-preferences"
                element={
                    <ProtectedRoute>
                        <JobPreferencePage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/resume"
                element={
                    <ProtectedRoute>
                        <ResumePage />
                    </ProtectedRoute>
                }
            />
            <Route
                path="/"
                element={
                    localStorage.getItem("accessToken")
                        ? <Navigate to="/dashboard" />
                        : <LoginPage />
                }
            />


        </Routes>

      </BrowserRouter>
  );
}

export default App;
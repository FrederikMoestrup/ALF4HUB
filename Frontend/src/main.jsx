import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router";
import "./index.css";
import TeamDashBoard from "./pages/TeamDashBoard.jsx";
import App from "./App.jsx";
import TestPage from "./pages/TestPage.jsx";

createRoot(document.getElementById("root")).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route path="/*" element={<App />} />
                <Route path="test" element={<TestPage />} />
                <Route path="/teamDashBoard" element={<TeamDashBoard />} />
            </Routes>
        </BrowserRouter>
    </StrictMode>
);
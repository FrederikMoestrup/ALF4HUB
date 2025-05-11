import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router";

import "./index.css";

import LeaveTeam from "./pages/team/LeaveTeam.jsx";
import Navbar from "./components/Navbar.jsx";
import Login from "./pages/login-register/Login.jsx";
import Register from "./pages/login-register/Register.jsx";
import Logo from "./assets/ALTF4HUB.png";

import BlogPostFrontPage from "./pages/blog/BlogPostFrontPage.jsx";
import CreateBlogPost from "./pages/blog/CreateBlogPost.jsx";
import Drafts from "./pages/blog/Drafts.jsx";
import TeamDashBoard from "./pages/team/teamDashboard/TeamDashBoard.jsx";
import TestPage from "./pages/team/TestPage.jsx";

const NotFound = () => (
  <div style={{ textAlign: "center", paddingTop: "100px" }}>
    <h1>404 - Page Not Found</h1>
    <p>The page you're looking for doesn't exist.</p>
  </div>
);

const RootComponent = () => (
  <>
    <Navbar />
    <main
      style={{
        backgroundColor: "#0e0f13",
        height: "100vh",
        color: "white",
        padding: "20px",
      }}
    >
      <Routes>
        <Route
          path="/"
          element={
            <div style={{ textAlign: "center" }}>
              <h1>Welcome to </h1>
              <img src={Logo} alt="ALTF4Hub Logo" className="logo-img" />
            </div>
          }
        />

        <Route path="/leave-team" element={<LeaveTeam />} />
        <Route path="/team-dashboard" element={<TeamDashBoard />} />
        <Route path="test" element={<TestPage />} />

        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        <Route path="/blog/posts" element={<BlogPostFrontPage />} />
        <Route path="/blog/create" element={<CreateBlogPost />} />
        <Route path="/blog/drafts" element={<Drafts />} />

        <Route path="*" element={<NotFound />} />
      </Routes>
    </main>
  </>
);

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <RootComponent />
    </BrowserRouter>
  </StrictMode>
);

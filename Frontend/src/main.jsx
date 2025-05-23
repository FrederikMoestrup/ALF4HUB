import { StrictMode, useState } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router";

import "./index.css";

import Navbar from "./components/Navbar.jsx";
import GlobalStyle from "./styles/GlobalStyles.js";
import Homepage from "./pages/Homepage.jsx";

import BlogPostFrontPage from "./pages/blog/BlogPostFrontPage.jsx";
import CreateBlogPost from "./pages/blog/CreateBlogPost.jsx";
import Drafts from "./pages/blog/Drafts.jsx";
import EditBlogPost from "./pages/blog/EditBlogPost.jsx";
import ReadBlogPost from "./pages/blog/ReadBlogPost.jsx";

import TeamDashBoard from "./pages/teamDashboard/TeamDashBoard.jsx";
import LeaveTeam from "./pages/team/LeaveTeam.jsx";
import TestPage from "./pages/team/TestPage.jsx";

import CreateTournament from "./pages/tournament/CreateTournament.jsx";
import JoinTournament from "./pages/tournament/JoinTournament.jsx";
import MyTournaments from "./pages/tournament/MyTournaments.jsx";
import TournamentOverview from "./pages/tournament/TournamentOverview.jsx";
import ViewTournamentsByGame from "./pages/tournament/ViewTournamentsByGame.jsx";

import Login from "./pages/login-register/Login.jsx";
import Register from "./pages/login-register/Register.jsx";
import Profile from "./pages/profile/Profile.jsx";

import NotificationsPage from "./pages/Notifications/NotificationsPage.jsx";


const NotFound = () => (
  <div style={{ textAlign: "center", paddingTop: "100px" }}>
    <h1>404 - Page Not Found</h1>
    <p>The page you're looking for doesn't exist.</p>
  </div>
);

const RootComponent = () => {
  const [notifications, setNotifications] = useState([]);
  
  return (
    <>
      <GlobalStyle />
      <Navbar
        setNotifications={setNotifications}        
      />
      <main
        style={{
          backgroundColor: "#0e0f13",
          height: "100vh",
          color: "white",
          padding: "20px",
        }}
      >
        <Routes>
          <Route path="/" element={<Homepage />} />

          <Route path="/tournaments" element={<TournamentOverview />} />
          <Route path="/tournaments/game/:gameName" element={<ViewTournamentsByGame />} />
          <Route path="/tournaments/create" element={<CreateTournament />} />
          <Route path="/tournaments/join" element={<JoinTournament />} />
          <Route path="/my-tournaments" element={<MyTournaments />} />

          <Route path="/leave-team" element={<LeaveTeam />} />
          <Route path="/team-dashboard" element={<TeamDashBoard />} />
          <Route path="test" element={<TestPage />} />

          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route path="/notifications" element={<NotificationsPage setNotifications={setNotifications} notifications={notifications} />} />

          <Route path="/blog/posts" element={<BlogPostFrontPage />} />
          <Route path="/blog/create" element={<CreateBlogPost />} />
          <Route path="/blog/drafts" element={<Drafts />} />
          <Route path="/blog/:postId" element={<ReadBlogPost />} />
          <Route path="/blog/:postId/edit" element={<EditBlogPost />} />

          <Route path="/profile" element={<Profile />} />

          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </>
  );
};

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <RootComponent />
    </BrowserRouter>
  </StrictMode>
);
export { RootComponent };
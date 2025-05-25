import { StrictMode, useState } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router";

import "./index.css";

import Navbar from "./components/Navbar.jsx";
import GlobalStyle from "./styles/GlobalStyles.js";
import HomePage from "./pages/HomePage.jsx";
import TeamPage from "./pages/team/TeamPage.jsx";
import TeamsPage from "./pages/team/TeamsPage.jsx";

import TournamentPage from "./pages/tournament/TournamentPage.jsx";
import TournamentsPage from "./pages/tournament/TournamentsPage.jsx";
import CreateTeamPage from "./pages/team/CreateTeamPage.jsx";
import CreateTournament from "./pages/tournament/CreateTournament.jsx";
import TournamentOverview from "./pages/tournament/TournamentOverview"; // Not used. We use TournamentsPage
import TournamentHistory from "./pages/tournament/TournamentHistory.jsx";

import CreateBlogPost from "./pages/blog/CreateBlogPost.jsx";
import Drafts from "./pages/blog/Drafts.jsx";
import EditBlogPost from "./pages/blog/EditBlogPost.jsx";
import ReadBlogPost from "./pages/blog/ReadBlogPost.jsx";
import ForumPage from "./pages/blog/ForumPage.jsx";

import TeamDashBoard from "./pages/teamDashboard/TeamDashBoard.jsx";
import LeaveTeam from "./pages/team/LeaveTeam.jsx";
import TestPage from "./pages/team/TestPage.jsx";

import Login from "./pages/login-register/Login.jsx";
import Register from "./pages/login-register/Register.jsx";
import Profile from "./pages/profile/Profile.jsx";

import NotificationsPage from "./pages/Notifications/NotificationsPage.jsx";

export const NotFound = () => (
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
      <Navbar setNotifications={setNotifications} />
      <main
        style={{
          backgroundColor: "#0e0f13",
          height: "100vh",
          color: "white",
          padding: "20px",
        }}
      >
        <Routes>
          <Route path="/" element={<HomePage />} />

          <Route path="/tournaments" element={<TournamentsPage />} />
          <Route
            path="/tournament/:tournamentId"
            element={<TournamentPage />}
          />
          <Route path="/tournaments/create" element={<CreateTournament />} />

          <Route path="/teams" element={<TeamsPage />} />
          <Route path="/team/:teamId" element={<TeamPage />} />
          <Route path="/create-team" element={<CreateTeamPage />} />
          <Route path="/leave-team" element={<LeaveTeam />} />
          <Route path="/team-dashboard" element={<TeamDashBoard />} />
          <Route path="/test" element={<TestPage />} />

          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          <Route
            path="/notifications"
            element={
              <NotificationsPage
                setNotifications={setNotifications}
                notifications={notifications}
              />
            }
          />

          <Route path="/blog/create" element={<CreateBlogPost />} />
          <Route path="/blog/forum" element={<ForumPage />} />
          <Route path="/blog/drafts" element={<Drafts />} />
          <Route path="/blog/:postId" element={<ReadBlogPost />} />
          <Route path="/blog/:postId/edit" element={<EditBlogPost />} />

          <Route path="/profile" element={<Profile />} />
          <Route path="/tournaments/history" element={<TournamentHistory />} />

          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </>
  );
};

export { RootComponent };

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <RootComponent />
    </BrowserRouter>
  </StrictMode>
);

import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route, Routes } from "react-router";

import "./index.css";

import Navbar from "./components/Navbar.jsx";
import GlobalStyle from "./styles/GlobalStyles.js";
import HomePage from "./pages/HomePage.jsx";
import TeamPage from "./pages/TeamPage.jsx";
import TeamsPage from "./pages/TeamsPage.jsx";
import TournamentPage from "./pages/TournamentPage.jsx";
import TournamentsPage from "./pages/TournamentsPage.jsx";
import CreateTeamPage from "./pages/CreateTeamPage.jsx";

import CreateBlogPost from "./pages/blog/CreateBlogPost.jsx";
import Drafts from "./pages/blog/Drafts.jsx";
import EditBlogPost from "./pages/blog/EditBlogPost.jsx";
import ReadBlogPost from "./pages/blog/ReadBlogPost.jsx";
import ForumPage from "./pages/blog/ForumPage.jsx";

import TeamDashBoard from "./pages/teamDashboard/TeamDashBoard.jsx";
import LeaveTeam from "./pages/team/LeaveTeam.jsx";
import TestPage from "./pages/team/TestPage.jsx";

import CreateTournament from "./pages/tournament/CreateTournament.jsx";
import JoinTournament from "./pages/tournament/JoinTournament.jsx";
import MyTournaments from "./pages/tournament/MyTournaments.jsx";
import TournamentOverview from "./pages/tournament/TournamentOverview.jsx";
import ViewTournamentsByGame from "./pages/tournament/ViewTournamentsByGame.jsx";
import TournamentHistory from "./pages/TournamentHistory.jsx";



import Login from "./pages/login-register/Login.jsx";
import Register from "./pages/login-register/Register.jsx";
import Profile from "./pages/profile/Profile.jsx";

export const NotFound = () => (
  <div style={{ textAlign: "center", paddingTop: "100px" }}>
    <h1>404 - Page Not Found</h1>
    <p>The page you're looking for doesn't exist.</p>
  </div>
);

const RootComponent = () => (
  <>
    <GlobalStyle />
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
        <Route path="/" element={<Homepage />} />

        <Route path="/tournaments" element={<TournamentOverview />} />
        <Route
          path="/tournaments/game/:gameName"
          element={<ViewTournamentsByGame />}
        />
        <Route path="/tournaments/create" element={<CreateTournament />} />
        <Route path="/tournaments/join" element={<JoinTournament />} />
        <Route path="/my-tournaments" element={<MyTournaments />} />

        <Route path="/teams" element={<TeamsPage />} />
        <Route path="/team/:teamId" element={<TeamPage />} />
        <Route path="/create-team" element={<CreateTeamPage />} />
        <Route path="/leave-team" element={<LeaveTeam />} />
        <Route path="/team-dashboard" element={<TeamDashBoard />} />
        <Route path="/test" element={<TestPage />} />

        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

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

createRoot(document.getElementById("root")).render(
  <StrictMode>
    <BrowserRouter>
      <RootComponent />
    </BrowserRouter>
  </StrictMode>
);

import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import { createGlobalStyle } from 'styled-components';
import HomePage from './pages/HomePage';
import TeamPage from './pages/TeamPage';
import TeamsPage from './pages/TeamsPage';
import TournamentPage from './pages/TournamentPage';
import TournamentsPage from './pages/TournamentsPage';
import TestPage from './pages/TestPage';
import './App.css';

const GlobalStyle = createGlobalStyle`
  body {
    margin: 0;
    padding: 0;
    font-family: system-ui, -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
    background-color: #f5f5f5;
  }
`;

function App() {
  return (
    <>
      <GlobalStyle />
      <Router>
        <div className="app-container">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/teams" element={<TeamsPage />} />
            <Route path="/team/:teamId" element={<TeamPage />} />
            <Route path="/tournaments" element={<TournamentsPage />} />
            <Route path="/tournament/:tournamentId" element={<TournamentPage />} />
            <Route path="/test" element={<TestPage />} />
          </Routes>
        </div>
      </Router>
    </>
  );
}

export default App;

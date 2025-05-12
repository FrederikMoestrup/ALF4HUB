import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';

const TeamsPage = () => {
  const url = "http://localhost:7070/api/teams";
  const [teams, setTeams] = useState([]);
  const [filterActive, setFilterActive] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    const fetchTeams = async () => {
      try {
        const response = await fetch(url);
        const data = await response.json();
        console.log("Fetched teams:", data);
        setTeams(data);
      } catch (error) {
        console.error("Error fetching teams:", error);
      }
    };

    fetchTeams();
  }, []);

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  const toggleFilter = () => {
    setFilterActive(!filterActive);
  };

  const filteredTeams = teams.filter(team => {
    const name = team?.teamName ?? '';
    const matchesSearch = name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesFilter = !filterActive || (team?.tournamentTeams?.length > 2);
    return matchesSearch && matchesFilter;
  });

  return (
    <div>
      <Navbar />
      <div className="teams-page-container">
        <div className="teams-header">
          <h1>Teams</h1>
          <div className="teams-actions">
            <Link to="/create-team" className="button-primary">Create Team</Link>
          </div>
        </div>

        <div className="teams-filters">
          <div className="search-bar">
            <input 
              type="text" 
              placeholder="Search teams..." 
              value={searchTerm}
              onChange={handleSearch}
              className="search-input"
            />
          </div>
          <div className="filter-options">
            <button 
              className={`filter-button ${filterActive ? 'active' : ''}`}
              onClick={toggleFilter}
            >
              {filterActive ? 'Show All Teams' : 'Show Experienced Teams'}
            </button>
          </div>
        </div>

        <div className="teams-list">
          {filteredTeams.length > 0 ? (
            filteredTeams.map(team => (
              <div key={team.id} className="team-card">
                <div className="team-card-header">
                  <div className="team-logo">
                    <span className="team-logo-icon">
                      {team.logo || '🏆'}
                    </span>
                  </div>
                  <div className="team-info">
                    <h3>{team.teamName || 'Unnamed Team'}</h3>
                    <p>Captain: {team.teamCaptain?.username || 'Unknown'}</p> {/* Displaying Captain */}
                    <p>Members: {team.teamAccounts?.length || 0}</p> {/* Displaying Number of Members */}
                    <p>Tournaments: {team.tournamentTeams?.length || 0}</p> {/* Displaying Number of Tournaments */}
                  </div>
                </div>
                <div className="team-card-body">
                  <p>{team.description || 'No description available.'}</p>
                </div>
                <div className="team-card-footer">
                  <Link to={`/team/${team.id}`} className="button-secondary">
                    View Team
                  </Link>
                </div>
              </div>
            ))
          ) : (
            <div className="no-teams-message">
              <p>No teams match your search criteria.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TeamsPage;

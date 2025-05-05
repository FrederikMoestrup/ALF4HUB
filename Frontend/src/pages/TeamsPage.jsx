import { useState } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';

const TeamsPage = () => {
  const [teams, setTeams] = useState([
    { 
      id: 1, 
      name: 'The serious people', 
      logo: '🎮',
      members: 5,
      description: 'A competitive team',
      tournaments: 3
    },
    { 
      id: 2, 
      name: 'Whatever kids', 
      logo: '🐱',
      members: 4,
      description: 'aight',
      tournaments: 2
    },
    { 
      id: 3, 
      name: 'The mentally unstable', 
      logo: '☠️',
      members: 6,
      description: 'hardstyle or die',
      tournaments: 5
    },
    { 
      id: 4, 
      name: 'Sicko mode', 
      logo: '☠️',
      members: 3,
      description: 'Casual team',
      tournaments: 1
    },
    { 
      id: 5, 
      name: 'Omega Squad', 
      logo: 'Ω',
      members: 5,
      description: 'We believe in science 🤓',
      tournaments: 4
    }
  ]);

  const [filterActive, setFilterActive] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  const toggleFilter = () => {
    setFilterActive(!filterActive);
  };

  // Filter teams based on search term and active filter
  const filteredTeams = teams.filter(team => {
    const matchesSearch = team.name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesFilter = !filterActive || team.tournaments > 2;
    return matchesSearch && matchesFilter;
  });

  return (
    <div>
      <Navbar />
      <div className="teams-page-container">
        <div className="teams-header">
          <h1>Teams</h1>
          <div className="teams-actions">
            <Link to="/create-team" className="button-primary">
              Create Team
            </Link>
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
                    <span className="team-logo-icon">{team.logo}</span>
                  </div>
                  <div className="team-info">
                    <h3>{team.name}</h3>
                    <p>Members: {team.members}</p>
                    <p>Tournaments: {team.tournaments}</p>
                  </div>
                </div>
                <div className="team-card-body">
                  <p>{team.description}</p>
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
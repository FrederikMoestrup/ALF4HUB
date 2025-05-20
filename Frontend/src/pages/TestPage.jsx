import { useState } from 'react';
import Navbar from '../components/Navbar';

const TestPage = () => {
  const [teams, setTeams] = useState([
    { id: 1, name: 'Team Alpha', members: 3, isFull: false },
    { id: 2, name: 'Team Beta', members: 4, isFull: false },
    { id: 3, name: 'Team Gamma', members: 5, isFull: true },
    { id: 4, name: 'Team Delta', members: 2, isFull: false },
    { id: 5, name: 'Team Epsilon', members: 3, isFull: false }
  ]);
  
  const [hasApplied, setHasApplied] = useState(false);
  const [selectedTeam, setSelectedTeam] = useState(null);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleApply = (teamId) => {
    setSelectedTeam(teamId);
    setHasApplied(true);
    setShowSuccessMessage(true);
    
    // Hide success message after 3 seconds
    setTimeout(() => {
      setShowSuccessMessage(false);
    }, 3000);
  };

  return (
    <div>
      <Navbar />
      <div className="test-page-container">
        <h1>Test Features</h1>
        
        <div className="test-section">
          <h2>Liste med hold (Team List)</h2>
          <div className="team-list">
            {teams.map(team => (
              <div key={team.id} className="team-item">
                <div className="team-info">
                  <h3>{team.name}</h3>
                  <p>Medlemmer (Members): {team.members}</p>
                  {team.isFull && <p className="full-tag">FULDT (FULL)</p>}
                </div>
                
                <div className="team-actions">
                  <button 
                    className="button-primary"
                    onClick={() => handleApply(team.id)}
                    disabled={team.isFull || (hasApplied && selectedTeam !== team.id)}
                  >
                    {team.isFull ? 'Fuldt (Full)' : 
                     (hasApplied && selectedTeam === team.id) ? 'Anmodet (Applied)' : 
                     'Anmod om deltagelse (Apply)'}
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
        
        {showSuccessMessage && (
          <div className="success-message">
            Din anmodning om deltagelse er sendt! (Your application has been sent!)
          </div>
        )}
      </div>
    </div>
  );
};

export default TestPage; 
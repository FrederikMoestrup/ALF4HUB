import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import Navbar from '../components/Navbar';

const TournamentPage = () => {
  const { tournamentId } = useParams();
  const [tournament, setTournament] = useState(null);
  const [loading, setLoading] = useState(true);
  const [hasTeam, setHasTeam] = useState(false);
  const [teams, setTeams] = useState([]);
  const [isInTournament, setIsInTournament] = useState(false);
  const [showError, setShowError] = useState(false);
  const [errorMessage, setErrorMessage] = useState('');
  
  useEffect(() => {
    // Simulate API call to fetch tournament data
    setTimeout(() => {
      // Mock data
      const mockTournament = {
        id: tournamentId,
        name: 'Tournament Name',
        teams: [
          { id: 1, name: 'Team 1', isFull: false, isSigned: false },
          { id: 2, name: 'Team 2', isFull: false, isSigned: false },
          { id: 3, name: 'Team 3', isFull: true, isSigned: false },
          { id: 4, name: 'Team 4', isFull: false, isSigned: false },
          { id: 5, name: 'Team 5', isFull: false, isSigned: false },
          { id: 6, name: 'Team 6', isFull: false, isSigned: false },
        ]
      };
      
      setTournament(mockTournament);
      setTeams(mockTournament.teams);
      setLoading(false);
      
      // For demo purposes, let's set some random state
      setHasTeam(Math.random() > 0.5);
      setIsInTournament(Math.random() > 0.5);
    }, 500);
  }, [tournamentId]);

  const handleSignUpTeam = (teamId) => {
    if (!hasTeam) {
      setShowError(true);
      setErrorMessage('You need to be in a team to sign up.');
      return;
    }
    
    // Simulate signing up a team
    setTeams(teams.map(team => 
      team.id === teamId ? { ...team, isSigned: true } : team
    ));
    setIsInTournament(true);
  };

  const handleSendJoinRequest = () => {
    if (!hasTeam) {
      setShowError(true);
      setErrorMessage('You need to be in a team to send a join request.');
      return;
    }
    
    alert('Join request sent!');
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  // Check if all teams are full
  const allTeamsFull = teams.every(team => team.isFull);

  return (
    <div>
      <Navbar isLoggedIn={true} />
      
      <div className="tournament-page">
        <div className="tournament-header">
          <h2>{tournament.name}</h2>
          <div className="tournament-actions">
            <button 
              className="button-primary" 
              onClick={handleSendJoinRequest}
              disabled={isInTournament}
            >
              Send "Join Team" Request
            </button>
          </div>
        </div>
        
        {showError && (
          <div className="error-message">
            {errorMessage}
          </div>
        )}
        
        <div className="tournament-teams">
          {allTeamsFull ? (
            <div className="no-teams-message">
              <p>Currently no open Team slots.</p>
            </div>
          ) : (
            teams.map(team => (
              <div key={team.id} className="tournament-item">
                <div className="team-info">
                  <h3>
                    <Link to={`/team/${team.id}`}>{team.name}</Link>
                  </h3>
                </div>
                <div className="team-actions">
                  {!team.isFull && !team.isSigned && (
                    <button 
                      className="button-primary"
                      onClick={() => handleSignUpTeam(team.id)}
                      disabled={isInTournament}
                    >
                      Sign up
                    </button>
                  )}
                  {team.isFull && (
                    <span>Full</span>
                  )}
                  {team.isSigned && (
                    <span>Signed Up</span>
                  )}
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default TournamentPage; 
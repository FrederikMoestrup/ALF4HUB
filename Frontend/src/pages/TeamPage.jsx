import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import Navbar from '../components/Navbar';

const TeamPage = () => {
  const { teamId } = useParams();
  const [team, setTeam] = useState(null);
  const [loading, setLoading] = useState(true);
  const [userRole, setUserRole] = useState('visitor'); // 'visitor', 'member', 'captain'
  const [isInTeam, setIsInTeam] = useState(false);
  
  useEffect(() => {
    // Simulate API call to fetch team data
    setTimeout(() => {
      // Mock data
      const mockTeam = {
        id: teamId,
        name: 'TeamName',
        logo: '/path/to/logo.png',
        members: [
          { id: 1, name: 'Player1', gameAccount: 'player1@game' },
          { id: 2, name: 'Player2', gameAccount: 'player2@game' },
          { id: 3, name: 'Player3', gameAccount: 'player3@game' },
        ],
        about: 'About this Team\nxxx\n...\nxxxx\n..'
      };
      
      setTeam(mockTeam);
      setLoading(false);
      
      // For demo purposes
      const roles = ['visitor', 'member', 'captain'];
      const randomRole = roles[Math.floor(Math.random() * roles.length)];
      setUserRole(randomRole);
      setIsInTeam(randomRole === 'member' || randomRole === 'captain');
    }, 500);
  }, [teamId]);

  const handleJoinTeam = () => {
    // In a real app, this would send a request to join the team
    alert('Request to join team sent!');
  };

  const handleLeaveTeam = () => {
    // In a real app, this would send a request to leave the team
    setIsInTeam(false);
    setUserRole('visitor');
  };

  const handleInvitePlayer = () => {
    // In a real app, this would open a modal to invite players
    alert('Invite modal would open here');
  };

  const handleEditTeam = () => {
    // In a real app, this would navigate to team edit page
    alert('Navigate to team edit page');
  };

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Navbar userTeam={team} isLoggedIn={true} />
      
      <div className="team-page">
        <div className="team-header">
          <div className="team-logo">
            {/* Team logo would be an actual image */}
            Team Logo
          </div>
          <div className="team-info">
            <h2>{team.name}</h2>
            <p>Tournament: 0</p>
          </div>
          
          {/* Different actions based on user role */}
          <div className="team-actions">
            {userRole === 'captain' && (
              <>
                <button className="button-primary" onClick={handleEditTeam}>Edit Team</button>
                <button className="button-primary" onClick={handleInvitePlayer}>Invite</button>
              </>
            )}
            {userRole === 'visitor' && !isInTeam && (
              <button className="button-primary" onClick={handleJoinTeam}>Sign Up</button>
            )}
            {userRole === 'member' && (
              <button className="button-danger" onClick={handleLeaveTeam}>Leave Team</button>
            )}
          </div>
        </div>
        
        <div className="team-content">
          <div className="members-section">
            <h3>Member names:</h3>
            {team.members.map(member => (
              <div key={member.id} className="member-item">
                <p>{member.name}</p>
                <p>Link to gameAccount: {member.gameAccount}</p>
              </div>
            ))}
          </div>
          
          <div className="about-section">
            <h3>About</h3>
            <p>{team.about}</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default TeamPage; 
import { useState, useEffect } from 'react';
import { useParams, Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import { useNotifications } from '../contexts/NotificationContext';

const TeamPage = () => {
  const { teamId } = useParams();
  const [team, setTeam] = useState(null);
  const [loading, setLoading] = useState(true);
  const [userRole, setUserRole] = useState('visitor'); // 'visitor', 'member', 'captain'
  const [isInTeam, setIsInTeam] = useState(false);
  const [showJoinModal, setShowJoinModal] = useState(false);
  const [joinMessage, setJoinMessage] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);
  const { sendJoinRequest } = useNotifications();
  
  useEffect(() => {
    // Simulate API call to fetch team data
    setTimeout(() => {
      // Mock data
      const mockTeam = {
        id: teamId,
        name: 'TeamName' + teamId, // Change team name to include ID for demonstration
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
    // Open the join request modal
    setShowJoinModal(true);
  };

  const handleSubmitJoinRequest = async () => {
    if (!joinMessage.trim()) {
      alert('Please write a message to the team captain');
      return;
    }
    
    setIsSubmitting(true);
    try {
      // Send join request using the context function
      await sendJoinRequest(
        team,
        { name: 'Current User' }, // In a real app, this would be the logged-in user
        joinMessage
      );
      
      // Close the modal
      setShowJoinModal(false);
      setJoinMessage('');
      
      // Show success message
      alert('Join request sent successfully!');
      
    } catch (error) {
      console.error('Error sending join request:', error);
      alert('Failed to send join request. Please try again.');
    } finally {
      setIsSubmitting(false);
    }
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
      
      {/* Join Request Modal */}
      {showJoinModal && (
        <div className="modal-overlay">
          <div className="modal-content">
            <h3>Join Request for {team.name}</h3>
            <p>Write a message to the team captain:</p>
            <textarea
              value={joinMessage}
              onChange={(e) => setJoinMessage(e.target.value)}
              placeholder="I would like to join your team because..."
              rows={4}
              className="join-message-input"
            />
            <div className="modal-actions">
              <button 
                className="button-primary"
                onClick={handleSubmitJoinRequest}
                disabled={isSubmitting}
              >
                {isSubmitting ? 'Sending...' : 'Send Request'}
              </button>
              <button 
                className="button-secondary"
                onClick={() => setShowJoinModal(false)}
                disabled={isSubmitting}
              >
                Cancel
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default TeamPage; 
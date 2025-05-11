import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import { useNotifications } from '../contexts/NotificationContext';

const TeamPage = () => {
  const { teamId } = useParams();
  const [team, setTeam] = useState(null);
  const [loading, setLoading] = useState(true);
  const [userRole, setUserRole] = useState("visitor"); // 'visitor', 'member', 'captain'
  const [isInTeam, setIsInTeam] = useState(false);
  const [showJoinModal, setShowJoinModal] = useState(false);
  const [joinMessage, setJoinMessage] = useState('');
  const { addNotification } = useNotifications();


  useEffect(() => {
    const fetchTeamData = async () => {
      try {
        const teamResponse = await fetch(
          `http://localhost:7070/api/teams/${teamId}`
        );
        const playersResponse = await fetch(
          `http://localhost:7070/api/teams/${teamId}/players`
        );

        if (!teamResponse.ok || !playersResponse.ok)
          throw new Error("Failed to fetch");

        const teamData = await teamResponse.json();
        const playersData = await playersResponse.json();

        const formattedMembers = playersData.map((player) => ({
          username: player.user.username,
          game: player.game,
        }));

        setTeam({ ...teamData, members: formattedMembers });
      } catch (error) {
        console.error("Error fetching team data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchTeamData();
  }, [teamId]);

 

  const handleSubmitJoinRequest = async () => {
  try {
    await new Promise(resolve => setTimeout(resolve, 1000));
    setShowJoinModal(false);
    setJoinMessage('');
    alert('Join request sent successfully!');
  } catch (error) {
    console.error('Error sending join request:', error);
    alert('Failed to send join request. Please try again.');
  }
};
  const handleJoinTeam = async () => {
    setShowJoinModal(true);
    // In a real app, this would send a request to join the team
    const currentUsername = localStorage.getItem("username"); // eksempel------------
    const isAlreadyMember = team.members.some(member => member.username === currentUsername);

    if (isAlreadyMember) {
      alert("You are already a member of this team.");
      return;
    }

    try {
      const response = await fetch(
          `http://localhost:7070/team-join-request/create/${userId}/${teamId}/${playerAccountId}`,
          { method: "POST" }
      );

      if (!response.ok) {
        const error = await response.json();
        throw new Error(error.message);
      }

      alert("Request to join team sent!");
    } catch (err) {
      alert(`Error: ${err.message}`);
    }
  };

  const handleLeaveTeam = () => {
    // In a real app, this would send a request to leave the team
    setIsInTeam(false);
    setUserRole("visitor");
  };

  const handleInvitePlayer = () => {
    // In a real app, this would open a modal to invite players
    alert("Invite modal would open here");
  };

  const handleEditTeam = () => {
    // In a real app, this would navigate to team edit page
    alert("Navigate to team edit page");
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
            <h2>{team.teamName}</h2>
            <p>Tournament: </p>
          </div>

          {/* Different actions based on user role */}
          <div className="team-actions">
            {userRole === "captain" && (
              <>
                <button className="button-primary" onClick={handleEditTeam}>
                  Edit Team
                </button>
                <button className="button-primary" onClick={handleInvitePlayer}>
                  Invite
                </button>
              </>
            )}
            {userRole === "visitor" && !isInTeam && (
              <button className="button-primary" onClick={handleJoinTeam}>
                Sign Up
              </button>
            )}
            {userRole === "member" && (
              <button className="button-danger" onClick={handleLeaveTeam}>
                Leave Team
              </button>
            )}
          </div>
        </div>

        <div className="team-content">
          <div className="members-section">
            <h3>Members:</h3>
            {team?.members?.map((member, index) => (
              <div key={index} className="bg-white rounded-lg shadow p-4">
                <h2 className="text-lg font-semibold">{member.username}</h2>
                <p className="text-gray-600">Game: {member.game}</p>
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
              >
                Send Request
              </button>
              <button
                className="button-secondary"
                onClick={() => setShowJoinModal(false)}
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

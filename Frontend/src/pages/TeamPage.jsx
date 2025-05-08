import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import Navbar from "../components/Navbar";

const TeamPage = () => {
  const { teamId } = useParams();
  const [team, setTeam] = useState(null);
  const [loading, setLoading] = useState(true);
  const [userRole, setUserRole] = useState("visitor"); // 'visitor', 'member', 'captain'
  const [isInTeam, setIsInTeam] = useState(false);

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

  const handleJoinTeam = () => {
    // In a real app, this would send a request to join the team
    alert("Request to join team sent!");
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
    </div>
  );
};

export default TeamPage;

import { useState, useEffect } from "react";
import { useParams, Link, Navigate, useNavigate } from "react-router-dom";
import Navbar from "../components/Navbar";
import styled from "styled-components";
import apiFacade from "../util/apiFacade";

const PageContainer = styled.div`
  margin: 0 auto;
  max-width: 1200px;
  padding: 20px;
`;

const TeamHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding: 20px;
  background-color: var(--color-accent);
  border-radius: 10px;
  border: 1px solid var(--color-text);
  box-shadow: 0 0 15px rgba(87, 210, 255, 0.3);
`;

const TeamLogo = styled.div`
  width: 100px;
  height: 100px;
  background-color: var(--color-main);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-text);
  font-weight: bold;
  border: 2px solid var(--color-text);
`;

const TeamInfo = styled.div`
  flex-grow: 1;
  margin-left: 20px;

  h2 {
    color: var(--color-text);
    font-family: "Xirod", sans-serif;
    margin-bottom: 10px;
  }

  p {
    color: var(--color-text);
  }
`;

const TeamActions = styled.div`
  display: flex;
  gap: 10px;
`;

const Button = styled.button`
  background-color: var(--color-button-general);
  color: var(--color-text);
  border: 1px solid var(--color-text);
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover {
    background-color: var(--color-accept);
    color: var(--color-main);
  }
`;

const DangerButton = styled(Button)`
  background-color: var(--color-warning);
  color: white;

  &:hover {
    background-color: #c82333;
    color: white;
  }
`;

const TeamContent = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 20px;

  @media (max-width: 768px) {
    grid-template-columns: 1fr;
  }
`;

const Section = styled.div`
  background-color: var(--color-accent);
  padding: 20px;
  border-radius: 10px;
  border: 1px solid var(--color-text);
  box-shadow: 0 0 10px rgba(87, 210, 255, 0.2);

  h3 {
    color: var(--color-text);
    font-family: "Xirod", sans-serif;
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid var(--color-text);
  }
`;

const MemberCard = styled.div`
  background-color: var(--color-button-general);
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 10px;
  border: 1px solid var(--color-text);

  h2 {
    color: var(--color-text);
    font-size: 18px;
    margin-bottom: 5px;
  }

  p {
    color: var(--color-text);
    opacity: 0.8;
  }
`;

const LoadingMessage = styled.div`
  color: var(--color-text);
  font-size: 18px;
  margin-top: 40px;
  text-align: center;
  padding: 20px;
`;

const TeamPage = () => {
  const { teamId } = useParams();
  const [team, setTeam] = useState(null);
  const [loading, setLoading] = useState(true);
  const [userRole, setUserRole] = useState("visitor"); // 'visitor', 'member', 'captain'
  const [isInTeam, setIsInTeam] = useState(false);
  const [currentUserId, setCurrentUserId] = useState(null);
  const navigate = useNavigate();

  const token = localStorage.getItem("token");
  

  useEffect(() => {
    const fetchTeamData = async () => {

      
      try {
        const teamResponse = await fetch(
          `http://localhost:7070/api/teams/${teamId}`
        );
        const playersResponse = await fetch(
          `http://localhost:7070/api/teams/${teamId}/players`
        );

        const id = await apiFacade.getUserId();
        setCurrentUserId(id)

        if (!teamResponse.ok || !playersResponse.ok)
          throw new Error("Failed to fetch");

        const teamData = await teamResponse.json();
        const playersData = await playersResponse.json();

        const formattedMembers = playersData.map((player) => ({
          username: player.user.username,
          game: player.game,
        }));

        const role =
          teamData?.teamCaptain?.id === id ? "captain" : "visitor";

        setUserRole(role);
        setTeam({ ...teamData, members: formattedMembers });
      } catch (error) {
        console.error("Error fetching team data:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchTeamData();
  }, [teamId]);

  const handleJoinTeam = async () => {
    const currentUsername = localStorage.getItem("username"); // eksempel------------
    const isAlreadyMember = team.members.some(
      (member) => member.username === currentUsername
    );

    if (isAlreadyMember) {
      alert("You are already a member of this team.");
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:7070/team-join-request/create/${currentUserId}/${teamId}/${playerAccountId}`,
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

  const handleDeleteTeam = async () => {
  const confirmed = window.confirm("Er du sikker på, at du vil slette holdet?");
  if (!confirmed) return;

  try {
    const response = await fetch(
      `http://localhost:7070/api/teams/${teamId}`,
      {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Fejl ved sletning: ${errorText}`);
    }

    alert("Holdet er slettet!");
    navigate("/teams");
  } catch (error) {
    console.error("Delete failed:", error);
    alert("Noget gik galt under sletningen af holdet.");
  }
};


  if (loading) {
    return <div>Loading...</div>;
  }

  const isCaptain = team?.teamCaptain?.id === currentUserId;

  return (
    <div>
      <PageContainer>
        <TeamHeader>
          <TeamLogo>LOGO</TeamLogo>
          <TeamInfo>
            <h2>{team.teamName}</h2>
          </TeamInfo>
          <TeamActions>
            {userRole === "captain" && (
              <>
                <Button onClick={handleEditTeam}>Rediger</Button>
                <Button onClick={handleInvitePlayer}>Inviter</Button>
              </>
            )}
            {userRole === "visitor" && !isInTeam && (
              <Button onClick={handleJoinTeam}>Tilmeld</Button>
            )}
            {userRole === "member" && (
              <DangerButton onClick={handleLeaveTeam}>Forlad hold</DangerButton>
            )}
            {isCaptain && (
              <DangerButton onClick={handleDeleteTeam}>Slet hold</DangerButton>
            )}
          </TeamActions>
        </TeamHeader>

        <TeamContent>
          <Section>
            <h3>Medlemmer</h3>
            {team?.members?.length > 0 ? (
              team.members.map((member, index) => (
                <MemberCard key={index}>
                  <h2>{member.username}</h2>
                </MemberCard>
              ))
            ) : (
              <p>Ingen medlemmer på dette hold.</p>
            )}
          </Section>
        </TeamContent>
      </PageContainer>
    </div>
  );
};

export default TeamPage;

import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import Navbar from "../components/Navbar";
import styled from "styled-components";

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
    font-family: 'Xirod', sans-serif;
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
    background-color: var(--color-text);
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
    font-family: 'Xirod', sans-serif;
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
    alert("Anmodning om at blive medlem af holdet er sendt!");
  };

  const handleLeaveTeam = () => {
    // In a real app, this would send a request to leave the team
    setIsInTeam(false);
    setUserRole("visitor");
  };

  const handleInvitePlayer = () => {
    // In a real app, this would open a modal to invite players
    alert("Inviter spillere her");
  };

  const handleEditTeam = () => {
    // In a real app, this would navigate to team edit page
    alert("Naviger til redigering af hold");
  };

  if (loading) {
    return <LoadingMessage>Indlæser holdinformation...</LoadingMessage>;
  }

  return (
    <div>
      <Navbar userTeam={team} isLoggedIn={true} />

      <PageContainer>
        <TeamHeader>
          <TeamLogo>
            {/* Team logo would be an actual image */}
            LOGO
          </TeamLogo>
          <TeamInfo>
            <h2>{team.teamName}</h2>
            <p>Turneringer: {team.tournamentTeams?.length || 0}</p>
          </TeamInfo>

          {/* Different actions based on user role */}
          <TeamActions>
            {userRole === "captain" && (
              <>
                <Button onClick={handleEditTeam}>
                  Rediger
                </Button>
                <Button onClick={handleInvitePlayer}>
                  Inviter
                </Button>
              </>
            )}
            {userRole === "visitor" && !isInTeam && (
              <Button onClick={handleJoinTeam}>
                Tilmeld
              </Button>
            )}
            {userRole === "member" && (
              <DangerButton onClick={handleLeaveTeam}>
                Forlad hold
              </DangerButton>
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
                  <p>Spil: {member.game}</p>
                </MemberCard>
              ))
            ) : (
              <p>Ingen medlemmer på dette hold.</p>
            )}
          </Section>

          <Section>
            <h3>Om holdet</h3>
            <p>{team.about || 'Ingen beskrivelse tilgængelig.'}</p>
          </Section>
        </TeamContent>
      </PageContainer>
    </div>
  );
};

export default TeamPage;

import { useState, useEffect } from "react";
import { useParams, Link } from "react-router-dom";
import Navbar from "../../components/Navbar";
import styled from "styled-components";

const PageContainer = styled.div`
  margin: 0 auto;
  max-width: 1200px;
  padding: 20px;
`;

const TournamentHeader = styled.div`
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

const TournamentTitle = styled.h2`
  color: var(--color-text);
  font-family: "Xirod", sans-serif;
  margin: 0;
`;

const TournamentActions = styled.div`
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

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;

    &:hover {
      background-color: var(--color-button-general);
      color: var(--color-text);
    }
  }
`;

const ErrorMessage = styled.div`
  padding: 15px;
  margin-bottom: 20px;
  background-color: var(--color-warning);
  color: white;
  border-radius: 5px;
  text-align: center;
`;

const TeamsList = styled.div`
  margin-top: 20px;
`;

const TeamItem = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px;
  margin-bottom: 10px;
  background-color: var(--color-accent);
  border-radius: 8px;
  border: 1px solid var(--color-text);
  box-shadow: 0 0 10px rgba(87, 210, 255, 0.2);
  transition: transform 0.3s ease;

  &:hover {
    transform: translateY(-3px);
    box-shadow: 0 0 15px rgba(87, 210, 255, 0.4);
  }
`;

const TeamInfo = styled.div`
  h3 {
    color: var(--color-text);
    font-family: "Xirod", sans-serif;
    margin: 0;
    font-size: 18px;

    a {
      color: var(--color-text);
      text-decoration: none;
      transition: color 0.3s ease;

      &:hover {
        color: white;
      }
    }
  }
`;

const TeamActions = styled.div`
  span {
    display: inline-block;
    padding: 6px 12px;
    border-radius: 4px;
    font-size: 14px;
    color: white;

    &.full {
      background-color: var(--color-warning);
    }

    &.signed {
      background-color: var(--color-accept);
    }
  }
`;

const NoTeamsMessage = styled.div`
  text-align: center;
  padding: 30px;
  color: var(--color-text);
  background-color: var(--color-accent);
  border-radius: 8px;
  border: 1px solid var(--color-text);
  margin-top: 20px;
`;

const LoadingMessage = styled.div`
  color: var(--color-text);
  font-size: 18px;
  margin-top: 40px;
  text-align: center;
  padding: 20px;
`;

const TournamentPage = () => {
  const { tournamentId } = useParams();
  const [tournament, setTournament] = useState(null);
  const [loading, setLoading] = useState(true);
  const [hasTeam, setHasTeam] = useState(false);
  const [teams, setTeams] = useState([]);
  const [isInTournament, setIsInTournament] = useState(false);
  const [showError, setShowError] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    // Simulate API call to fetch tournament data
    setTimeout(() => {
      // Mock data
      const mockTournament = {
        id: tournamentId,
        name: "Tournament Name",
        teams: [
          { id: 1, name: "Team 1", isFull: false, isSigned: false },
          { id: 2, name: "Team 2", isFull: false, isSigned: false },
          { id: 3, name: "Team 3", isFull: true, isSigned: false },
          { id: 4, name: "Team 4", isFull: false, isSigned: false },
          { id: 5, name: "Team 5", isFull: false, isSigned: false },
          { id: 6, name: "Team 6", isFull: false, isSigned: false },
        ],
      };

      setTournament(mockTournament);
      setTeams(mockTournament.teams);
      setLoading(false);

      // For demo purposes.
      setHasTeam(Math.random() > 0.5);
      setIsInTournament(Math.random() > 0.5);
    }, 500);
  }, [tournamentId]);

  const handleSignUpTeam = (teamId) => {
    if (!hasTeam) {
      setShowError(true);
      setErrorMessage("Du skal være medlem af et hold for at tilmelde dig.");
      return;
    }

    // Simulate signing up a team
    setTeams(
      teams.map((team) =>
        team.id === teamId ? { ...team, isSigned: true } : team
      )
    );
    setIsInTournament(true);
  };

  const handleSendJoinRequest = () => {
    if (!hasTeam) {
      setShowError(true);
      setErrorMessage(
        "Du skal være medlem af et hold for at sende en anmodning."
      );
      return;
    }

    alert("Anmodning om at deltage i turnering sendt!");
  };

  if (loading) {
    return <LoadingMessage>Indlæser turnering...</LoadingMessage>;
  }

  // Check if all teams are full
  const allTeamsFull = teams.every((team) => team.isFull);

  return (
    <div>
      <PageContainer>
        <TournamentHeader>
          <TournamentTitle>{tournament.name}</TournamentTitle>
          <TournamentActions>
            <Button onClick={handleSendJoinRequest} disabled={isInTournament}>
              Send "Join Hold" anmodning
            </Button>
          </TournamentActions>
        </TournamentHeader>

        {showError && <ErrorMessage>{errorMessage}</ErrorMessage>}

        <TeamsList>
          {allTeamsFull ? (
            <NoTeamsMessage>
              <p>Der er i øjeblikket ingen ledige holdpladser.</p>
            </NoTeamsMessage>
          ) : (
            teams.map((team) => (
              <TeamItem key={team.id}>
                <TeamInfo>
                  <h3>
                    <Link to={`/team/${team.id}`}>{team.name}</Link>
                  </h3>
                </TeamInfo>
                <TeamActions>
                  {!team.isFull && !team.isSigned && (
                    <Button
                      onClick={() => handleSignUpTeam(team.id)}
                      disabled={isInTournament}
                    >
                      Tilmeld
                    </Button>
                  )}
                  {team.isFull && <span className="full">Fuldt</span>}
                  {team.isSigned && <span className="signed">Tilmeldt</span>}
                </TeamActions>
              </TeamItem>
            ))
          )}
        </TeamsList>
      </PageContainer>
    </div>
  );
};

export default TournamentPage;

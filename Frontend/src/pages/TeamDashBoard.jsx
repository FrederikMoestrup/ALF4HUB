import styled from "styled-components";
import ShowTeamInfo from "../components/ShowTeamInfo.jsx";
import React, { useEffect, useState } from "react";
import apiFacade from "../util/apiFacade.js";

const HeaderSection = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const Container = styled.div`
  padding: 20px;
  text-align: center;
  max-width: 1280px;
  margin: 0 auto;
`;

const Title = styled.h1`
  font-size: 28px;
`;


const PlayersWrapper = styled.div`
  display: flex;
  justify-content: center;
  gap: 20px;
  flex-wrap: wrap;
`;

const InviteCard = styled.div`
  background: #e0e0e0;
  padding: 15px;
  border-radius: 10px;
  width: 180px;
  height: 260px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

const InvitePlus = styled.div`
  font-size: 100px;
  margin-bottom: 15px;
  color: #F3F3F3;
`;

const InviteButton = styled.button`
  background-color: #5CFF69;
  border: none;
  padding: 8px 15px;
  color: black;
  font-weight: bold;
  border-radius: 5px;
  cursor: pointer;
`;
const GameName = styled.h2`
  font-size: 48px;
  font-weight: 900;
  font-family: 'Arial', sans-serif;
  color: orange;
  -webkit-text-stroke: 1px black; 
  text-align: left;
  margin: 0;
`;

const TeamName = styled.h3`
  font-size: 24px;
  color: black;
  margin-top: 20px;
  margin-bottom: 30px;
`;

const GameBox = styled.div`
  background-color: #a3a3a3;
  width: 1222px;
  height: 104px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  justify-content: flex-start;
  padding-left: 40px;
  border-radius: 10px;
  margin-bottom: 20px;
`;

function TeamDashBoard() {
  const [team, setTeam] = useState(null);
  const [currentUser, setCurrentUser] = useState(null)

  
  useEffect(() => {
    const fetchTeams = async () => {
      try {
        const teamsData = await apiFacade.getAllTeams();
  
        if (teamsData.length > 0) {
          const team = teamsData[0];
          setTeam(team);
          setCurrentUser(team.teamCaptain);  
        }
  
      } catch (error) {
        console.error('Failed to fetch teams:', error);
      }
    };
  
    fetchTeams();
  }, []);
  

  if (!team) {
    return <Container><Title>No teams for the captain.</Title></Container>;
  }

  const renderPlayers = () => {
    if (!team || team.teamAccounts.length === 0) {
      return <div>No Players on the team.</div>;
    }

    const playerCards = team.teamAccounts.slice(0, 5).map(account => (
      <ShowTeamInfo 
        key={account.id} 
        team={{
          teamCaptain: team.teamCaptain,
          rank: account.rank,
          userName: account.user?.username ?? "Unknown",

        }}
        teamCaptain={false}
      />
    ));

    if (team.teamAccounts.length < 5) {
      playerCards.push(
        <InviteCard key="invite">
          <InvitePlus>+</InvitePlus>
          <InviteButton>Invite</InviteButton>
        </InviteCard>
      );
    }

    return playerCards;
  };
  return (
    <Container>
      <Title>Team Dashboard</Title>
      <HeaderSection>
        <GameBox>
          <GameName>{team.game}</GameName>
        </GameBox>
        <TeamName>{team.teamName}</TeamName>
      </HeaderSection>

      <PlayersWrapper>
        {renderPlayers()}
      </PlayersWrapper>
    </Container>
  );
}

export default TeamDashBoard;
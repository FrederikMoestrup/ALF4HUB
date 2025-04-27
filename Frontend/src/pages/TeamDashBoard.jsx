import styled from "styled-components";
import ShowTeamInfo from "../components/ShowTeamInfo.jsx";
import React, { useEffect, useState } from "react";

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
  const [players, setPlayers] = useState([]);

  useEffect(() => {
    const playersData = [
      {
        id: 1,
        playAccountName: "Pizza Time",
        game: "CS2",
        rank: "8.5",
        team : "HotChilliPeppers",
        isCaptain: false,
        isActive: true
      },
      {
        id: 2,
        playAccountName: "Stal",
        game: "CS2",
        rank: "6.2",
        team : "HotChilliPeppers",
        isCaptain: true,
        isActive: false
      },
      {
        id: 3,
        playAccountName: "bing_bong",
        game: "CS2",
        rank: "5.6",
        team : "BestOfTheBest",
        isCaptain: false,
        isActive: true
      },
      {
        id: 4,
        playAccountName: "Orson",
        game: "CS2",
        rank: "4.8",
        team : "HotChilliPeppers",
        isCaptain: false,
        isActive: true
      }
    ];

    setPlayers(playersData);
  }, []);

  const renderPlayers = () => {
    const captain = players.find(player => player.isCaptain);
    const currentTeam = captain ? captain.team : "";
  
    const teamPlayers = players.filter(player => player.team === currentTeam);
  
    const playerCards = teamPlayers.slice(0, 5).map((player, index) => (
      <ShowTeamInfo key={player.id} player={player} isCaptain={index === 5} />
    ));
  
    if (teamPlayers.length < 5) {
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

{players.length > 0 && (
  <>
  <HeaderSection>
    <GameBox>
      <GameName>{players[0].game}</GameName>
    </GameBox>

    <TeamName> {players[0].team}</TeamName> 
    </HeaderSection>
  </>
)}

<PlayersWrapper>
  {renderPlayers()}
</PlayersWrapper>
    </Container>
  );
}

export default TeamDashBoard;
import React from "react";
import styled from "styled-components";

const PlayerCard = styled.div`
  background:rgb(161, 157, 157);
  padding: 15px;
  border-radius: 10px;
  width: 180px;
  display: flex;
  flex-direction: column;
  align-items: center;
  position: relative;
  opacity: ${props => (props.isActive ? "1" : "0.5")};
  transition: opacity 0.3s ease;
`;

const PlayerImage = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 50%;
  object-fit: cover;
`;

const Crown = styled.div`
  position: absolute;
  top: -15px;
  left: 50%;
  transform: translateX(-50%);
  font-size: 24px;
`;

const PlayerName = styled.div`
  margin-top: 10px;
  font-weight: bold;
`;


const RankCircle = styled.div`
  margin-top: 10px;
  width: 40px;
  height: 40px;
  border: 2px solid black;
  border-radius: 50%;
  font-size: 14px;
  font-weight: bold;
  color: black;
  display: flex;
  align-items: center;
  justify-content: center;
`;

const RemoveButton = styled.button`
  background-color:rgb(219, 88, 88);
  border: none;
  padding: 8px 15px;
  color: white;
  border-radius: 5px;
  margin-top: 10px;
  cursor: pointer;
`;

const ShowTeamInfo = ({player, isCaptain}) =>{
    
    return (
        <PlayerCard isActive={player.isActive}>
          {isCaptain && <Crown>👑</Crown>}
          <PlayerImage img="./" alt="" />
          <PlayerName>{player.playAccountName}</PlayerName>
          <RankCircle>{player.rank}</RankCircle>
          <RemoveButton>Remove</RemoveButton>
        </PlayerCard>
      );
};
export default ShowTeamInfo
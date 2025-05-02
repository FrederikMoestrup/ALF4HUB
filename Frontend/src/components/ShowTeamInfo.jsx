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


const RankRectangle  = styled.div`
margin-top: 10px;
  padding: 6px 12px;
  border: 2px solid black;
  border-radius: 8px;
  font-size: 14px;
  font-weight: bold;
  color: black;
  display: inline-block;
  white-space: nowrap;
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

const ShowTeamInfo = ({team, isCaptain}) =>{

    
    return (
        <PlayerCard >
          {isCaptain && <Crown>👑</Crown>}
          <PlayerImage src="https://via.placeholder.com/100" alt="Player" />
          <PlayerName>{team.playAccountName}</PlayerName>
          <RankRectangle>{team.rank}</RankRectangle>
          <RemoveButton>Remove</RemoveButton>
        </PlayerCard>
      );
};
export default ShowTeamInfo
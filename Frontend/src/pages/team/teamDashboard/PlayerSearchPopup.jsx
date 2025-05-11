import { useState, useEffect } from "react";
import styled from "styled-components";
import playerApi from "../../../util/apiFacade";
import AddPlayerButton from "../../../components/AddPlayerButton";

const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.7);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;

const PopupContent = styled.div`
  background: #fff;
  border-radius: 12px;
  padding: 30px;
  width: 70%;
  max-width: 600px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
  display: flex;
  flex-direction: column;
  align-items: center;
  transition: all 0.3s ease-in-out;
  margin-top: 50px;
  margin-bottom: 50px;
  max-height: 80%;
  position: relative;
`;

const CloseButton = styled.button`
  background: transparent;
  border: none;
  font-size: 20px;
  color: #333;
  cursor: pointer;
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 10px;
  &:hover {
    color: #f00;
  }
`;

const Title = styled.h2`
  font-size: 24px;
  color: #333;
  margin-bottom: 20px;
`;

const SearchInput = styled.input`
  width: 100%;
  padding: 10px;
  border-radius: 8px;
  border: 1px solid #ccc;
  margin-bottom: 10px;
  font-size: 16px;
`;

const UserListContainer = styled.div`
  width: 100%;
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 15px;
  max-height: 300px;
  overflow-y: auto;
`;

const UserItem = styled.div`
  background-color: #f9f9f9;
  padding: 12px;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  transition: background-color 0.2s ease;
  color: #000;
`;

const PlayerSearchPopup = ({ onClose, onSelectPlayer, teamId }) => {
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(false);
  const [players, setPlayers] = useState([]);
  const [filteredPlayers, setFilteredPlayers] = useState([]);
  const [teamPlayers, setTeamPlayers] = useState([]);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchPlayersAndTeamPlayers = async () => {
      setLoading(true);
      setError(null);

      try {
        const playerData = await playerApi.searchPlayers();
        setPlayers(playerData || []);
        setFilteredPlayers(playerData || []);

        const teamData = await playerApi.getAllTeams();
        const team = teamData.find((t) => t.id === teamId);
        if (team && team.teamAccounts) {
          setTeamPlayers(team.teamAccounts);
        }
      } catch (err) {
        console.error("Error fetching players or team:", err);
        setError("Failed to fetch player accounts or team data");
      } finally {
        setLoading(false);
      }
    };

    fetchPlayersAndTeamPlayers();
  }, [teamId]);

  useEffect(() => {
    if (!searchTerm) {
      setFilteredPlayers(
        players.filter(
          (player) =>
            !teamPlayers.some((teamPlayer) => teamPlayer.id === player.id)
        )
      );
    } else {
      const filtered = players.filter(
        (player) =>
          player.playerAccountName
            .toLowerCase()
            .includes(searchTerm.toLowerCase()) &&
          !teamPlayers.some((teamPlayer) => teamPlayer.id === player.id)
      );
      setFilteredPlayers(filtered);
    }
  }, [searchTerm, players, teamPlayers]);

  return (
    <Overlay>
      <PopupContent>
        <CloseButton onClick={onClose}>X Close</CloseButton>
        <Title>Search Player</Title>
        <SearchInput
          type="text"
          placeholder="Search for player..."
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
        />
        {loading ? (
          <p>Loading players...</p>
        ) : error ? (
          <p style={{ color: "red" }}>{error}</p>
        ) : (
          <UserListContainer>
            {filteredPlayers.length > 0 ? (
              filteredPlayers.map((player) => (
                <UserItem key={player.id}>
                  <div>
                    {player.playerAccountName} (Rank: {player.rank || "N/A"})
                  </div>
                  <AddPlayerButton
                    playerAccount={player}
                    teamId={teamId}
                    onSuccess={() => {
                      onSelectPlayer(player);
                      onClose();
                    }}
                  />
                </UserItem>
              ))
            ) : (
              <UserItem>No players found</UserItem>
            )}
          </UserListContainer>
        )}
      </PopupContent>
    </Overlay>
  );
};

export default PlayerSearchPopup;

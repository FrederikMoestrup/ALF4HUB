import React, { useState } from 'react';
import PlayerSearchPopup from '../components/PlayerSearchPopup';

const TestPage = () => {
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [selectedPlayer, setSelectedPlayer] = useState(null);

  const openPopup = () => setIsPopupOpen(true);
  const closePopup = () => setIsPopupOpen(false);

  const handleSelectPlayer = (player) => {
    setSelectedPlayer(player);
    console.log('Selected player:', player);
    closePopup();
  };

  return (
    <div>
      <h1>Test Player Search</h1>
      <button onClick={openPopup}>Search Player</button>
      {isPopupOpen && (
        <PlayerSearchPopup
          onClose={closePopup}
          onSelectPlayer={handleSelectPlayer}
        />
      )}
      {selectedPlayer && (
        <div>
          <h3>Selected Player:</h3>
          <p>Name: {selectedPlayer.playAccountName}</p>
          <p>Rank: {selectedPlayer.rank}</p>
        </div>
      )}
    </div>
  );
};

export default TestPage;

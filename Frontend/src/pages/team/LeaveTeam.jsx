import React, { useState, useEffect } from "react";
import "../../styles/LeaveTeam.css";

function LeaveTeam({ playerId: propPlayerId, teamId: propTeamId }) {
  const [showPopup, setShowPopup] = useState(false);
  const [successMessage, setSuccessMessage] = useState(false);
  const [playerId, setPlayerId] = useState(null);
  const [teamId, setTeamId] = useState(null);

  useEffect(() => {
    const storedPlayerId = localStorage.getItem("playerId");
    const storedTeamId = localStorage.getItem("teamId");
    setPlayerId(propPlayerId || storedPlayerId);
    setTeamId(propTeamId || storedTeamId);
  }, [propPlayerId, propTeamId]);

  const togglePopup = () => {
    setShowPopup(!showPopup);
  };

  const handleLeaveTeam = async () => {
    try {
      const response = await fetch(
        `http://localhost:7070/api/${playerId}/leave-team/${teamId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      if (response.ok) {
        console.log("Successfully left the team.");
        setSuccessMessage(true);
        setShowPopup(false);
      } else {
        const errorText = await response.text();
        console.error("Failed to leave team:", errorText);
        alert("Noget gik galt: " + errorText);
      }
    } catch (error) {
      console.error("Error while leaving team:", error);
      alert("Der opstod en fejl ved kontakt til serveren.");
    }
  };

  if (!playerId || !teamId) {
    return (
      <div className="error-box">
        <p>Mangler spiller- eller holdoplysninger...</p>
      </div>
    );
  }

  return (
    <div className="container">
      {successMessage ? (
        <div className="success-message">
          <p>Du har nu forladt holdet!</p>
        </div>
      ) : (
        <>
          <button onClick={togglePopup} className="open-button">
            Forlad hold
          </button>

          {showPopup && (
            <div className="popup-overlay">
              <div className="popup">
                <button onClick={togglePopup} className="close-x">
                  &times;
                </button>
                <p>Er du sikker på, at du vil forlade dit hold?</p>
                <p>Denne handling kan ikke fortrydes.</p>
                <button onClick={handleLeaveTeam} className="close-button">
                  Forlad holdet
                </button>
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}

export default LeaveTeam;

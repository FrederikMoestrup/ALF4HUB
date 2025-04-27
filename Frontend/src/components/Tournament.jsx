import React, { useState } from "react";
import "../css/Tournament.css";

function Tournament() {
  const [showPopup, setShowPopup] = useState(false);
  const [successMessage, setSuccessMessage] = useState(false);

  const togglePopup = () => {
    setShowPopup(!showPopup);
  };

  const handleLeaveTeam = () => {
    // Tom for nu, så vi kan tilføje funktionen ved at efterlade et hold
    console.log("Leaving team... (empty function)"); 
    setSuccessMessage(true); 
    setShowPopup(false); 
  };

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
                <br />
                <p>Er du sikker på at du vil forlade dit hold?</p>
                <p>Denne handling kan ikke fortrydes</p>
                <br />
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

export default Tournament;

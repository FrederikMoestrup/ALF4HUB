import { useState } from "react";
import apiFacade from "../../../util/apiFacade";

function AddPlayerButton({ playerAccount, teamId, onSuccess }) {
  const [added, setAdded] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleAdd = async () => {
    const confirmAdd = window.confirm(
      `Are you sure you want to add "${
        playerAccount.playerAccountName || "this player"
      }" to the team?`
    );
    if (!confirmAdd) return;

    try {
      await apiFacade.addPlayerToTeam(teamId, playerAccount.id);
      setAdded(true);
      setErrorMessage("");
      if (onSuccess) onSuccess();
    } catch (error) {
      setErrorMessage(error.message || "Failed to add player to team");
    }
  };

  if (added) {
    return (
      <div style={{ color: "green", marginTop: "10px" }}>
        Player added to team.
      </div>
    );
  }

  return (
    <div>
      <button
        onClick={handleAdd}
        style={{
          padding: "10px 20px",
          backgroundColor: "green",
          color: "white",
          border: "none",
          borderRadius: "8px",
          cursor: "pointer",
        }}
      >
        Add to Team
      </button>

      {errorMessage && (
        <div style={{ color: "red", marginTop: "10px" }}>{errorMessage}</div>
      )}
    </div>
  );
}

export default AddPlayerButton;

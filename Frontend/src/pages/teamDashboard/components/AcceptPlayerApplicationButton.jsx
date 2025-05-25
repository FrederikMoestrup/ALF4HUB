import { useState } from "react";
import apiFacade from "../../../util/apiFacade";

function AcceptPlayerApplicationButton({ playerAccount, teamId, onSuccess }) {
  const [accepted, setAccepted] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const handleAccept = async () => {
    const confirm = window.confirm(
      `Acceptér ansøgning fra ${playerAccount.playerAccountName}?`
    );
    if (!confirm) return;

    try {
      await apiFacade.acceptPlayerApplication(teamId, playerAccount.id);
      setAccepted(true);
      if (onSuccess) onSuccess();
    } catch (err) {
      setErrorMessage(err.message || "Fejl ved accept");
    }
  };

  if (accepted)
    return <div style={{ color: "green" }}>✅ Ansøgning accepteret</div>;

  return (
    <div>
      <button
        onClick={handleAccept}
        style={{
          padding: "8px 12px",
          backgroundColor: "#007acc",
          color: "white",
          border: "none",
          borderRadius: "5px",
          marginTop: "5px",
          cursor: "pointer",
        }}
      >
        Acceptér ansøgning
      </button>

      {errorMessage && <div style={{ color: "red" }}>{errorMessage}</div>}
    </div>
  );
}

export default AcceptPlayerApplicationButton;

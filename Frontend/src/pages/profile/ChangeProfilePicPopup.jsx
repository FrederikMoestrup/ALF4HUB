import React, { useState } from "react";
import styled from "styled-components";

// Styled Components
const Overlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

const Popup = styled.div`
  background: #fff;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 2px 16px rgba(0,0,0,0.2);
  min-width: 320px;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  color: black;
`;

const Input = styled.input`
  padding: 0.5rem;
  border: 1px solid #ccc;
  border-radius: 6px;
  font-size: 1rem;
`;

const ButtonRow = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: 0.5rem;
`;

const Button = styled.button`
  padding: 0.5rem 1.2rem;
  border: none;
  border-radius: 6px;
  background: ${(props) => (props.primary ? "#007bff" : "#eee")};
  color: ${(props) => (props.primary ? "#fff" : "#333")};
  cursor: pointer;
  font-size: 1rem;
  &:hover {
    background: ${(props) => (props.primary ? "#0056b3" : "#ddd")};
  }
`;

const ErrorMsg = styled.div`
  color: #d32f2f;
  font-size: 0.95rem;
`;

export default function ChangeProfilePicPopup({
  isOpen,
  onClose,
  onSuccess,
  userId, 
}) {
  const [url, setUrl] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  if (!isOpen) return null;

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
  
    // Basic URL validation
    if (!/^https?:\/\/.+\.(jpg|jpeg|png|gif)$/i.test(url)) {
      setError("Indtast et gyldigt billede URL (jpg, jpeg, png eller gif).");
      setLoading(false);
      return;
    }
  
    try {
      const token = localStorage.getItem("token");
      
      const res = await fetch(`http://localhost:7070/api/users/${userId}/profile_picture`, {
        method: "PUT",
        headers: { 
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify({ profilePicture: url }),
      });
  
      if (!res.ok) {
        const errorData = await res.json();
        throw new Error(errorData.message || "Fejl ved opdatering af profilbillede.");
      }
  
      setLoading(false);
      setUrl("");
      if (onSuccess) onSuccess(url);
      onClose();
    } catch (err) {
      setError(err.message || "Noget gik galt.");
      setLoading(false);
    }
  };
  
  return (
    <Overlay>
      <Popup>
        <h2>Skift profilbillede</h2>
        <form onSubmit={handleSubmit}>
          <label>
            Billede URL:
            <Input
              type="url"
              value={url}
              onChange={(e) => setUrl(e.target.value)}
              placeholder="https://example.com/image.jpg"
              required
              disabled={loading}
            />
          </label>
          {error && <ErrorMsg>{error}</ErrorMsg>}
          <ButtonRow>
            <Button type="button" onClick={onClose} disabled={loading}>
              Annuller
            </Button>
            <Button type="submit" primary disabled={loading}>
              {loading ? "Gemmer..." : "Gem"}
            </Button>
          </ButtonRow>
        </form>
      </Popup>
    </Overlay>
  );
}

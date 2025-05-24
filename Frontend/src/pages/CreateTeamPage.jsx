import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";
import apiFacade from "../util/apiFacade";

const PageContainer = styled.div`
  margin: 0 auto;
  max-width: 800px;
  padding: 20px;
`;

const Header = styled.div`
  margin-bottom: 30px;
`;

const Title = styled.h1`
  color: var(--color-text);
  font-family: "Xirod", sans-serif;
`;

const FormContainer = styled.div`
  background-color: var(--color-main);
  border: 1px solid var(--color-main);
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 0 15px rgba(87, 210, 255, 0.3);
`;

const FormGroup = styled.div`
  margin-bottom: 20px;
`;

const Label = styled.label`
  display: block;
  color: var(--color-text);
  margin-bottom: 8px;
  font-weight: bold;
`;

const Input = styled.input`
  width: 100%;
  padding: 10px;
  background-color: var(--color-main);
  border: 1px solid var(--color-text-accent);
  border-radius: 5px;
  color: var(--color-text);
  font-family: "Kdam Thmor Pro", sans-serif;

  &:focus {
    outline: none;
    box-shadow: 0 0 5px rgba(87, 210, 255, 0.5);
  }
`;

const Button = styled.button`
  background-color: var(--color-button-general);
  color: var(--color-text);
  border: 1px solid var(--color-button-general);
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-family: "Kdam Thmor Pro", sans-serif;
  font-size: 16px;
  transition: all 0.3s ease;

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;
const CancelButton = styled(Button)`
  &:hover {
    background-color: var(--color-warning);
    color: var(--color-main);
  }
`;

const SubmitButton = styled(Button)`
  &:hover {
    background-color: var(--color-accept);
    color: var(--color-main);
  }
`;

const ErrorMessage = styled.div`
  color: var(--color-warning);
  font-size: 14px;
  margin-top: 5px;
`;

const ButtonsContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
`;

const CreateTeamPage = () => {
  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(true);
  const [loading, setLoading] = useState(true);
  const [teamName, setTeamName] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  // Check if the user is logged in
  useEffect(() => {
    const checkLogin = async () => {
      const userId = await apiFacade.getUserId();
      if (!userId) {
        setIsLoggedIn(false);
      }
      setLoading(false);
    };
    checkLogin();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();

    const trimmedName = teamName.trim();

    // check not empty
    if (trimmedName.trim() === "") {
      setError("Holdnavn må ikke være tomt");
      return;
    }

    setError("");
    setIsSubmitting(true);

    const token = localStorage.getItem("token");

    try {
      // Get the user ID from the token
      const userId = await apiFacade.getUserId();

      // API call to create team with the correct endpoint
      const response = await fetch(
        `http://localhost:7070/api/teams/team-captain/${userId}`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            teamName: trimmedName,
          }),
        }
      );

      if (!response.ok) {
        let message = "Noget gik galt ved oprettelsen af holdet";

        try {
          const errorText = await response.text();

          try {
            const errorResponse = JSON.parse(errorText);
            // Check if the error response has a message or warning
            message = errorResponse.message || errorResponse.warning || message;
          } catch {
            message = errorText || message;
          }
        } catch {
          // fallback
        }

        throw new Error(message);
      }

      setTeamName("");
      navigate("/teams");
    } catch (error) {
      console.error("Fejl:", error.message);
      setError(error.message);
    } finally {
      setIsSubmitting(false);
    }
  };

  // if user is not logged in
  if (loading) return null;

  if (!isLoggedIn) {
    return (
      <PageContainer>
        <FormContainer>
          <ErrorMessage>
            Du skal være logget ind for at oprette et hold.
          </ErrorMessage>
        </FormContainer>
      </PageContainer>
    );
  }
  // if user is logged in
  return (
    <div>
      <PageContainer>
        <Header>
          <Title>OPRET HOLD</Title>
        </Header>

        <FormContainer>
          <form onSubmit={handleSubmit}>
            <FormGroup>
              <Label htmlFor="teamName">Holdnavn</Label>
              <Input
                type="text"
                id="teamName"
                value={teamName}
                onChange={(e) => setTeamName(e.target.value)}
                placeholder="Indtast holdnavn"
              />
            </FormGroup>

            {error && <ErrorMessage>{error}</ErrorMessage>}

            <ButtonsContainer>
              <CancelButton type="button" onClick={() => navigate("/teams")}>
                Annuller
              </CancelButton>

              <SubmitButton type="submit" disabled={isSubmitting}>
                {isSubmitting ? "Opretter..." : "Opret Hold"}
              </SubmitButton>
            </ButtonsContainer>
          </form>
        </FormContainer>
      </PageContainer>
    </div>
  );
};

export default CreateTeamPage;

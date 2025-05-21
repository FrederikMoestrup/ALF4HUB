import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Navbar from '../components/Navbar';
import styled from 'styled-components';

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
  font-family: 'Xirod', sans-serif;
`;

const FormContainer = styled.div`
  background-color: var(--color-accent);
  border: 1px solid var(--color-text);
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
  border: 1px solid var(--color-text);
  border-radius: 5px;
  color: var(--color-text);
  font-family: 'Kdam Thmor Pro', sans-serif;
  
  &:focus {
    outline: none;
    box-shadow: 0 0 5px rgba(87, 210, 255, 0.5);
  }
`;

const Select = styled.select`
  width: 100%;
  padding: 10px;
  background-color: var(--color-main);
  border: 1px solid var(--color-text);
  border-radius: 5px;
  color: var(--color-text);
  font-family: 'Kdam Thmor Pro', sans-serif;
  
  &:focus {
    outline: none;
    box-shadow: 0 0 5px rgba(87, 210, 255, 0.5);
  }
`;

const RadioGroup = styled.div`
  display: flex;
  gap: 20px;
`;

const RadioLabel = styled.label`
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--color-text);
  cursor: pointer;
`;

const RadioInput = styled.input`
  cursor: pointer;
  accent-color: var(--color-text);
`;

const Button = styled.button`
  background-color: var(--color-button-general);
  color: var(--color-text);
  border: 1px solid var(--color-text);
  padding: 10px 20px;
  border-radius: 5px;
  cursor: pointer;
  font-family: 'Kdam Thmor Pro', sans-serif;
  font-size: 16px;
  transition: all 0.3s ease;
  
  &:hover {
    background-color: var(--color-text);
    color: var(--color-main);
  }
  
  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
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
  const [teamName, setTeamName] = useState('');
  const [error, setError] = useState('');
  const [nameExists, setNameExists] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [teams, setTeams] = useState([]);

  // Fetch existing teams to check for name availability
  useEffect(() => {
    const fetchTeams = async () => {
      try {
        const response = await fetch('http://localhost:7070/api/teams');
        const data = await response.json();
        setTeams(data);
      } catch (error) {
        console.error('Error fetching teams:', error);
      }
    };

    fetchTeams();
  }, []);

  // Check if team name already exists
  useEffect(() => {
    if (teamName.trim() === '') {
      setNameExists(false);
      return;
    }
    
    const exists = teams.some(team => 
      team.teamName && team.teamName.toLowerCase() === teamName.toLowerCase()
    );
    
    setNameExists(exists);
  }, [teamName, teams]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    // Validate form
    if (teamName.trim() === '') {
      setError('Holdnavn må ikke være tomt');
      return;
    }
    
    if (nameExists) {
      setError('Dette holdnavn er allerede i brug');
      return;
    }
    
    setError('');
    setIsSubmitting(true);
    
    try {
      // Get current user (for team captain)
      const mockUser = {
        id: 1,
        username: 'currentUser'
      };
      
      // Create team object according to the API structure
      const newTeam = {
        teamName: teamName,
        teamCaptain: {
          id: mockUser.id,
          username: mockUser.username
        },
        teamAccounts: [],
        tournamentTeams: []
      };
      
      // API call to create team
      const response = await fetch('http://localhost:7070/api/teams', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newTeam)
      });
      
      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Kunne ikke oprette hold');
      }
      
      const data = await response.json();
      console.log('Response status:', response.status);
      console.log('Full response data:', data);
      
      // Redirect to the teams page
      navigate('/teams');
    } catch (error) {
      console.error('Error creating team:', error);
      setError(`Der opstod en fejl: ${error.message}`);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <div>
      <Navbar />
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
              {nameExists && (
                <ErrorMessage>Dette holdnavn er allerede i brug</ErrorMessage>
              )}
            </FormGroup>
            
            {error && <ErrorMessage>{error}</ErrorMessage>}
            
            <ButtonsContainer>
              <Button type="button" onClick={() => navigate('/teams')}>
                Annuller
              </Button>
              <Button type="submit" disabled={isSubmitting || nameExists}>
                {isSubmitting ? 'Opretter...' : 'Opret Hold'}
              </Button>
            </ButtonsContainer>
          </form>
        </FormContainer>
      </PageContainer>
    </div>
  );
};

export default CreateTeamPage; 
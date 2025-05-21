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
  const [isPrivate, setIsPrivate] = useState(false);
  const [selectedGame, setSelectedGame] = useState('');
  const [selectedTournament, setSelectedTournament] = useState('');
  const [tournaments, setTournaments] = useState([]);
  const [error, setError] = useState('');
  const [nameExists, setNameExists] = useState(false);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [teams, setTeams] = useState([]);

  // Available games from the backend(i dont know if there is more.)
  const games = [
    { value: 'COUNTER_STRIKE', label: 'Counter-Strike' },
    { value: 'LEAGUE_OF_LEGENDS', label: 'League of Legends' },
    { value: 'DOTA_2', label: 'Dota 2' },
    { value: 'VALORANT', label: 'Valorant' },
    { value: 'ROCKET_LEAGUE', label: 'Rocket League' }
  ];

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

  // Fetch tournaments
  useEffect(() => {
    const fetchTournaments = async () => {
      if (selectedGame) {
        try {
          const response = await fetch('http://localhost:7070/api/tournaments');
          const data = await response.json();
          // Filter tournaments by selected game
          const filteredTournaments = data.filter(tournament => 
            tournament.game === selectedGame
          );
          setTournaments(filteredTournaments);
        } catch (error) {
          console.error('Error fetching tournaments:', error);
          // Fallback to mock data if API fails
          setTournaments([
            { id: 1, tournamentName: 'Summer Championship 2025' },
            { id: 2, tournamentName: 'Winter League 2025' },
            { id: 3, tournamentName: 'Rocket Showdown' },
            { id: 4, tournamentName: 'Spring Arena 2025' },
            { id: 5, tournamentName: 'Battle Royale Masters' }
          ]);
        }
      } else {
        setTournaments([]);
      }
    };

    fetchTournaments();
  }, [selectedGame]);

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
    
    if (!selectedGame) {
      setError('Vælg venligst et spil');
      return;
    }
    
    setError('');
    setIsSubmitting(true);
    
    try {
      // Get current user (Authentication system should be implemented when i find out where it is)
      // For now we're just mocking a user
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
      
      // If a tournament is selected, add it to the team
      if (selectedTournament) {
        const selectedTournamentObj = tournaments.find(t => t.id === parseInt(selectedTournament));
        if (selectedTournamentObj) {
          newTeam.tournament = {
            id: selectedTournamentObj.id,
            tournamentName: selectedTournamentObj.tournamentName
          };
        }
      }
      
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
            
            <FormGroup>
              <Label>Holdtype</Label>
              <RadioGroup>
                <RadioLabel>
                  <RadioInput
                    type="radio"
                    name="privacy"
                    checked={!isPrivate}
                    onChange={() => setIsPrivate(false)}
                  />
                  Offentligt
                </RadioLabel>
                <RadioLabel>
                  <RadioInput
                    type="radio"
                    name="privacy"
                    checked={isPrivate}
                    onChange={() => setIsPrivate(true)}
                  />
                  Privat
                </RadioLabel>
              </RadioGroup>
            </FormGroup>
            
            <FormGroup>
              <Label htmlFor="game">Spil</Label>
              <Select
                id="game"
                value={selectedGame}
                onChange={(e) => setSelectedGame(e.target.value)}
              >
                <option value="">Vælg spil</option>
                {games.map(game => (
                  <option key={game.value} value={game.value}>
                    {game.label}
                  </option>
                ))}
              </Select>
            </FormGroup>
            
            <FormGroup>
              <Label htmlFor="tournament">Turnering</Label>
              <Select
                id="tournament"
                value={selectedTournament}
                onChange={(e) => setSelectedTournament(e.target.value)}
                disabled={!selectedGame}
              >
                <option value="">Vælg turnering (valgfrit)</option>
                {tournaments.map(tournament => (
                  <option key={tournament.id} value={tournament.id}>
                    {tournament.tournamentName}
                  </option>
                ))}
              </Select>
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
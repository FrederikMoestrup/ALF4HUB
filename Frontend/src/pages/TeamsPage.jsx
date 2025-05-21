import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';
import styled from 'styled-components';

const PageContainer = styled.div`
  margin: 0 auto;
  max-width: 1200px;
  padding: 20px;
`;

const Header = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
`;

const Title = styled.h1`
  color: var(--color-text);
  font-family: 'Xirod', sans-serif;
`;

const Actions = styled.div`
  display: flex;
  gap: 10px;
`;

const Button = styled(Link)`
  background-color: var(--color-button-general);
  color: var(--color-text);
  border: 1px solid var(--color-text);
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  text-decoration: none;
  display: inline-block;
  transition: all 0.3s ease;
  
  &:hover {
    background-color: var(--color-text);
    color: var(--color-main);
  }
`;

const FiltersContainer = styled.div`
  display: flex;
  justify-content: space-between;
  margin-bottom: 20px;
  
  @media (max-width: 768px) {
    flex-direction: column;
    gap: 10px;
  }
`;

const SearchInput = styled.input`
  background-color: var(--color-accent);
  border: 1px solid var(--color-text);
  color: var(--color-text);
  padding: 8px 12px;
  border-radius: 5px;
  width: 300px;
  
  &::placeholder {
    color: var(--color-text);
    opacity: 0.7;
  }
  
  &:focus {
    outline: none;
    box-shadow: 0 0 5px rgba(87, 210, 255, 0.5);
  }
`;

const FilterButton = styled.button`
  background-color: ${props => props.active ? 'var(--color-text)' : 'var(--color-button-general)'};
  color: ${props => props.active ? 'var(--color-main)' : 'var(--color-text)'};
  border: 1px solid var(--color-text);
  padding: 8px 16px;
  border-radius: 5px;
  cursor: pointer;
  transition: all 0.3s ease;
  
  &:hover {
    background-color: var(--color-text);
    color: var(--color-main);
  }
`;

const TeamsList = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
  gap: 20px;
`;

const TeamCard = styled.div`
  background-color: var(--color-accent);
  border: 1px solid var(--color-text);
  border-radius: 10px;
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 0 10px rgba(87, 210, 255, 0.2);
  
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 0 15px rgba(87, 210, 255, 0.4);
  }
`;

const TeamCardHeader = styled.div`
  display: flex;
  padding: 15px;
  border-bottom: 1px solid var(--color-text);
`;

const TeamLogo = styled.div`
  width: 50px;
  height: 50px;
  background-color: var(--color-main);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--color-text);
  font-size: 20px;
  border: 1px solid var(--color-text);
`;

const TeamInfo = styled.div`
  margin-left: 15px;
  
  h3 {
    color: var(--color-text);
    font-family: 'Xirod', sans-serif;
    font-size: 18px;
    margin-bottom: 5px;
  }
  
  p {
    color: var(--color-text);
    margin: 0;
    font-size: 14px;
    margin-bottom: 3px;
  }
`;

const TeamCardBody = styled.div`
  padding: 15px;
  
  p {
    color: var(--color-text);
    margin: 0;
  }
`;

const TeamCardFooter = styled.div`
  padding: 15px;
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid var(--color-text);
`;

const NoTeamsMessage = styled.div`
  text-align: center;
  padding: 30px;
  color: var(--color-text);
  grid-column: 1 / -1;
`;

const LoadingMessage = styled.div`
  color: var(--color-text);
  font-size: 18px;
  margin-top: 40px;
  text-align: center;
  padding: 20px;
  grid-column: 1 / -1;
`;

const TeamsPage = () => {
  const url = "http://localhost:7070/api/teams";
  const [teams, setTeams] = useState([]);
  const [filterActive, setFilterActive] = useState(false);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTeams = async () => {
      try {
        setLoading(true);
        const response = await fetch(url);
        const data = await response.json();
        console.log("Fetched teams:", data);
        setTeams(data);
      } catch (error) {
        console.error("Error fetching teams:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchTeams();
  }, []);

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  const toggleFilter = () => {
    setFilterActive(!filterActive);
  };

  const filteredTeams = teams.filter(team => {
    const name = team?.teamName ?? '';
    const matchesSearch = name.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesFilter = !filterActive || (team?.tournamentTeams?.length > 2);
    return matchesSearch && matchesFilter;
  });

  return (
    <div>
    
      <PageContainer>
        <Header>
          <Title>TEAMS</Title>
          <Actions>
            <Button to="/create-team">Opret hold</Button>
          </Actions>
        </Header>

        <FiltersContainer>
          <div>
            <SearchInput 
              type="text" 
              placeholder="Søg efter hold..." 
              value={searchTerm}
              onChange={handleSearch}
            />
          </div>
          <div>
            <FilterButton 
              active={filterActive}
              onClick={toggleFilter}
            >
              {filterActive ? 'Vis alle hold' : 'Vis erfarne hold'}
            </FilterButton>
          </div>
        </FiltersContainer>

        <TeamsList>
          {loading ? (
            <LoadingMessage>Indlæser hold...</LoadingMessage>
          ) : filteredTeams.length > 0 ? (
            filteredTeams.map(team => (
              <TeamCard key={team.id}>
                <TeamCardHeader>
                  <TeamLogo>
                    {team.logo || '🏆'}
                  </TeamLogo>
                  <TeamInfo>
                    <h3>{team.teamName || 'Unavngivet hold'}</h3>
                    <p>Kaptajn: {team.teamCaptain?.username || 'Ukendt'}</p>
                    <p>Medlemmer: {team.teamAccounts?.length || 0}</p>
                    <p>Turneringer: {team.tournamentTeams?.length || 0}</p>
                  </TeamInfo>
                </TeamCardHeader>
                <TeamCardBody>
                  <p>{team.description || 'Ingen beskrivelse tilgængelig.'}</p>
                </TeamCardBody>
                <TeamCardFooter>
                  <Button to={`/team/${team.id}`}>
                    Se hold
                  </Button>
                </TeamCardFooter>
              </TeamCard>
            ))
          ) : (
            <NoTeamsMessage>
              <p>Ingen hold matcher dine søgekriterier.</p>
            </NoTeamsMessage>
          )}
        </TeamsList>
      </PageContainer>
    </div>
  );
};

export default TeamsPage;
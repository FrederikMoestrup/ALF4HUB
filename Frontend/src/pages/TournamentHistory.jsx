import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  GlobalStyle,
  Container,
  Navbar,
  HomeButton,
  ProfileButton,
  NavLinks,
  Content,
  Section,
  SectionTitle,
  TournamentCard,
  TournamentList,
  ButtonContainer,
  Button,
  Footer,
} from "../../styles/TournamentHistoryStyles";

function TournamentHistory() {
  const [tournaments, setTournaments] = useState([]);
  const userId = localStorage.getItem("userid");

  useEffect(() => {
    if (!userId) return;
    fetch(`http://localhost:7070/api/tournaments/history/${userId}`)
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch tournaments");
        return res.json();
      })
      .then((data) => setTournaments(data))
      .catch((err) => console.error("Error:", err));
  }, [userId]);

  return (
    <>
      <GlobalStyle />
      <Container>
        <Navbar>
          <HomeButton>
            <a href="/">Home</a>
          </HomeButton>

          <NavLinks>
            <NavLink to="/teams">Teams</NavLink>
            <NavLink to="/tournaments">Tournaments</NavLink>
            <NavLink to="/blogposts">Blog</NavLink>
          </NavLinks>

          <ProfileButton>
            <a href="/profile">Profile</a>
          </ProfileButton>
        </Navbar>

        <Content>
          <ButtonContainer>
            <NavLink to="/tournaments">
              <Button>Back to tournaments</Button>
            </NavLink>
            <NavLink to="/createtournament">
              <Button>Create new</Button>
            </NavLink>
          </ButtonContainer>

          <Section>
            <SectionTitle>My Tournament History</SectionTitle>
            <TournamentList>
              {tournaments.length === 0 ? (
                <p>No past tournaments found.</p>
              ) : (
                tournaments.map((tournament) => (
                  <TournamentCard key={tournament.id}>
                    <h3>{tournament.name}</h3>
                    <p>
                      {new Date(tournament.startDate).toLocaleDateString()} -{" "}
                      {new Date(tournament.endDate).toLocaleDateString()}
                    </p>
                    <small>Status: {tournament.status}</small>
                  </TournamentCard>
                ))
              )}
            </TournamentList>
          </Section>
        </Content>

        <Footer>
          <p>
            © Altf4hub | Firskovvej 18 2800 Lyngby | CVR-nr. 10101010 | Tlf: 36
            15 45 04 | Mail: turnering@altf4hub.dk
          </p>
        </Footer>
      </Container>
    </>
  );
}

export default TournamentHistory;

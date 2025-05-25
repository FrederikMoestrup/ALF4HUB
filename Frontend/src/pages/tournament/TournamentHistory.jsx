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

  /* FOR REAL DATA
  const userId = localStorage.getItem("userid");

  useEffect(() => {
    if (!userId) return;
    fetch(`http://localhost:7070/api/tournaments/user/${userId}`)
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch tournaments");
        return res.json();
      })
      .then((data) => setTournaments(data))
      .catch((err) => console.error("Error:", err));
  }, [userId]);
  */

  useEffect(() => {
    // Her mocker vi bare nogle dummy-turneringer direkte
    const dummyData = [
      {
        id: 1,
        name: "Dummy Tournament A",
        startDate: "2024-01-15",
        endDate: "2024-01-20",
        status: "COMPLETED",
      },
      {
        id: 2,
        name: "Dummy Tournament B",
        startDate: "2024-02-10",
        endDate: "2024-02-15",
        status: "CANCELLED",
      },
    ];

    setTournaments(dummyData);
  }, []);

  return (
    <>
      <GlobalStyle />
      <Container>
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
                  <TournamentCard
                    key={tournament.id}
                    style={{
                      backgroundColor: "#f9f9f9",
                      padding: "20px",
                      borderRadius: "10px",
                      marginBottom: "20px",
                      boxShadow: "0 2px 6px rgba(0, 0, 0, 0.1)",
                      color: "#222", // 👈 mørk tekst
                    }}
                  >
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

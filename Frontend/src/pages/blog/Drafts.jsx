import { useEffect, useState } from "react";
import { NavLink } from "react-router";
import apiFacade from "../../util/apiFacade.js"
import {
  GlobalStyle,
  Container,
  Navbar,
  HomeButton,
  ProfileButton,
  NavLinks,
  Content,
  Footer,
  BlogCard,
  BlogContainer,
  SectionTitle,
  BlogSectionLeft,
} from "./styles/draftsPageStyles";

function Drafts() {
  const [drafts, setDrafts] = useState([]);
  const userId = apiFacade.getCurrentUser()?.id;
  
  const fetchDrafts = async () => {
    try {
      const response = await fetch(
        "http://localhost:7070/api/blogpost/draft/" + userId,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      if (!response.ok) {
        throw new Error("Failed to fetch blog posts");
      }
      const data = await response.json();
      setDrafts(data);
    } catch (error) {
      console.error("Error:", error);
    }
  };

  useEffect(() => {
    if (userId) {

      fetchDrafts();
    } 
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
            <a href="/">Profile</a>
          </ProfileButton>
        </Navbar>

        <Content>
          <BlogContainer>
            <BlogSectionLeft>
              <SectionTitle>Your unpublished drafts</SectionTitle>
              {drafts.length < 1 ? <p>No recent posts yet.</p> : null}
              <ul Style="list-style-type: none; padding: 0;">
                {drafts.map((draft) => (
                  <BlogCard key={draft.id} Style="border-left: 5px solid red;">
                    <h3>{draft.title}</h3>
                    <p>{draft.content}</p>
                    <small>Saved on{draft.createdAt}</small>
                  </BlogCard>
                ))}
              </ul>
            </BlogSectionLeft>
          </BlogContainer>
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

export default Drafts;
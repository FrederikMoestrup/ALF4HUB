import { useEffect, useState } from "react";
import { NavLink } from "react-router";
import apiFacade from "../../util/apiFacade"
import {
  GlobalStyle,
  Container,
  Content,
  BlogCard,
  BlogContainer,
  SectionTitle,
  BlogSectionLeft,
} from "./styles/draftsPageStyles";

function Drafts() {
  const [drafts, setDrafts] = useState([]);
  const [userId, setUserId] = useState();

  const fetchDrafts = async () => {
    try {
      const currentUserId = await apiFacade.getUserId();
      setUserId(currentUserId)

      const response = await fetch(
        "http://localhost:7070/api/blogpost/draft/" + currentUserId,
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
    fetchDrafts();
  }, [userId]);

  return (
    <>
      <GlobalStyle />
      <Container>
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
      </Container>
    </>
  );
}

export default Drafts;
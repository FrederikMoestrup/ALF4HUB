import styled, { createGlobalStyle } from "styled-components";
import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";

const GlobalStyle = createGlobalStyle`
  html, body, #root{
    margin: 0;
    padding: 0;
    height: 100%;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
  }
`;

const BlogCard = styled.div`
  background-color: #f8f8f8;
  border-radius: 8px;
  padding: 16px;
  margin-bottom: 16px;
  box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.1);

  h3 {
    margin: 0 0 8px;
  }

  p {
    margin: 0 0 8px;
  }

  small {
    color: #777;
  }
`;
const Container = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;

const Navbar = styled.nav`
  position: relative;
  background-color: #ebecf8;
  border-bottom: 1px solid #cccccc;
  padding: 1rem 2rem;
  display: flex;
  justify-content: center; /* Center NavLinks in the middle */
  align-items: center;
`;

const HomeButton = styled.div`
  position: absolute;
  left: 2rem;

  a {
    outline: none;
    color: black;
    font-size: 1rem;
    padding: 8px 10px;
    font-weight: bold;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;
    text-decoration: none;

    &:hover {
      background-color: white;
      color: #000;
    }
  }
`;

const ProfileButton = styled(HomeButton)`
  right: 2rem; /* Position on the opposite corner */
  left: unset; /* Make sure left is not applied */
`;

const NavLinks = styled.div`
  display: flex;
  gap: 2rem;

  a {
    text-decoration: none;
    outline: none;
    color: black;
    font-size: 1rem;
    padding: 8px 10px;
    font-weight: bold;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;

    &:hover {
      background-color: white;
      color: #000;
    }
  }
`;

const Content = styled.main`
  flex: 1;
  display: flex;
  padding: 2rem;
  gap: 2rem;
  overflow-y: auto;
  background-color: #ebecf8;
`;

const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding-top: 2.5rem;
  margin-bottom: 2rem;
`;

const BlogContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-start;
  align-items: flex-start;
  position: relative;
  width: 100%;
  margin-top: 2rem; /* Add space above the blog container */
  flex-wrap: wrap;
`;

const Section = styled.section`
  position: relative;
  background: #f9f9f9;
  padding: 3rem;
  border-radius: 12px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
`;

const SectionTitle = styled.h2`
  position: absolute;
  top: -2.5rem; /* Increase space between the title and the blog sections */
  left: 0; /* Align the title with the left border of the blog sections */
  margin: 0;
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  padding: 0 0.5rem;
`;

const BlogSectionLeft = styled(Section)`
  width: 100%; /* Ensure the section doesn't overflow */
`;

const BlogSectionRight = styled(Section)`
  width: 100%; /* Ensure the section doesn't overflow */
`;

const Button = styled.button`
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  background-color: transparent;
  border: 1px solid rgb(0, 42, 255);
  color: black;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s, box-shadow 0.3s;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;
  width: 200px;

  &:hover {
    background-color: rgb(0, 42, 255);
  }
`;

const Footer = styled.footer`
  background-color: #ebecf8;
  color: #333;
  padding: 1rem 2rem;
  flex-shrink: 0;
`;

function BlogPostFrontPage() {
  const [blogPosts, setBlogPosts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:7070/api/blogpost/preview")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch blog posts");
        }
        return res.json();
      })
      .then((data) => setBlogPosts(data))
      .catch((err) => console.error("Error:", err));
  }, []);

  const handleDelete = async (id) => {
    const confirmed = window.confirm("Are you sure you want to delete this blog post?");
    if (!confirmed) return;
  
    try {
      const res = await fetch(`http://localhost:7070/api/blogpost/${id}`, {
        method: "DELETE",
      });
      if (!res.ok) {
        throw new Error("Failed to delete blog post");
      }
  
      setBlogPosts((prev) => prev.filter((post) => post.id !== id));
    } catch (error) {
      console.error("Delete error:", error);
      alert("An error occurred while deleting the blog post.");
    }
  };
  

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
            <NavLink to="/">Blog</NavLink>
          </NavLinks>
          <ProfileButton>
            <a href="/">Profile</a>
          </ProfileButton>
        </Navbar>

        <Content>
          {/* Button container */}
          <ButtonContainer>
            <NavLink to="/createblogpost">
              <Button>Create blogpost</Button>
            </NavLink>{" "}
            <Button>Drafts</Button>
          </ButtonContainer>

          {/* Blog container */}
          <BlogContainer>
            <BlogSectionLeft>
              <SectionTitle>Your published blogposts</SectionTitle>
              <p>No recent posts yet.</p>
            </BlogSectionLeft>

            <BlogSectionRight>
              <SectionTitle>Latest from the Community</SectionTitle>

              {blogPosts.length === 0 ? (
                <p>No blogposts yet.</p>
              ) : (
                blogPosts.map((post) => (
                  <BlogCard key={post.id}>
                    <h3>{post.title}</h3>
                    <p>{post.content}</p>
                    <small>Posted on {post.createdAt}</small>
                    <Button onClick={() => handleDelete(post.id)}>Delete</Button>
                  </BlogCard>
                ))
              )}
            </BlogSectionRight>
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

export default BlogPostFrontPage;

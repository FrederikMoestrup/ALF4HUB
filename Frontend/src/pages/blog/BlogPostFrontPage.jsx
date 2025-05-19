import { useEffect, useState } from "react";
import { NavLink } from "react-router-dom";
import {
  GlobalStyle,
  BlogCard,
  Container,
  Navbar,
  HomeButton,
  ProfileButton,
  NavLinks,
  Content,
  ButtonContainer,
  BlogContainer,
  Section,
  SectionTitle,
  BlogSectionLeft,
  BlogSectionRight,
  Button,
  Footer,
} from "./styles/blogPostFrontPageStyles";

function BlogPostFrontPage() {
  const [blogPosts, setBlogPosts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:7071/api/blogpost/preview")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch blog posts");
        }
        return res.json();
      })
      .then((data) => setBlogPosts(data))
      .catch((err) => console.error("Error:", err));
  }, []);

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
          {/* Button container */}
          <ButtonContainer>
            <NavLink to="/createblogpost">
              <Button>Create blogpost</Button>
            </NavLink>
            <NavLink to="/drafts">
              <Button>Drafts</Button>
            </NavLink>
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
                    <NavLink
                      to={`/blog/${post.id}`}
                      style={{ textDecoration: "none", color: "inherit" }}
                    >
                      <h3>{post.title}</h3>
                      <p>
                        {post.content.length > 100
                          ? post.content.substring(0, 100) + "..."
                          : post.content}
                      </p>
                     <small>Posted on {post.createdAt}</small>
                    </NavLink>
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

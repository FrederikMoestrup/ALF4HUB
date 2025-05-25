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
  BlogCardLink,
} from "./styles/blogPostFrontPageStyles";

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
                  <BlogCardLink to={`/blog/${post.id}`} key={post.id}>
                    <BlogCard>
                      <h3>{post.title}</h3>
                      <p>{post.content}</p>
                      <small>Posted on {post.createdAt}</small>
                    </BlogCard>
                  </BlogCardLink>
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

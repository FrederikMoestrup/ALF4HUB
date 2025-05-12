import React, { useState, useEffect } from "react";
import {
  Container,
  ForumContent,
  ForumHeader,
  ForumTitle,
  ButtonContainer,
  Button,
  BlogCard,
  BlogTitle,
  BlogContent,
  BlogMeta,
  NoPostsMessage,
  LoadingSpinner,
} from "./styles/ForumPage";

function ForumPage() {
  const [blogPosts, setBlogPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch("http://localhost:7070/api/blogpost/preview")
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch blog posts");
        }
        return res.json();
      })
      .then((data) => {
        setBlogPosts(data);
        setLoading(false);
      })
      .catch((err) => {
        console.error("Error:", err);
        setError(err.message);
        setLoading(false);
      });
  }, []);

  return (
    <Container>
      <ForumContent>
        <ForumHeader>
          <ForumTitle>Forum</ForumTitle>
          <ButtonContainer>
            <Button>Opret opslag</Button>
            <Button>Se dine opslag</Button>
          </ButtonContainer>
        </ForumHeader>

        {loading && <LoadingSpinner>Loading posts...</LoadingSpinner>}

        {error && <NoPostsMessage>Error loading posts: {error}</NoPostsMessage>}

        {!loading && !error && blogPosts.length === 0 && (
          <NoPostsMessage>No blogposts yet.</NoPostsMessage>
        )}

        {!loading &&
          !error &&
          blogPosts.length > 0 &&
          blogPosts.map((post) => (
            <BlogCard key={post.id} onClick={() => handlePostClick(post.id)}>
              <BlogTitle>{post.title}</BlogTitle>
              <BlogContent>{post.content}</BlogContent>
              <BlogMeta>
                <span>Posted on {post.createdAt}</span>
                <span>{post.status}</span>
              </BlogMeta>
            </BlogCard>
          ))}
      </ForumContent>
    </Container>
  );
}
export default ForumPage;

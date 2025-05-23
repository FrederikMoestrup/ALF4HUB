import React, { useState, useEffect } from "react";
import { NavLink } from "react-router-dom";

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
  BlogCardLink,
} from "./styles/ForumPage";

function ForumPage() {
  const [blogPosts, setBlogPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetch("http://localhost:7070/api/blogpost/preview")
      .then(async (res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch blog posts");
        }

        const contentType = res.headers.get("content-type");

        if (contentType && contentType.includes("application/json")) {
          return res.json();
        } else {
          const text = await res.text();
          if (text === "No blog posts with content preview found") {
            return [];
          } else {
            throw new Error("Unexpected response format");
          }
        }
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
            <NavLink to="/blog/create">
              <Button>Opret opslag</Button>
            </NavLink>

            <Button>Se dine opslag</Button>
            <NavLink to="/blog/drafts">
              <Button>Se dine kladder</Button>
            </NavLink>
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
            <BlogCardLink to={`/blog/${post.id}`} key={post.id}>
              <BlogCard>
                <BlogTitle>{post.title}</BlogTitle>
                <BlogContent style={{ color: "white" }}>
                  {post.content}
                </BlogContent>
                <BlogMeta style={{ color: "green" }}>
                  <span>Posted on {post.createdAt}</span>
                  <span>{post.status}</span>
                </BlogMeta>
              </BlogCard>
            </BlogCardLink>
          ))}
      </ForumContent>
    </Container>
  );
}

export default ForumPage;

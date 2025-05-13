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
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalBody,
  ModalFooter,
  CloseButton,
} from "./styles/ForumPage";

function ForumPage() {
  const [blogPosts, setBlogPosts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [selectedPost, setSelectedPost] = useState(null);
  const [showModal, setShowModal] = useState(false);
  const [loadingFullPost, setLoadingFullPost] = useState(false);

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

  const handlePostClick = (postId) => {
    setLoadingFullPost(true);
    setShowModal(true);

    fetch(`http://localhost:7070/api/blogpost/${postId}`)
      .then((res) => {
        if (!res.ok) {
          throw new Error("Failed to fetch full blog post");
        }
        return res.json();
      })
      .then((data) => {
        setSelectedPost(data);
        setLoadingFullPost(false);
      })
      .catch((err) => {
        console.error("Error fetching full post:", err);
        setError("Failed to load the full post. Please try again.");
        setLoadingFullPost(false);
      });
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedPost(null);
  };

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
              <BlogContent style={{ color: "white" }}>
                {post.content}
              </BlogContent>
              <BlogMeta style={{ color: "green" }}>
                <span>Posted on {post.createdAt}</span>
                <span>{post.status}</span>
              </BlogMeta>
            </BlogCard>
          ))}
      </ForumContent>

      {showModal && (
        <ModalOverlay onClick={closeModal}>
          <ModalContent onClick={(e) => e.stopPropagation()}>
            <ModalHeader>
              {loadingFullPost ? "Loading..." : selectedPost?.title}
              <CloseButton onClick={closeModal}>×</CloseButton>
            </ModalHeader>
            <ModalBody>
              {loadingFullPost ? (
                <LoadingSpinner>Loading full post...</LoadingSpinner>
              ) : (
                <>
                  <div>{selectedPost?.content}</div>
                  <BlogMeta>
                    <span>Posted on {selectedPost?.createdAt}</span>
                    <span>Status: {selectedPost?.status}</span>
                  </BlogMeta>
                  {selectedPost?.comments &&
                    selectedPost.comments.length > 0 && (
                      <div className="comments-section">
                        <h3>Comments</h3>
                        {selectedPost.comments.map((comment, index) => (
                          <div key={index} className="comment">
                            <p>{comment.content}</p>
                            <small>
                              By {comment.author} on {comment.createdAt}
                            </small>
                          </div>
                        ))}
                      </div>
                    )}
                </>
              )}
            </ModalBody>
            <ModalFooter>
              <Button onClick={closeModal}>Close</Button>
            </ModalFooter>
          </ModalContent>
        </ModalOverlay>
      )}
    </Container>
  );
}

export default ForumPage;

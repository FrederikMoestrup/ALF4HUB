import React from "react";
import {
  Content,
  BlogSectionLeft,
  Button,
} from "./styles/blogPostFrontPageStyles.js";
import styled from "styled-components";

// Example blog post data
const blogPost = {
  title: "Nye Features - Læs mere",
  content: `"But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account of the system...`,
  author: "MasterMind039",
  createdAt: "31/03 2025",
};

const PostHeader = styled.div`
 display: flex;
  flex-direction: column;
  align-items: flex-start;
  gap: 0.3rem;
  margin-bottom: 1.5rem;
  font-size: 1rem;
  color: #555;

  span {
    font-weight: bold;
  }

  small {
    color: #777;
    font-weight: normal;
  }
`;

const PostTitle = styled.h2`
  text-align: center;
  margin-bottom: 1.5rem;
  font-size: 1.6rem;
  color: black;
`;

const PostContent = styled.p`
  font-size: 1rem;
  line-height: 1.6;
  color: #222;
  white-space: pre-line;
`;

const ButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  margin-top: 2rem;
`;

const ReadBlogPost = () => {
  return (
    <Content>
      <BlogSectionLeft>
        <PostHeader>
          <span>Dato: {blogPost.createdAt}</span>
          <span>Forfatter: {blogPost.author}</span>
        </PostHeader>

        <PostTitle>{blogPost.title}</PostTitle>

        <PostContent>{blogPost.content}</PostContent>

        <ButtonWrapper>
          <Button onClick={() => window.history.back()}>Luk</Button>  {/* The button navigates the user back to the previous page */}
        </ButtonWrapper>
      </BlogSectionLeft>
    </Content>
  );
};

export default ReadBlogPost;
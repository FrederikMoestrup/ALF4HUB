import styled from "styled-components";
import { Link } from "react-router-dom";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  background-color: var(--color-main);
  color: var(--color-text);
  width: 100%;
  min-height: 100vh;
`;

export const ForumContent = styled.div`
  padding: 2rem;
`;

export const ForumHeader = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
`;

export const BlogCardLink = styled(Link)`
  text-decoration: none;
  color: inherit;
  display: block;

  &:visited,
  &:hover,
  &:active {
    color: inherit;
    text-decoration: none;
  }
`;

export const ForumTitle = styled.h1`
  font-size: 3rem;
  margin: 0;
`;

export const ButtonContainer = styled.div`
  display: flex;
  gap: 1rem;
`;

export const Button = styled.button`
  background-color: var(--color-button-general);
  color: var(--color-text);
  border: none;
  border-radius: 4px;
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  cursor: pointer;
  transition: background-color 0.2s;

  &:hover {
    background-color: rgba(39, 59, 64, 0.8);
  }
`;

export const BlogCard = styled.div`
  background-color: var(--color-accent);
  border-radius: 8px;
  padding: 1.5rem;
  margin-bottom: 1rem;
  transition: background-color 0.2s;
  cursor: pointer;

  &:hover {
    background-color: rgba(23, 27, 38, 0.8);
  }
`;

export const BlogTitle = styled.h3`
  margin: 0 0 0.75rem 0;
  font-size: 1.25rem;
`;

export const BlogContent = styled.p`
  margin: 0 0 1rem 0;
`;

export const BlogMeta = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.875rem;
  color: var(--color-text);
`;

export const NoPostsMessage = styled.p`
  text-align: center;
  padding: 2rem;
  color: var(--color-text);
  font-style: italic;
`;

export const LoadingSpinner = styled.div`
  text-align: center;
  padding: 2rem;
  color: var(--color-text);
`;

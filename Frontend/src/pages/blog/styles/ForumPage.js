import styled from "styled-components";

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

export const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.7);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

export const ModalContent = styled.div`
  background-color: #1a1a1a;
  border-radius: 8px;
  width: 80%;
  max-width: 700px;
  max-height: 90vh;
  overflow-y: auto;
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
`;

export const ModalHeader = styled.div`
  padding: 16px 20px;
  border-bottom: 1px solid #333;
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 20px;
  font-weight: bold;
  color: white;
`;

export const ModalBody = styled.div`
  padding: 20px;
  color: white;
  line-height: 1.6;
  flex: 1;

  .comments-section {
    margin-top: 30px;
    border-top: 1px solid #333;
    padding-top: 15px;
  }

  .comment {
    background-color: #252525;
    padding: 12px;
    margin-bottom: 10px;
    border-radius: 6px;

    small {
      display: block;
      margin-top: 5px;
      color: #888;
    }
  }
`;

export const ModalFooter = styled.div`
  padding: 16px 20px;
  border-top: 1px solid #333;
  display: flex;
  justify-content: flex-end;
`;

export const CloseButton = styled.button`
  background: none;
  border: none;
  font-size: 24px;
  color: #888;
  cursor: pointer;

  &:hover {
    color: white;
  }
`;

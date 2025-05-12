import styled, { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle`
  html, body, #root{
    margin: 0;
    padding: 0;
    height: 100%;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
  }
`;

export const BlogCard = styled.div`
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

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;

export const Navbar = styled.nav`
  position: relative;
  background-color: #ebecf8;
  border-bottom: 1px solid #cccccc;
  padding: 1rem 2rem;
  display: flex;
  justify-content: center; /* Center NavLinks in the middle */
  align-items: center;
`;

export const HomeButton = styled.div`
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

export const ProfileButton = styled(HomeButton)`
  right: 2rem; /* Position on the opposite corner */
  left: unset; /* Make sure left is not applied */
`;

export const NavLinks = styled.div`
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

export const Content = styled.main`
  flex: 1;
  display: flex;
  padding: 2rem;
  gap: 2rem;
  overflow-y: auto;
  background-color: #ebecf8;
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 2rem;
  padding-top: 2.5rem;
  margin-bottom: 2rem;
`;

export const BlogContainer = styled.div`
  display: flex;
  gap: 2rem;
  justify-content: flex-start;
  align-items: flex-start;
  position: relative;
  width: 100%;
  margin-top: 2rem; /* Add space above the blog container */
  flex-wrap: wrap;
`;

export const Section = styled.section`
  position: relative;
  background: #f9f9f9;
  padding: 3rem;
  border-radius: 12px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
`;

export const SectionTitle = styled.h2`
  position: absolute;
  top: -2.5rem; /* Increase space between the title and the blog sections */
  left: 0; /* Align the title with the left border of the blog sections */
  margin: 0;
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  padding: 0 0.5rem;
`;

export const BlogSectionLeft = styled(Section)`
  width: 100%; /* Ensure the section doesn't overflow */
`;

export const BlogSectionRight = styled(Section)`
  width: 100%; /* Ensure the section doesn't overflow */
`;

export const Button = styled.button`
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

export const Footer = styled.footer`
  background-color: #ebecf8;
  color: #333;
  padding: 1rem 2rem;
  flex-shrink: 0;
`;

import styled, { createGlobalStyle } from "styled-components";

export const GlobalStyle = createGlobalStyle`
  html, body, #root {
    margin: 0;
    padding: 0;
    height: 100%;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
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
  justify-content: center;
  align-items: center;
`;

export const HomeButton = styled.div`
  position: absolute;
  left: 2rem;

  a {
    color: black;
    font-size: 1rem;
    font-weight: bold;
    text-decoration: none;
    padding: 8px 10px;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;

    &:hover {
      background-color: white;
      color: black;
    }
  }
`;

export const ProfileButton = styled(HomeButton)`
  right: 2rem;
  left: unset;
`;

export const NavLinks = styled.div`
  display: flex;
  gap: 2rem;

  a {
    text-decoration: none;
    color: black;
    font-weight: bold;
    font-size: 1rem;
    padding: 8px 10px;
    border-radius: 4px;
    transition: background-color 0.3s ease, color 0.3s ease;

    &:hover {
      background-color: white;
      color: black;
    }
  }
`;

export const Content = styled.main`
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 2rem;
  background-color: #ebecf8;
  overflow-y: auto;
`;

export const Section = styled.section`
  background-color: #f9f9f9;
  border-radius: 12px;
  padding: 2rem;
  margin-bottom: 2rem;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.05);
  position: relative;
`;

export const SectionTitle = styled.h2`
  position: absolute;
  top: -2.5rem;
  left: 0;
  margin: 0;
  font-size: 1.2rem;
  font-weight: bold;
  color: #333;
  padding: 0 0.5rem;
`;

export const TournamentCard = styled.div`
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

export const TournamentList = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
`;

export const ButtonContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1.5rem;
  margin-bottom: 2rem;
`;

export const Button = styled.button`
  padding: 0.75rem 1.5rem;
  font-size: 1rem;
  background-color: transparent;
  border: 1px solid rgb(0, 42, 255);
  color: black;
  border-radius: 8px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  font-weight: 600;
  cursor: pointer;
  transition: transform 0.3s, background-color 0.3s;

  &:hover {
    background-color: rgb(0, 42, 255);
    color: white;
  }
`;

export const Footer = styled.footer`
  background-color: #ebecf8;
  color: #333;
  padding: 1rem 2rem;
  flex-shrink: 0;
  text-align: center;
`;

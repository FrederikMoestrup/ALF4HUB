import styled, { createGlobalStyle } from "styled-components";

/* Should fill the entire screen, doesn't right now. Needs to be fixed */
export const GlobalStyle = createGlobalStyle`
  html, body, #root{
    margin: 0;
    padding: 0;
    height: 100%;
    box-sizing: border-box;
    font-family: 'Segoe UI', sans-serif;
    color: pink;
  }
`;

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  min-height: 100vh;
`;

/* Top navbar */
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

/* Main part of the page */
export const Content = styled.main`
  flex: 1;
  display: flex;
  justify-content: center; /* Center the form horizontally */
  align-items: flex-start;
  padding: 2rem;
  gap: 2rem;
  background-color: #ebecf8;
`;

/*export const BackButton = styled.button`
  position: absolute;
  top: 2rem;
  left: 2rem;
  background: none;
  border: none;
  font-size: 2rem;
  color: black;
  cursor: pointer;

  &:hover {
    opacity: 0.7;
  }
`;*/

// Styled components for the form
export const FormWrapper = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
`;

export const FormTitle = styled.h2`
  text-align: center;
  margin-bottom: 1rem;
  color: black;
  margin-top: -0.3rem;
`;

export const Form = styled.form`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
  background-color: #ebecf8;
  padding: 2rem;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  max-width: 800px;
  width: 100%;
`;

export const Label = styled.label`
  color: black;
  font-weight: bold;
  text-align: left;
  margin-bottom: -1rem;
`;

export const Input = styled.input`
  padding: 0.75rem;
  font-size: 1rem;
  border-radius: 4px;
  border: 1px solid rgb(0, 42, 255);
`;

export const Textarea = styled.textarea`
  padding: 0.75rem;
  font-size: 1rem;
  border-radius: 4px;
  border: 1px solid rgb(0, 42, 255);
  height: 200px;
`;

export const ButtonsContent = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 100px;
`;

export const Button = styled.button`
  width: 250px;
  padding: 0.75rem;
  font-size: 1rem;
  background-color: transparent;
  border: 1px solid rgb(0, 42, 255);
  color: black;
  border-radius: 4px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  transition: transform 0.3s, box-shadow 0.3s;
  border-radius: 8px;
  font-weight: 600;
  cursor: pointer;

  &:hover {
    background-color: rgb(0, 42, 255);
  }
`;

export const RequiredText = styled.p`
  font-size: 0.875rem;
  color: black;
  align-self: flex-start;
`;

export const Footer = styled.footer`
  background-color: #ebecf8;
  color: #333;
  padding: 1rem 2rem;
  flex-shrink: 0;
`;
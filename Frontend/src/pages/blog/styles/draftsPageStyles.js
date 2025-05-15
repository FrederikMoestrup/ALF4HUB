import styled, { createGlobalStyle } from "styled-components";


const palette = {
  background: "#0e0f13", 
  surface: "#14171f",     
  nav: "#0E0F12",         
  border: "#2b2d36",      
  text: "#ffffff",         
  secondaryText: "#9da3b0",
  accent: "#263639",       
  accentHover: "#2f4447",  
  danger: "#ff3b3b",       
};


export const GlobalStyle = createGlobalStyle`
  /* Gaming‑style font */
  @import url('https://fonts.googleapis.com/css2?family=Rajdhani:wght@400;600;700&display=swap');

  *, *::before, *::after {
    box-sizing: border-box;
  }

  html, body {
    margin: 0;
    padding: 0;
    font-family: 'Rajdhani', sans-serif;
    background: ${palette.background};
    color: ${palette.text};
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  a {
    text-decoration: none;
    color: inherit;
  }
`;


export const Container = styled.div`
  min-height: 100%;
  display: flex;
  flex-direction: column;
`;

export const Navbar = styled.nav`
  width: 100%;
  background: ${palette.nav};
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 2rem;
  position: sticky;
  top: 0;
  z-index: 20;
`;


const baseNavButton = styled.div`
  display: flex;
  align-items: center;
  gap: 0.35rem;
  font-size: 1.25rem;

  a {
    display: flex;
    align-items: center;
    color: ${palette.text};
    transition: opacity 0.2s ease;
  }

  a:hover {
    opacity: 0.75;
  }
`;

export const HomeButton = styled(baseNavButton)`
  svg, img {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

export const ProfileButton = styled(baseNavButton)`
  svg, img {
    width: 1.6rem;
    height: 1.6rem;
  }
`;

export const NavLinks = styled.div`
  display: flex;
  gap: 2rem;

  a {
    font-size: 1.35rem;
    letter-spacing: 0.04em;
    position: relative;
  }

  a::after {
    content: "";
    position: absolute;
    bottom: -6px;
    left: 0;
    width: 0%;
    height: 2px;
    background: ${palette.text};
    transition: width 0.25s ease;
  }

  a:hover::after,
  a.active::after {
    width: 100%;
  }
`;
export const Content = styled.main`
  width: 90%;
  max-width: auto;   
 padding: 0;        
  margin: 5.5rem auto 2rem; 
  flex: 1;
`;

export const BlogContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
`;

export const BlogSectionLeft = styled.section`
  flex: 1;
`;

export const SectionTitle = styled.h2`
  margin: 0 0 0.75rem 0;
  font-size: 2.25rem;
  font-weight: 600;
  letter-spacing: 0.03em;
`;

export const BlogCard = styled.li`
  list-style: none;
  background: ${palette.surface};
  border: 1px solid ${palette.border};
  border-left: 5px solid ${palette.danger}; 
  border-radius: 4px;
  padding: 1rem 1.25rem;
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;

  h3 {
    margin: 0;
    font-size: 1.5rem;
    font-weight: 600;
  }

  p {
    margin: 0;
    color: ${palette.secondaryText};
    line-height: 1.35;
  }

  small {
    color: ${palette.secondaryText};
    font-size: 0.85rem;
  }
  h3::after {
  content: " ✨";
}

`;

export const Footer = styled.footer`
  width: 100%;
  background: ${palette.nav};
  color: ${palette.secondaryText};
  border: 0;
  text-align: center;
  font-size: 0.9rem;
`;


export const AccentButton = styled.button`
  background: ${palette.accent};
  color: ${palette.text};
  border: none;
  border-radius: 6px;
  padding: 0.65rem 1.75rem;
  font-family: inherit;
  font-size: 1.25rem;
  cursor: pointer;
  transition: background 0.2s ease, transform 0.1s ease;

  &:hover {
    background: ${palette.accentHover};
  }

  &:active {
    transform: scale(0.97);
  }
`;

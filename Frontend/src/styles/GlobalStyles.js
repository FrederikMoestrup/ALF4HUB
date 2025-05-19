// src/GlobalStyles.js
import { createGlobalStyle } from 'styled-components';

const GlobalStyle = createGlobalStyle`
  :root {
    --color-button-general: rgba(39, 59, 64, 1);
    --color-popup: rgba(202, 233, 234, 1);
    --color-main: rgba(17, 19, 25, 1);
    --color-accent: rgba(23, 27, 38, 1);
    --color-text: rgba(87, 210, 255, 1);
    --color-warning: rgba(227, 57, 57, 1);
    --color-accept: rgba(65, 206, 91, 1);
    --color-light-accent: rgba(245, 245, 245, 1);
  }

  @import url('https://fonts.googleapis.com/css2?family=Kdam+Thmor+Pro&display=swap');
  @font-face {
    font-family: 'Xirod';
    src: url('/fonts/Xirod-Regular.ttf') format('truetype');
    font-weight: normal;
    font-style: normal;
  }

  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
  }

  body {
    background-color: var(--color-main);
    color: var(--color-text);
    font-family: 'Kdam Thmor Pro', sans-serif;
    line-height: 1.6;
  }

  h1, h2, h3 {
    font-family: 'Xirod', sans-serif;
    color: var(--color-text);
  }
`;

export default GlobalStyle;
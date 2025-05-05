import React from 'react';
import { Link } from 'react-router-dom';
import './acces.css';

const Login = () => {
  return (
    <div className="login-page">
      <h2>Place holder for logo (ALTF4HUB)</h2>
      <form className="login-form">
        <div className="input-group">
          <label htmlFor="username">Brugernavn</label>
          <input type="text" id="username" name="username" placeholder="Indtast dit brugernavn" required />
        </div>
        <div className="input-group">
          <label htmlFor="password">Adgangskode</label>
          <input type="password" id="password" name="password" placeholder="Indtast din adgangskode" required />
        </div>
        <button type="submit">Login</button>
      </form>
      <p>Har du ikke en konto? opret en <Link to="/register">Registrer her</Link></p>
    </div>
  );
};

export default Login;

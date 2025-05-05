import React from 'react';
import { Link } from 'react-router-dom';
import './acces.css';

const Register = () => {
  return (
    <div className="register-page">
      <h2>Place holder for logo (ALTF4HUB)</h2>
      <form className="register-form">
        <div className="input-group">
          <label htmlFor="username">Brugernavn</label>
          <input type="text" id="username" name="username" placeholder="Indtast dit brugernavn" required />
        </div>
        <div className="input-group">
          <label htmlFor="email">Email</label>
          <input type="email" id="email" name="email" placeholder="Indtast din email" required />
        </div>
        <div className="input-group">
          <label htmlFor="password">Adgangskode</label>
          <input type="password" id="password" name="password" placeholder="Indtast din adgangskode" required />
        </div>
        <button type="submit">Register</button>
      </form>
      <p>Har du allerede en konto? <Link to="/login">Login her</Link></p>
    </div>
  );
};

export default Register;

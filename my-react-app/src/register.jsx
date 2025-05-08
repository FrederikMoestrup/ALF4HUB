import React from 'react';
import { Link } from 'react-router-dom';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './acces.css';
import Logo from './assets/ALTF4HUB.png';

const Register = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch('http://localhost:7070/api/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password, email })
      });

      const data = await res.json();

      if (res.ok) {
        localStorage.setItem('token', data.token);
        navigate('/login'); 
      } else {
        setErrorMsg(data.msg || 'Registrering fejlede');
      }

    } catch (err) {
      console.error('Fejl ved registrering:', err);
      setErrorMsg('Noget gik galt. Prøv igen.');
    }
  };

  return (
    <div className="register-page">
     <img src={Logo} alt="Logo for the page" className="logo-img" />
     <h1>Registrer</h1>
      <form className="register-form" onSubmit={handleSubmit}>
        <div className="input-group">
          <label htmlFor="username">Brugernavn</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Adgangskode</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {errorMsg && <p style={{ color: 'red' }}>{errorMsg}</p>}
        <button type="submit">Register</button>
      </form>
      <p>Har du allerede en konto? <Link to="/login">Login her</Link></p>
    </div>
  );
};

export default Register;

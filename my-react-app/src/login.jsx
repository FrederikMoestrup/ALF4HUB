import React from 'react';
import { Link } from 'react-router-dom';
import './acces.css';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';


const Login = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [errorMsg, setErrorMsg] = useState('');
  const navigate = useNavigate();


  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const res = await fetch('http://localhost:7070/api/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password })
      });

      const data = await res.json();

      if (res.ok) {
        localStorage.setItem('token', data.token);
        navigate('/');
      } else {
        setErrorMsg(data.msg || 'Login fejlede');
      }

    } catch (err) {
      console.error('Fejl ved login:', err);
      setErrorMsg('Noget gik galt. Prøv igen.');
    }
  };

  return (
    <div className="login-page">
      <h2>Place holder for logo (ALTF4HUB)</h2>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="input-group">
          <label htmlFor="username">Brugernavn</label>
          <input
            type="text"
            id="username"
            name="username"
            placeholder="Indtast dit brugernavn"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Adgangskode</label>
          <input
            type="password"
            id="password"
            name="password"
            placeholder="Indtast din adgangskode"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        {errorMsg && <p style={{ color: 'red' }}>{errorMsg}</p>}
        <button type="submit">Login</button>
      </form>
      <p>Har du ikke en konto? opret en <Link to="/register">Registrer her</Link></p>
    </div>
  );
};

export default Login;

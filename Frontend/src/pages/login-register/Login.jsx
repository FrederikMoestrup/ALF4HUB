import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import "./acces.css";
import Logo from "../../assets/ALTF4HUB.png";
import apiFacade from "../../util/apiFacade";

const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMsg, setErrorMsg] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await apiFacade.login(username, password);
      navigate("/");
    } catch (err) {
      console.error("Fejl ved login:", err);
      setErrorMsg(err.message || "Noget gik galt. Prøv igen.");
    }
  };

  return (
    <div className="login-page">
      <img src={Logo} alt="Logo for the page" className="logo-img" />
      <h1>Log Ind</h1>
      <p>
        Har du ikke en konto? opret en <Link to="/register">Registrer her</Link>
      </p>
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
        {errorMsg && <p style={{ color: "red" }}>{errorMsg}</p>}
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default Login;

import { Link } from 'react-router-dom';
import { useState } from 'react';
import NotificationCenter from './NotificationCenter';

const Navbar = ({ isLoggedIn, userTeam }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(isLoggedIn || false);
  
  const handleLogout = () => {
    setIsAuthenticated(false);
    // Additional logout logic would go here
  };

  return (
    <nav className="navbar">
      <div className="nav-links">
        <Link to="/">Home</Link>
        <Link to="/teams">Teams</Link>
        <Link to="/tournaments">Tournaments</Link>
        <Link to="/test">Test Features</Link>
      </div>
      <div className="auth-links">
        {isAuthenticated ? (
          <>
            {userTeam && (
              <Link to={`/team/${userTeam.id}`} className="auth-button">
                My Team
              </Link>
            )}
            <NotificationCenter />
            <button className="auth-button" onClick={handleLogout}>
              Logout
            </button>
          </>
        ) : (
          <>
            <Link to="/login" className="auth-button">
              Login
            </Link>
            <Link to="/register" className="auth-button">
              Register
            </Link>
          </>
        )}
      </div>
    </nav>
  );
};

export default Navbar; 
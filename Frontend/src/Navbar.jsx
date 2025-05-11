import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome, faUser } from '@fortawesome/free-solid-svg-icons';

const Navbar = () => {
  return (
    <header className="navbar">

      
      <div className="nav-left">
        <Link to="/">
          <FontAwesomeIcon icon={faHome} />
        </Link>

      </div>
      <div className="nav-center">
        <a href="#">Turneringer</a>
        <a href="#">Hold</a>
        <a href="#">Forum</a>
      </div>
      <div className="nav-right">

        <Link to="/login">
          <FontAwesomeIcon icon={faUser} className="user-icon" />
        </Link>

      </div>
    </header>
  );
};

export default Navbar;

import React from 'react';
import './Navbar.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHome, faUser } from '@fortawesome/free-solid-svg-icons';

const Navbar = () => {
  return (
    <header className="navbar">
      <div className="nav-left">
        <FontAwesomeIcon icon={faHome} />
      </div>
      <div className="nav-center">
        <a href="#">Turneringer</a>
        <a href="#">Hold</a>
        <a href="#">Forum</a>
      </div>
      <div className="nav-right">
        <FontAwesomeIcon icon={faUser} />
      </div>
    </header>
  );
};

export default Navbar;

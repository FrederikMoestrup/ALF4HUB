import { Link } from 'react-router-dom';
import '../styles/Navbar.css';
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
        <Link to="/tournaments">Turneringer</Link>
        <Link to="/teams">Hold</Link>
        <Link to="#">Forum</Link>
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
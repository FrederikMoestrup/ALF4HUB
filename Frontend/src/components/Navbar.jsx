import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "../styles/Navbar.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faHome, faUser, faBell } from "@fortawesome/free-solid-svg-icons";
import apiFacade from "./notifications_apiFacade.js";
import styled from "styled-components";

const LogoutButton = styled.button`
  background: none;
  border: none;
  color: white;
  font-size: 1rem;
  cursor: pointer;
  transition: color 0.2s ease;

  &:hover {
    color: red;
  }
`;

const Navbar = ({ setNotifications }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(apiFacade.loggedIn());
  const [unreadCount, setUnreadCount] = useState(0); 
  const [hasNotifications, setHasNotifications] = useState(false); 
  const navigate = useNavigate();

  useEffect(() => {
    const updateLoginStatus = () => {
      const status = apiFacade.loggedIn();
      setIsLoggedIn(status);

      if (status) {
        apiFacade
          .getUnreadNotificationCount()
          .then((data) => {
            setUnreadCount(data.count);
          })
          .catch((err) =>
            console.error("Fejl ved hentning af ulæste notifikationer:", err)
          );

        apiFacade
          .getNotificationCountForUser()
          .then((data) => {
            setHasNotifications(data.count > 0);
          })
          .catch((err) =>
            console.error("Fejl ved hentning af alle notifikationer:", err)
          );
      } else {
        setUnreadCount(0);
        setHasNotifications(false);
      }
    };

    updateLoginStatus();
    window.addEventListener("loginChanged", updateLoginStatus);
    return () => {
      window.removeEventListener("loginChanged", updateLoginStatus);
    };
  }, []);

  const handleLogout = () => {
    apiFacade.logout();
    setNotifications([]);
    navigate("/");
  };

  return (
    <header className="navbar">
      <div className="nav-left">
        <Link to="/">
          <FontAwesomeIcon icon={faHome} />
        </Link>
      </div>

      <div className="nav-center">
        <Link to="#">Turneringer</Link>
        <Link to="#">Hold</Link>
        <Link to="#">Forum</Link>
      </div>

      <div className="nav-right">
        {isLoggedIn && (
          <>
            {hasNotifications && (
              <Link to="/notifications" className="notification-icon">
                <FontAwesomeIcon icon={faBell} />
                {unreadCount > 0 && (
                  <span className="notification-badge">{unreadCount}</span>
                )}
              </Link>
            )}
            <LogoutButton onClick={handleLogout}>Logout</LogoutButton>
          </>
        )}

        <Link to={isLoggedIn ? "/profile" : "/login"}>
          <FontAwesomeIcon icon={faUser} className="user-icon" />
        </Link>
      </div>
    </header>
  );
};

export default Navbar;

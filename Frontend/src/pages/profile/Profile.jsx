import React, { useState, useEffect } from "react";
import "./Profile.css";
import ChangeProfilePicPopup from "./ChangeProfilePicPopup";
import { useNavigate } from "react-router-dom";
import apiFacade from "../../util/apiFacade.js";

const Profile = () => {
  const navigate = useNavigate();
  const [currentUser, setCurrentUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({ password: "" });
  const [imagePreviewUrl, setImagePreviewUrl] = useState(null);
  const [showPicPopup, setShowPicPopup] = useState(false);
  const [showProfileInfo, setShowProfileInfo] = useState(true);
  const [debugInfo, setDebugInfo] = useState("");

  // Fetch user data on component mount
  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          navigate("/login");
          return;
        }

        // Use the getUserId function from apiFacade
        const userId = await apiFacade.getUserId();

        if (!userId) {
          setDebugInfo("No user ID found in token");
          throw new Error("No user ID found in token");
        }

        setDebugInfo(`Using user ID: ${userId}`);

        // Fetch user data
        const response = await fetch(
          `http://localhost:7070/api/users/${userId}`,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (!response.ok) {
          const errorText = await response.text();
          setDebugInfo(`API error: ${response.status} - ${errorText}`);
          throw new Error(`Failed to fetch user data: ${response.status}`);
        }

        const userData = await response.json();
        setCurrentUser(userData);
        setFormData({
          ...userData,
          password: "",
        });
      } catch (err) {
        setError(err.message || "Error fetching user data");
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  useEffect(() => {
    if (editing && formData.profileImage instanceof File) {
      const objectUrl = URL.createObjectURL(formData.profileImage);
      setImagePreviewUrl(objectUrl);
      return () => URL.revokeObjectURL(objectUrl);
    } else {
      setImagePreviewUrl(null);
    }
  }, [formData.profileImage, editing]);

  const handleChange = (e) => {
    const { name, value, files } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: files ? files[0] : value,
    }));
  };

  const handleSave = async () => {
    try {
      const token = localStorage.getItem("token");

      // Validate email format
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(formData.email)) {
        alert("Indtast venligst en gyldig email adresse");
        return;
      }

      // Update user information
      const response = await fetch(
        `http://localhost:7070/api/users/${currentUser.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${token}`,
          },
          body: JSON.stringify({
            username: formData.username,
            email: formData.email,
            password: formData.password || undefined,
          }),
        }
      );

      if (!response.ok) {
        throw new Error("Failed to update user information");
      }

      const updatedUser = await response.json();
      setCurrentUser(updatedUser);
      setEditing(false);
      setFormData((prev) => ({ ...prev, password: "" }));
    } catch (err) {
      alert(err.message || "Error updating profile");
      console.error(err);
    }
  };

  if (loading) return <div>Loading...</div>;
  if (error)
    return (
      <div>
        <div>Error: {error}</div>
        <div>Debug Info: {debugInfo}</div>
        <button onClick={handleLogout}>Return to Login</button>
      </div>
    );
  if (!currentUser) return <div>No user data available</div>;

  return (
    <div className="profile-card">
      <div className="profile-left">
        <div className="profile-picture">
          <img
            src={
              imagePreviewUrl ||
              currentUser.profilePicture ||
              "https://img.freepik.com/free-psd/contact-icon-illustration-isolated_23-2151903337.jpg"
            }
            alt="Profilbillede"
            onError={(e) => {
              e.target.onerror = null; // Prevent infinite loop
              e.target.src =
                "https://img.freepik.com/free-psd/contact-icon-illustration-isolated_23-2151903337.jpg";
            }}
          />
        </div>
        {editing && (
          <button
            type="button"
            className="change-pic-btn"
            onClick={() => setShowPicPopup(true)}
            style={{ margin: "0.5rem 0" }}
          >
            Skift profilbillede
          </button>
        )}

        <button onClick={() => setShowProfileInfo(!showProfileInfo)}>
          Brugeroplysninger
        </button>
        <button onClick={() => navigate("/team-dashboard")}>Hold</button>
        <button onClick={() => navigate("/tournaments")}>Turneringer</button>
        <button onClick={() => navigate("/blog/forum")}>Blogposts</button>
        <button className="logout-button" onClick={handleLogout}>
          Log af
        </button>
      </div>

      {showProfileInfo && (
        <div className="profile-right">
          <label>Brugernavn</label>
          {editing ? (
            <input
              name="username"
              value={formData.username}
              onChange={handleChange}
            />
          ) : (
            <div className="static-text">{currentUser.username}</div>
          )}

          <label>Email</label>
          {editing ? (
            <input
              name="email"
              value={formData.email}
              onChange={handleChange}
            />
          ) : (
            <div className="static-text">{currentUser.email}</div>
          )}

          <label>Rolle</label>
          <div className="static-text">
            {currentUser.roles ? currentUser.roles.join(", ") : "User"}
          </div>

          <label>Strikes</label>
          <div className="static-text">{currentUser.strikes || 0}</div>

          {editing && (
            <>
              <label>Adgangskode (lad være tom for at beholde nuværende)</label>
              <input
                name="password"
                type="password"
                value={formData.password}
                onChange={handleChange}
              />
            </>
          )}

          <div className="profile-actions">
            <button onClick={editing ? handleSave : () => setEditing(true)}>
              {editing ? "Gem" : "Redigér"}
            </button>
            {editing && (
              <button onClick={() => setEditing(false)}>Annuller</button>
            )}
          </div>
        </div>
      )}
      <ChangeProfilePicPopup
        isOpen={showPicPopup}
        onClose={() => setShowPicPopup(false)}
        onSuccess={(newUrl) => {
          setCurrentUser((prev) => ({
            ...prev,
            profilePicture: newUrl,
          }));
        }}
        userId={currentUser.id}
      />
    </div>
  );
};

export default Profile;

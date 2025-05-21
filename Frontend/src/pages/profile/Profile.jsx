import React, { useState, useEffect } from 'react';
import './Profile.css';

const Profile = ({ user, loggedInUser, onSave }) => {
  const dummyUser = {
    id: 0,
    username: "TestBruger",
    email: "test@example.com",
    strikes: 0,
    profileImageUrl: "../src/assets/alf4hubfav.png"
  };

  const [currentUser, setCurrentUser] = useState(user || dummyUser);
  const actualLoggedInUser = loggedInUser || dummyUser;
  const isOwner = actualLoggedInUser && currentUser.id === actualLoggedInUser.id;

  const [editing, setEditing] = useState(false);
  const [formData, setFormData] = useState({ ...currentUser, password: '' });
  const [imagePreviewUrl, setImagePreviewUrl] = useState(null);

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
      [name]: files ? files[0] : value
    }));
  };

  const handleSave = () => {
    const updatedProfileImageUrl =
      formData.profileImage instanceof File
        ? URL.createObjectURL(formData.profileImage)
        : currentUser.profileImageUrl;

    setCurrentUser({
      ...currentUser,
      username: formData.username,
      email: formData.email,
      profileImageUrl: updatedProfileImageUrl,
    });

    if (onSave) {
      onSave(formData);
    }

    setEditing(false);
    setFormData((prev) => ({ ...prev, password: '' }));
  };

  return (
    <div className="profile-card">
      {/* LEFT COLUMN */}
      <div className="profile-left">
        <div className="profile-picture">
          <img
            src={imagePreviewUrl || currentUser.profileImageUrl || "/default-avatar.png"}
            alt="Profilbillede"
            onError={(e) => (e.target.src = "/default-avatar.png")}
          />
        </div>

        {editing && (
          <input type="file" name="profileImage" accept="image/*" onChange={handleChange} />
        )}

        <button>Brugeroplysninger</button>
        <button>Hold</button>
        <button>Turneringer</button>
        <button>Blogposts</button>
        <button className="logout-button">Log af</button>
      </div>

      {/* RIGHT COLUMN */}
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

        <label>Adgangskode</label>
        {editing ? (
          <input
            name="password"
            type="password"
            value={formData.password}
            onChange={handleChange}
          />
        ) : (
          <div className="static-text">••••••••••••••</div>
        )}

        {isOwner && (
          <div className="profile-actions">
            <button onClick={editing ? handleSave : () => setEditing(true)}>
              {editing ? 'Gem' : 'Redigér'}
            </button>
          </div>
        )}
      </div>
    </div>
  );
};

export default Profile;

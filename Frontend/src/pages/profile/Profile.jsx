import React, { useState, useEffect } from "react";
import "./Profile.css";
import ChangeProfilePicPopup from "./ChangeProfilePicPopup";

const Profile = ({ user, loggedInUser, onSave }) => {
    const dummyUser = {
        id: 0,
        username: "TestBruger",
        email: "test@example.com",
        role: "User",
        strikes: 0,
        profileImageUrl: "https://i.imgur.com/xd4qwBX.png",
    };

    const [currentUser, setCurrentUser] = useState(user || dummyUser);
    const actualLoggedInUser = loggedInUser || dummyUser;
    const isOwner = actualLoggedInUser?.id === currentUser?.id;

    const [editing, setEditing] = useState(false);
    const [formData, setFormData] = useState({ ...currentUser, password: "" });
    const [imagePreviewUrl, setImagePreviewUrl] = useState(null);
    const [showPicPopup, setShowPicPopup] = useState(false);

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

        if (onSave) onSave(formData);

        setEditing(false);
        setFormData((prev) => ({ ...prev, password: "" }));
    };

    return (
        <div className="profile-card">
            <div className="profile-left">
                <div className="profile-picture">
                    <img
                        src={
                            imagePreviewUrl ||
                            currentUser.profileImageUrl ||
                            "https://i.imgur.com/xd4qwBX.png"
                        }
                        alt="Profilbillede"
                        onError={(e) => {
                            e.target.onerror = null; // Prevent infinite loop
                            e.target.src = "https://i.imgur.com/xd4qwBX.png";
                        }}
                    />
                </div>
                {isOwner && editing && (
                    <button
                        type="button"
                        className="change-pic-btn"
                        onClick={() => setShowPicPopup(true)}
                        style={{ margin: "0.5rem 0" }}
                    >
                        Skift profilbillede
                    </button>
                )}

                {/* {editing && (
                    <input
                        type="file"
                        name="profileImage"
                        accept="image/*"
                        onChange={handleChange}
                    />
                )} */}

                <button>Brugeroplysninger</button>
                <button>Hold</button>
                <button>Turneringer</button>
                <button>Blogposts</button>
                <button className="logout-button">Log af</button>
            </div>

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
                <div className="static-text">{currentUser.role}</div>

                <label>Strikes</label>
                <div className="static-text">{currentUser.strikes}</div>

                {editing && (
                    <>
                        <label>Adgangskode</label>
                        <input
                            name="password"
                            type="password"
                            value={formData.password}
                            onChange={handleChange}
                        />
                    </>
                )}

                {isOwner && (
                    <div className="profile-actions">
                        <button
                            onClick={
                                editing ? handleSave : () => setEditing(true)
                            }
                        >
                            {editing ? "Gem" : "Redigér"}
                        </button>
                    </div>
                )}
            </div>
            <ChangeProfilePicPopup
                isOpen={showPicPopup}
                onClose={() => setShowPicPopup(false)}
                onSuccess={(newUrl) => {
                    setCurrentUser((prev) => ({
                        ...prev,
                        profileImageUrl: newUrl,
                    }));
                }}
                userId={currentUser.id}
            />
        </div>
    );
};

export default Profile;

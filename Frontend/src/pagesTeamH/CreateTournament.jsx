import React from 'react';
import { useNavigate } from 'react-router-dom';  // Importer useNavigate til navigation
import '../index.css';  // Husk at importere CSS'en

function CreateTournament() {
    const navigate = useNavigate();  // Hent navigate funktionen fra useNavigate

    // Funktion til at navigere tilbage til Home-siden
    const goBack = () => {
        navigate('/Home');
    };

    return (
        <>
            <div className="container">
                <div className="header">
                    <button className="back-btn" onClick={goBack}>←</button>
                    <h1>Create your next tournament</h1>
                </div>

                <div className="form">
                    <input
                        type="text"
                        placeholder="Choose name for tournament"
                        className="input-field"
                    />
                    <div className="date-fields">
                        <input
                            type="date"
                            placeholder="Start date"
                            className="input-field"
                        />
                        <input
                            type="date"
                            placeholder="End date"
                            className="input-field"
                        />
                    </div>
                    <button className="confirm-btn">Confirm</button>
                </div>

                <aside className="sidebar">
                    <h3>My tournaments</h3>
                    <ul>
                        <li>Tournament 1</li>
                        <li>Tournament 2</li>
                        <li>Tournament 3</li>
                        <li>Tournament 4</li>
                    </ul>
                </aside>

            </div>

            <footer className="infoFooter">
                <p>Altf4hub</p>
                <p>Firskovvej 18</p>
                <p>2800 Lyngby</p>
                <p>CVR-nr. 10101010</p>
                <p>Tlf: 36 15 45 04</p>
                <p>Mail: turnering@altf4hub.dk</p>
            </footer>
        </>
    );
}

export default CreateTournament;

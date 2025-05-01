import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../index.css';

function CreateTournament() {
    const navigate = useNavigate();
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [error, setError] = useState('');

    const goBack = () => {
        navigate('/Home');
    };

    const handleConfirm = () => {
        const today = new Date();
        const start = new Date(startDate);
        const end = new Date(endDate);

        today.setHours(0, 0, 0, 0);

        if (start < today) {
            setError('Startdato kan ikke være før dags dato.');
        } else if (end < start) {
            setError('Slutdato kan ikke være før startdato.');
        } else {
            setError('');

            alert('Turnering oprettet!'); // skal fjernes og lav en rigtigt besked
        }
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
                            className="input-field"
                            value={startDate}
                            onChange={(e) => setStartDate(e.target.value)}
                        />
                        <input
                            type="date"
                            className="input-field"
                            value={endDate}
                            onChange={(e) => setEndDate(e.target.value)}
                        />
                    </div>

                    {error && <p style={{ color: 'red' }}>{error}</p>}

                    <button className="confirm-btn" onClick={handleConfirm}>Confirm</button>
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

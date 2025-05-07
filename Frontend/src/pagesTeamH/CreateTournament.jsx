import React from 'react';
import { useNavigate } from 'react-router-dom';  // Importer useNavigate til navigation
import {useState} from 'react'; // Importer useState til at håndtere state
import '../index.css';
import apiFacade from './apiFacade.js';

function CreateTournament() {
    const [tournamentName, setTournamentName] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);

    const navigate = useNavigate();

    // Funktion til at navigere tilbage til Home-siden
    const goBack = () => {
        navigate('/Home');
    };
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess(false);


        if (!tournamentName || !startDate || !endDate) {
            setError('Please fill in all fields.');
            setSuccess(false);
            return;
        }
        if (new Date(endDate) < new Date(startDate)) {
            setError('Start date must be before end date.');
            return;
        }

        try {
            await apiFacade.createTournament(tournamentName, startDate, endDate);
            setSuccess(true);
            setTournamentName('');
            setStartDate('');
            setEndDate('');
        } catch (err) {
            console.error(err);
            setError('Fejl: ' + err.message);
        }
    };


    return (
        <>
            <div className="container">
                <div className="header">
                    <button className="back-btn" onClick={goBack}>←</button>
                    <h1>Create your next tournament</h1>
                </div>

                <form className="form" onSubmit={handleSubmit}>
                    <input
                        type="text"
                        placeholder="Choose name for tournament"
                        className="input-field"
                        value={tournamentName}
                        onChange={(e) => {
                            setTournamentName(e.target.value);
                            setError('');
                        }}
                    />
                    <div className="date-fields">
                        <input
                            type="date"
                            className="input-field"
                            value={startDate}
                            onChange={(e) => {
                                setStartDate(e.target.value);
                                setError('');
                            }}
                        />
                        <input
                            type="date"
                            className="input-field"
                            value={endDate}
                            onChange={(e) => {
                                setEndDate(e.target.value);
                                setError('');
                            }}
                        />
                    </div>
                    <button type="submit" className="create-btn">Confirm</button>

                    {error && <p className="error-message">{error}</p>}
                    {success && <p className="success-message">Tournament is created!</p>}
                </form>

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

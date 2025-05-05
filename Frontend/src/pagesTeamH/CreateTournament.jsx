import React from 'react';
import { useNavigate } from 'react-router-dom';  // Importer useNavigate til navigation
import {useState} from 'react'; // Importer useState til at håndtere state
import '../index.css';  // Husk at importere CSS'en

function CreateTournament() {
    const navigate = useNavigate();  // Hent navigate funktionen fra useNavigate
    const [showMessage, setShowMessage] = useState(false);  // State til at håndtere visning af succes besked
    const [showError, setShowError] = useState(false);  // State til at håndtere visning af fejlbesked
    const [name, setName] = useState('');  // State til at håndtere turneringsnavn
    const [startDate, setStartDate] = useState('');  // State til at håndtere startdato
    const [endDate, setEndDate] = useState('');  // State til at håndtere slutdato

    // Funktion til at navigere tilbage til Home-siden
    const goBack = () => {
        navigate('/Home');
    };

    const handleCreate = () => {
        if (!name || !startDate || !endDate) {
            setShowError(true);
            setTimeout(() => setShowError(false), 3000);
            return;
        }

        setShowMessage(true);
        setTimeout(() => {
            setShowMessage(false);
            setName('');
            setStartDate('');
            setEndDate('');
            navigate('/tournament');
        }, 3000);
    };


    return (
        <>
            <div className="container">
                <div className="header">
                    <button className="back-btn" onClick={goBack}>←</button>
                    <h1>Create your next tournament</h1>
                </div>

                <div className="headline">
                    <h2>Create your tournament</h2>
                </div>

                <div className="form">
                    <input
                        type="text"
                        placeholder="Choose name for tournament"
                        className="input-field"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
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
                    <button className="create-btn" onClick={handleCreate}>Create</button>
                    {showMessage && <p className="success-message">Tournament created successfully!</p>}
                    {showError && <p className="error-message">Please fill in all fields.</p>}
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

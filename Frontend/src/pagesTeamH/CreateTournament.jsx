import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../index.css';

function CreateTournament() {
    const [name, setName] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false); 

    const navigate = useNavigate();

    const bannedWords = [
        'fuck', 'shit', 'bitch', 'idiot', 'nazi', 'porno', 'porn', 'racist',
        'asshole', 'fucker', 'bastard', 'slut', 'whore', 'retard', 'cunt', 'douche',
        'crap', 'piss', 'faggot', 'cock', 'dick', 'pussy', 'bollocks', 'twat',
        'motherfucker', 'satan', 'hell', 'skank', 'rape', 'rapist', 'kinky', 'nsfw',
        'dræb', 'dræber', 'hader', 'kælling', 'spasser', 'klam', 'racisme', 'neger',
        'jødehader', 'muslimhader', 'bøsse', 'lesbisk', 'luder'        
      ];
      

    const goBack = () => {
        navigate('/Home');
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setSuccess(false); 

        if (!name || !startDate || !endDate) {
            setError('Alle felter skal udfyldes');
            return;
        }

        // Her tjekker vi for stødende ord
        const containsBadWord = bannedWords.some(word =>
            name.toLowerCase().includes(word)
        );

        if (containsBadWord) {
            setError('Turneringsnavnet indeholder stødende ord');
            return;
        }

        if (new Date(endDate) < new Date(startDate)) {
            setError('Slutdato må ikke være før startdato');
            return;
        }

        setError('');

        // Her skal vi gemme turneringen i databasen
        console.log('Turnering gemt:', { name, startDate, endDate });

        setSuccess(true);
        setTimeout(() => setSuccess(false), 3000); // skjul bekræftelse efter 3 sekunder

        // jeg nulstiller formen ved succesfuld oprettelse
        setName('');
        setStartDate('');
        setEndDate('');
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
                        value={name}
                        onChange={(e) => {
                            setName(e.target.value);
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
                    <button type="submit" className="confirm-btn">Confirm</button>

                    {error && <p className="error-message">{error}</p>}
                    {success && <p className="success-message">Turneringen er oprettet!</p>}
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

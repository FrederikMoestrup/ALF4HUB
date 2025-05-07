import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import '../index.css';
import apiFacade from '../utilTeamH/apiFacade';
import styled from 'styled-components';

const StyledError = styled.p`
  background-color: #ffe0e0;
  color: #a00;
  padding: 10px;
  border: 1px solid #a00;
  border-radius: 6px;
  text-align: center;
  max-width: 400px;
  margin: 1rem auto;
`;

function CreateTournament() {
    const [name, setName] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState(false);

    const navigate = useNavigate();

    const goBack = () => {
        navigate('/Home');
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSuccess(false);
        setError('');
    
        if (!name || !startDate || !endDate) {
            setError('Alle felter skal udfyldes');
            return;
        }
    
        if (new Date(endDate) < new Date(startDate)) {
            setError('Slutdato må ikke være før startdato');
            return;
        }
    
        try {
            const nameExists = await apiFacade.checkTournamentNameExists(name);
    
            if (nameExists) {
                setError('En turnering med dette navn findes allerede');
            } else {
                setSuccess(true);
            }
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

                    {error && <StyledError>{error}</StyledError>}
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

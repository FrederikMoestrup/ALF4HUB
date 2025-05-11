import { useState } from 'react';
import apiFacade from './util/apiFacade';
import './App.css';

function RemovePlayer({ playerAccount, teamId }) {
    const [removed, setRemoved] = useState(false);
    const [errorMessage, setErrorMessage] = useState('');

    const handleRemove = async () => {
        try {
            await apiFacade.removePlayerFromTeam(teamId, playerAccount.id);
            setRemoved(true);
            setErrorMessage('');
        } catch (error) {
            setErrorMessage(error.message || 'Failed to remove player from team');
        }
    };

    if (removed) {
        return (
            <div style={{ color: 'green', marginTop: '10px' }}>
                Player removed from team.
            </div>
        );
    }

    return (
        <div>
            <button
                onClick={handleRemove}
                style={{
                    padding: '10px 20px',
                    backgroundColor: 'crimson',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer',
                }}
            >
                Remove from Team
            </button>

            {errorMessage && (
                <div style={{ color: 'red', marginTop: '10px' }}>
                    {errorMessage}
                </div>
            )}
        </div>
    );
}

export default RemovePlayer;

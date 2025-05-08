import { useState } from 'react';
import apiFacade from '../util/apiFacade';
import '../App.css';

function InactiveButton({ playerAccount }) {
    const [player, setPlayer] = useState(playerAccount);
    const [errorMessage, setErrorMessage] = useState('');

    const toggleActive = () => {
        apiFacade.togglePlayerStatus(player).then((response) => {
            console.log("Updated player from backend:", player); // <-- Add this
            console.log("Updated response from backend:", response); // <-- Add this
            setPlayer(response);
        }).catch((error) => {
            console.error("Error updating player status:", error);
            setErrorMessage('Failed to update status. Please try again.');
        });
    }

    return (
        <div>
            <button
                onClick={toggleActive}
                style={{
                    padding: '10px 20px',
                    backgroundColor: player.isActive ? 'green' : 'red',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer',
                }}
            >
                {player.isActive ? 'Active' : 'Inactive'}
            </button>

            {errorMessage && (
                <div style={{ color: 'red', marginTop: '10px' }}>
                    {errorMessage}
                </div>
            )}
        </div>
    );
}

export default InactiveButton;
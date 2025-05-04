import { useState, useEffect } from 'react';
import apiFacade from '../util/apiFacade';
import '../App.css';

function InactiveButton({ playerAccount }) {
    const [isActive, setIsActive] = useState(playerAccount.isActive);
    const [errorMessage, setErrorMessage] = useState('');

    useEffect(() => {
        setIsActive(playerAccount.isActive);
    }, [playerAccount]);

    const toggleActive = async () => {
        try {
            const updatedPlayer = await apiFacade.togglePlayerStatus(playerAccount);
            setIsActive(updatedPlayer.isActive); // Update the state with the new status
            setErrorMessage(''); // Clear any previous error
            setIsActive(!isActive); // Toggle the active state
        } catch (error) {
            setErrorMessage(error.message || 'Failed to update status. Please try again.');
        }
    };

    return (
        <div>
            <button
                onClick={toggleActive}
                style={{
                    padding: '10px 20px',
                    backgroundColor: isActive ? 'green' : 'red',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer',
                }}
            >
                {isActive ? 'Active' : 'Inactive'}
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
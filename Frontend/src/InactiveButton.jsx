import { useState, useEffect } from 'react'
import './App.css'

function InactiveButton({ playerAccount }) {
    // useState to manage the active status of the user, so it can be toggled
    const [isActive, setIsActive] = useState(playerAccount.isActive)
    const [errorMessage, setErrorMessage] = useState('');

    // useEffect to change the status if prop changes, for example if somehow someone else changes the status externally
    useEffect(() => {
        setIsActive(playerAccount.isActive);
    }, [playerAccount]);

    const toggleActive = async () => {
        // Toggle the active status. If it's true, set it to false and vice versa
        setIsActive(!isActive);

        //API call to update the active status
        try {
            // Change the URL to the correct endpoint for the API
            const response = await fetch(`http://localhost:8080/api/player-accounts/${playerAccount.id}/status`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    ...playerAccount,
                    isActive: !isActive // Send the new status
                }),
            });

            if (response.ok) {
                setErrorMessage(''); // Clear any previous error
            }

            // If the response fails, set the error message and revert the status
            else {
                setIsActive(isActive); // Revert to the previous state
                setErrorMessage('Failed to update status. Please try again.');
            }
        }

        // Catch any other errors, such as network issues
        catch {
            setIsActive(isActive); // Revert to the previous state
            setErrorMessage('Network error. Please check your connection.');
        }
    }

    return (
        <div>
            {/* Button */}
            <button
                onClick={toggleActive}
                style={{
                    padding: '10px 20px',
                    backgroundColor: isActive ? 'green' : 'red',
                    color: 'white',
                    border: 'none',
                    borderRadius: '8px',
                    cursor: 'pointer'
                }}
            >
                {isActive ? 'Active' : 'Inactive'}
            </button>

            {/* Error Message */}
            {errorMessage && (
                <div style={{ color: 'red', marginTop: '10px' }}>
                    {errorMessage}
                </div>
            )}
        </div>
    );
}

export default InactiveButton;

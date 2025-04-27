import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import Navbar from '../components/Navbar';

const HomePage = () => {
  const [, setTournaments] = useState([]);
  
  // Simulating fetching data - in a real app, this would be an API call
  useEffect(() => {
    // Placeholder data
    const mockTournaments = [
      { id: 1, name: 'Summer Tournament 2025', date: '2025-07-15' },
      { id: 2, name: 'Winter Championship', date: '2025-12-10' },
    ];
    
    setTournaments(mockTournaments);
  }, []);

  return (
    <div>
      <Navbar />
      
      <div className="hero-section">
        <h1>Tournament Site Name</h1>
        <div className="background-image">
          {/* Background image would be set via CSS */}
        </div>
      </div>

      <div className="tournament-card">
        <div className="tournament-promo">
          <div className="tournament-screenshot">
            <h3>Screenshot from older tournament</h3>
            {/* Image would be here */}
          </div>
          <div className="tournament-join">
            <h3>Join a Tournament Today!</h3>
            <Link to="/tournaments" className="button-primary">
              Browse Tournaments
            </Link>
          </div>
        </div>
      </div>
    </div>
  );
};

export default HomePage; 
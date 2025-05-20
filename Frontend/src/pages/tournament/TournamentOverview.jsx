import { useEffect, useState } from "react";
import GlobalStyles from "Frontend/src/styles/GlobalStyles.js";

const TournamentOverview = () => {
    const [tournaments, setTournaments] = useState([]);

    useEffect(() => {
        fetch("http://localhost:7070/api/tournaments")
            .then((res) => {
                if (!res.ok) {
                    throw new Error("Failed to fetch tournaments");
                }
                return res.json();
            })
            .then((data) => setTournaments(data))
            .catch((err) => console.error("Error:", err));
    }, []);

    return (
        <>
            <GlobalStyles />
            <h1>Tournament Overview</h1>
            <div>
                {tournaments.length === 0 ? (
                    <p>No tournaments available.</p>
                ) : (
                    tournaments.map((tournament) => (
                        <div key={tournament.id} className="tournament-card">
                            <h2>{tournament.name}</h2>
                            <p>{tournament.description}</p>
                            <small>Start Date: {tournament.startDate}</small>
                        </div>
                    ))
                )}
            </div>
        </>
    );
};

export default TournamentOverview;
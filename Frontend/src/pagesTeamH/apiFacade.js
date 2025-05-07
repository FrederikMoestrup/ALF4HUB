const apiFacade = {
    createTournament: async (tournamentName, startDate, endDate) => {
        const url = "http://localhost:7070/api/tournaments/";

        const body = {
            tournamentName,
            startDate,
            endDate,
            game: "VALORANT",
            tournamentSize: 16,
            teamSize: 5,
            prizePool: 1000.0,
            rules: "Standard tournament rules",
            entryRequirements: "Minimum age 16",
            status: "UPCOMING",
            startTime: "12:00",
            endTime: "18:00",
            teams: [],
            host: null
        };


        console.log(" Sender POST til:", url, "med data:", body);

        const res = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(body)
        });

        if (!res.ok) {
            const errorText = await res.text();
            console.error(" Fejl ved oprettelse:", res.status, errorText);
            throw new Error(errorText);
        }

        const result = await res.json();
        console.log(" Turnering oprettet:", result);
        return result;
    }
};

export default apiFacade;
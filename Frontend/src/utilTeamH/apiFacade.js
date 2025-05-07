const apiFacade = {
    checkTournamentNameExists: async (name) => {
      const url = `http://localhost:7070/api/tournaments/checkname?name=${encodeURIComponent(name)}`;
      console.log("🔍 Sender forespørgsel til:", url);
  
      const res = await fetch(url, {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
        }
      });
  
      if (!res.ok) {
        console.error("❌ Fejl ved forespørgsel:", res.status, res.statusText);
        throw new Error('Kunne ikke tjekke turneringsnavn');
      }
  
      const result = await res.json();
      console.log("✅ Svar modtaget:", result);
  
      return result.exists;
    }
  };
  
  export default apiFacade;
  
  


const API_URL = 'http://localhost:7070/api'; 

const apiFacade = {
  searchPlayers: async (searchTerm = '', page = 1, size = 10) => {
    try {
      const queryParams = new URLSearchParams({
        search: searchTerm,
        page: page,
        size: size,
      });

      const response = await fetch(`${API_URL}/player-accounts?${queryParams.toString()}`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          
        },
      });

      if (!response.ok) {
        const errorResponse = await response.json();
        throw new Error(errorResponse.message || 'Failed to fetch player accounts');
      }

      const data = await response.json();
      console.log('Fetched player accounts:', data);

      
      return data.content || data;
    } catch (error) {
      console.error('Error fetching player accounts:', error);
      throw error;
    }
  },

  getAllTeams: async () => {
    try {
      const response = await fetch(`${API_URL}/teams`, {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        const errorResponse = await response.json();
        throw new Error(errorResponse.message || 'Failed to fetch teams');
      }

      const data = await response.json();
      console.log('Fetched all teams:', data);
      return data;
    } catch (error) {
      console.error('Error fetching all teams:', error);
      throw error;
    }
  }
  

};

export default apiFacade;

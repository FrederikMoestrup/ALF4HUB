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
  },

  togglePlayerStatus: async (player) => {
    try {
      const updatedStatus = !player.isActive;

      const response = await fetch(`${API_URL}/player-accounts/${player.id}/status?status=${updatedStatus}`,
          {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            ...player,
            isActive: updatedStatus,
          }),
        }
      )
        .then((response) => response.json())
        .then((response) => {
          return {
            ...response,
            isActive: response.active,
          };
        })
        .catch(() => {
          throw new Error("Failed to update player status");
        });

      return await response; // Return the updated player
    } catch (error) {
      console.error('Error updating player status:', error);
      throw error;
    }
  },

  removePlayerFromTeam: async (teamId, playerId) => {
    try {
      const response = await fetch(`${API_URL}/teams/${teamId}/remove-player`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: playerId }),
      });

      if (!response.ok) {
        const errorResponse = await response.json();
        throw new Error(errorResponse.message || 'Failed to remove player');
      }

      return await response.json();
    } catch (error) {
      console.error('Error removing player:', error);
      throw error;
    }
  },

  addPlayerToTeam: async (teamId, playerId) => {
    try {
      const response = await fetch(`${API_URL}/teams/${teamId}/invite-player`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ id: playerId }),
      });

      if (!response.ok) {
        const errorResponse = await response.json();
        throw new Error(errorResponse.message || 'Failed to invite player to the team');
      }

      const data = await response.json();
      console.log('Player successfully added to the team:', data);
      return data;
    } catch (error) {
      console.error('Error adding player to team:', error);
      throw error;
    }
  },



};

export default apiFacade;

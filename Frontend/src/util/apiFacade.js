const API_URL = 'http://localhost:7070/api';


const login = async (username, password) => {
  const res = await fetch("http://localhost:7070/api/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  const data = await res.json();

  if (!res.ok) {
    throw new Error(data.msg || "Login fejlede");
  }

  localStorage.setItem("token", data.token);
  window.dispatchEvent(new Event("loginChanged")); 
  return data;
};


const setToken = (token) => {
  if (token && token !== "undefined") {
    localStorage.setItem("token", token);
  } else {
    console.warn("Prøver at sætte ugyldig token:", token);
    localStorage.removeItem("token"); 
  }
};


const getToken = () => {
  return localStorage.getItem("token");
};


const getUser = () => {
  const token = getToken();
  if (token) {
    try {
      const payloadBase64 = token.split(".")[1];
      const decodedClaims = JSON.parse(window.atob(payloadBase64));
      return decodedClaims.username || decodedClaims.sub;
    } catch (error) {
      console.error("Error decoding JWT:", error);
      return null;
    }
  }
  return null;
};


const loggedIn = () => {
  const token = getToken();  
  return token != null;
};


const logout = () => {  
  localStorage.removeItem("token");
  window.dispatchEvent(new Event("loginChanged"));
};


const getUserRoles = () => {
  const token = getToken();
  if (token) {
    try {
      const payloadBase64 = token.split(".")[1];
      const decodedClaims = JSON.parse(window.atob(payloadBase64));
      return Array.isArray(decodedClaims.roles) ? decodedClaims.roles : [];
    } catch (error) {
      console.error("Error decoding JWT:", error);
      return [];
    }
  }
  return [];
};


const hasUserAccess = (neededRole) => {
  const roles = getUserRoles();
  return roles.includes(neededRole);
};


const makeOptions = (method, addToken, body) => {
  const opts = {
    method,
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
  };
  if (addToken && loggedIn()) {
    opts.headers["Authorization"] = `Bearer ${getToken()}`;
  }
  if (body) {
    opts.body = JSON.stringify(body);
  }
  return opts;
};


const fetchWithAuth = async (endpoint, method = "GET", body = null) => {
  const options = makeOptions(method, true, body);
  const response = await fetch(`${API_URL}${endpoint}`, options);

  if (!response.ok) {
    let error;
    try {
      error = await response.json();
    } catch {
      error = { message: "Ugyldigt JSON-svar fra server" };
    }
    throw new Error(error.message || "Noget gik galt");
  }
  return await response.json();
};


// --- Notifikationer ---


const getNotificationCountForUser = async () => {  
  try {
    const response = await fetchWithAuth(`/notifications/total-count`);    
    return response;
  } catch (error) {
    console.error("Fejl ved hentning af notifikationer:", error);
    throw error;
  }
};


const getUnreadNotificationCount = async () => {  
  try {
    const response = await fetchWithAuth('/notifications/unread-count');    
    return response;
  } catch (error) {
    console.error("Fejl ved hentning af ulæste notifikationer:", error);
    throw error;
  }
};


const getAllNotifications = async () => {  
  try {
    const response = await fetchWithAuth('/notifications');    
    return response;
  } catch (error) {
    console.error("Fejl ved hentning af alle notifikationer:", error);
    throw error;
  }
};


const markNotificationAsRead = async (id) => {  
  try {
    const response = await fetchWithAuth(`/notifications/${id}/read`, "PUT");    
    return response;
  } catch (error) {
    console.error("Fejl ved markering af notifikation som læst:", error);
    throw error;
  }
};


const markAllNotificationsAsRead = async () => { 
  try {
    const response = await fetchWithAuth('/notifications/markallasread', "PUT");   
    return response;
  } catch (error) {
    console.error("Fejl ved markering af alle notifikationer:", error);
    throw error;
  }
}; 


const apiFacade = {
  setToken,
  getToken,
  getUser,
  login,
  loggedIn,
  logout,
  getUserRoles,
  hasUserAccess,  
  makeOptions,
  fetchWithAuth,
 
  getNotificationCountForUser,
  getUnreadNotificationCount,
  getAllNotifications,
  markNotificationAsRead,
  markAllNotificationsAsRead,

  
  searchPlayers: async (searchTerm = '', page = 1, size = 10) => {
    const queryParams = new URLSearchParams({ search: searchTerm, page, size });
    const response = await fetch(`${API_URL}/player-accounts?${queryParams.toString()}`, makeOptions('GET', true));
    if (!response.ok) throw new Error('Failed to fetch player accounts');
    const data = await response.json();
    return data.content || data;
  },

  getAllTeams: async () => {
    const response = await fetch(`${API_URL}/teams`, makeOptions('GET', true));
    if (!response.ok) throw new Error('Failed to fetch teams');
    return await response.json();
  },

  removePlayerFromTeam: async (teamId, playerId) => {
    const response = await fetch(`${API_URL}/teams/${teamId}/remove-player/${playerId}`, makeOptions('DELETE', true));
    if (!response.ok) throw new Error('Failed to remove player');
    return await response.json();
  },

  addPlayerToTeam: async (teamId, playerId) => {
    const response = await fetch(`${API_URL}/teams/${teamId}/invite-player/${playerId}`, makeOptions('POST', true));
    if (!response.ok) throw new Error('Failed to invite player');
    return await response.json();
  }
};

export default apiFacade;

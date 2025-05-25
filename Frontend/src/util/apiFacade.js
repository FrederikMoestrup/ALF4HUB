const API_URL = "http://localhost:7070/api";

const login = async (username, password) => {
  const res = await fetch(`${API_URL}/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password }),
  });

  const data = await res.json();
  if (!res.ok) throw new Error(data.msg || "Login fejlede");

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

const getToken = () => localStorage.getItem("token");

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

const loggedIn = () => !!getToken();

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

const hasUserAccess = (neededRole) => getUserRoles().includes(neededRole);

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
  if (body) opts.body = JSON.stringify(body);
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

const getUserId = async () => {
  const token = await localStorage.getItem("token");
  if (!token) return null;

  const payloadBase64 = token.split(".")[1];
  const claims = JSON.parse(window.atob(payloadBase64));
  return claims ? claims.userid : "";
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
  getUserId,

  // Notifikationer
  getNotificationCountForUser: async () => {
    return await fetchWithAuth(`/notifications/total-count`);
  },
  getUnreadNotificationCount: async () => {
    return await fetchWithAuth("/notifications/unread-count");
  },
  getAllNotifications: async () => {
    return await fetchWithAuth("/notifications");
  },
  markNotificationAsRead: async (id) => {
    return await fetchWithAuth(`/notifications/${id}/read`, "PUT");
  },
  markAllNotificationsAsRead: async () => {
    return await fetchWithAuth("/notifications/markallasread", "PUT");
  },

  createNotification: async (notification) => {
    const response = await fetchWithAuth(
      "/notifications",
      "POST",
      notification
    );
    return response;
  },

  // Spillere og hold
  searchPlayers: async (searchTerm = "", page = 1, size = 10) => {
    const queryParams = new URLSearchParams({ search: searchTerm, page, size });
    const response = await fetch(
      `${API_URL}/player-accounts?${queryParams.toString()}`,
      makeOptions("GET", true)
    );
    if (!response.ok) throw new Error("Failed to fetch player accounts");
    const data = await response.json();
    return data.content || data;
  },

  getAllTeams: async () => {
    const response = await fetch(`${API_URL}/teams`, makeOptions("GET", true));
    if (!response.ok) throw new Error("Failed to fetch teams");
    return await response.json();
  },

  removePlayerFromTeam: async (teamId, playerId) => {
    const response = await fetch(
      `${API_URL}/teams/${teamId}/remove-player/${playerId}`,
      makeOptions("DELETE", true)
    );
    if (!response.ok) throw new Error("Failed to remove player");
    return await response.json();
  },

  addPlayerToTeam: async (teamId, playerId) => {
    const response = await fetch(
      `${API_URL}/teams/${teamId}/invite-player/${playerId}`,
      makeOptions("POST", true)
    );
    if (!response.ok) throw new Error("Failed to invite player");
    return await response.json();
  },

  acceptPlayerApplication: async (teamId, playerId) => {
    const response = await fetch(
      `${API_URL}/teams/${teamId}/applications/${playerId}/accept`,
      makeOptions("POST", true)
    );
    if (!response.ok) throw new Error("Kunne ikke acceptere ansøgningen");

    await apiFacade.createNotification({
      type: "APPLICATION_ACCEPTED",
      receiverId: playerId,
      message: "Din ansøgning til holdet er blevet accepteret!",
    });

    return await response.text();
  },

  rejectPlayerApplication: async (teamId, playerId) => {
    const response = await fetch(
      `${API_URL}/teams/${teamId}/applications/${playerId}/reject`,
      makeOptions("POST", true)
    );
    if (!response.ok) throw new Error("Kunne ikke afvise ansøgning");

    await apiFacade.createNotification({
      type: "APPLICATION_REJECTED",
      receiverId: playerId,
      message: "Din ansøgning til holdet blev afvist",
    });

    return await response.text();
  },
};

export default apiFacade;

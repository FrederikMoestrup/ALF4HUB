import { createContext, useState, useContext, useEffect } from "react";

const NotificationContext = createContext();

export const useNotifications = () => {
  return useContext(NotificationContext);
};

export const NotificationProvider = ({ children }) => {
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [isOpen, setIsOpen] = useState(false);

  // Initialize notifications when the provider loads
  useEffect(() => {
    fetchTeamJoinRequests();

    // Interval for notifcation for test.
    const interval = setInterval(() => {
      fetchTeamJoinRequests();
    }, 30000);

    return () => clearInterval(interval);
  }, []);

  useEffect(() => {
    // Count unread notifications
    const count = notifications.filter((notif) => !notif.isRead).length;
    setUnreadCount(count);
  }, [notifications]);

  // Function to add a new notification
  const addNotification = (notification) => {
    setNotifications((prev) => [
      ...prev,
      { ...notification, isRead: false, id: Date.now() },
    ]);
  };

  // Function to mark a notification as read
  const markAsRead = (notificationId) => {
    setNotifications((prev) =>
      prev.map((notif) =>
        notif.id === notificationId ? { ...notif, isRead: true } : notif
      )
    );
  };

  // Function to mark all notifications as read
  const markAllAsRead = () => {
    setNotifications((prev) =>
      prev.map((notif) => ({ ...notif, isRead: true }))
    );
  };

  // Function to remove a notification
  const removeNotification = (notificationId) => {
    setNotifications((prev) =>
      prev.filter((notif) => notif.id !== notificationId)
    );
  };

  // Function to toggle the notification panel
  const toggleNotifications = () => {
    setIsOpen((prev) => !prev);
    if (!isOpen) {
      markAllAsRead();
    }
  };

  // Mock function to fetch team join requests from the API
  const fetchTeamJoinRequests = () => {
    // This would be an API call in a real application
    const mockRequests = [
      {
        id: 1,
        type: "teamJoinRequest",
        teamId: "123",
        teamName: "Alpha Squad",
        requesterId: "456",
        requesterName: "JohnDoe",
        message: "I would like to join your team",
        createdAt: new Date().toISOString(),
        isRead: false,
      },
      {
        id: 2,
        type: "teamJoinRequest",
        teamId: "123",
        teamName: "Alpha Squad",
        requesterId: "789",
        requesterName: "JaneSmith",
        message: "Looking for a team to play with",
        createdAt: new Date(Date.now() - 86400000).toISOString(), // 1 day ago
        isRead: false,
      },
    ];

    // Merge with existing notifications
    setNotifications((prev) => {
      const existingIds = prev.map((n) => n.id);
      const newNotifications = mockRequests.filter(
        (n) => !existingIds.includes(n.id)
      );
      return [...prev, ...newNotifications];
    });
  };

  // Function to simulate sending a join request
  const sendJoinRequest = (teamData, requesterData, message) => {
    const newNotification = {
      id: Date.now(),
      type: "teamJoinRequest",
      teamId: teamData.id,
      teamName: teamData.name,
      requesterId:
        requesterData.id || "user-" + Math.floor(Math.random() * 1000),
      requesterName: requesterData.name || "Current User",
      message: message,
      createdAt: new Date().toISOString(),
      isRead: false,
    };

    // In a real app, this would send the request to the backend
    // For demo, just add it to our local notifications
    addNotification(newNotification);

    return Promise.resolve(newNotification);
  };

  const value = {
    notifications,
    unreadCount,
    isOpen,
    toggleNotifications,
    addNotification,
    markAsRead,
    markAllAsRead,
    removeNotification,
    fetchTeamJoinRequests,
    sendJoinRequest,
  };

  return (
    <NotificationContext.Provider value={value}>
      {children}
    </NotificationContext.Provider>
  );
};

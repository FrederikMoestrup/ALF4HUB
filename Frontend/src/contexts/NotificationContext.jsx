import { createContext, useState, useContext, useEffect } from 'react';

const NotificationContext = createContext();

export const useNotifications = () => {
  return useContext(NotificationContext);
};

export const NotificationProvider = ({ children }) => {
  const [notifications, setNotifications] = useState([]);
  const [unreadCount, setUnreadCount] = useState(0);
  const [isOpen, setIsOpen] = useState(false);

  useEffect(() => {
    // Count unread notifications
    const count = notifications.filter(notif => !notif.isRead).length;
    setUnreadCount(count);
  }, [notifications]);

  // Function to add a new notification
  const addNotification = (notification) => {
    setNotifications(prev => [...prev, { ...notification, isRead: false, id: Date.now() }]);
  };

  // Function to mark a notification as read
  const markAsRead = (notificationId) => {
    setNotifications(prev =>
      prev.map(notif =>
        notif.id === notificationId ? { ...notif, isRead: true } : notif
      )
    );
  };

  // Function to mark all notifications as read
  const markAllAsRead = () => {
    setNotifications(prev =>
      prev.map(notif => ({ ...notif, isRead: true }))
    );
  };

  // Function to remove a notification
  const removeNotification = (notificationId) => {
    setNotifications(prev => prev.filter(notif => notif.id !== notificationId));
  };

  // Function to toggle the notification panel
  const toggleNotifications = () => {
    setIsOpen(prev => !prev);
    if (!isOpen) {
      markAllAsRead();
    }
  };

  // Mock function to fetch team join requests from the API
  const fetchTeamJoinRequests = async (userId) => {
    // This would be an API call in a real application
    // For now, let's simulate some notifications
    setTimeout(() => {
      const mockRequests = [
        {
          id: 1,
          type: 'teamJoinRequest',
          teamId: '123',
          teamName: 'Alpha Squad',
          requesterId: '456',
          requesterName: 'JohnDoe',
          message: 'I would like to join your team',
          createdAt: new Date().toISOString(),
          isRead: false
        },
        {
          id: 2,
          type: 'teamJoinRequest',
          teamId: '123',
          teamName: 'Alpha Squad',
          requesterId: '789',
          requesterName: 'JaneSmith',
          message: 'Looking for a team to play with',
          createdAt: new Date(Date.now() - 86400000).toISOString(), // 1 day ago
          isRead: false
        }
      ];
      
      setNotifications(mockRequests);
    }, 1000);
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
    fetchTeamJoinRequests
  };

  return (
    <NotificationContext.Provider value={value}>
      {children}
    </NotificationContext.Provider>
  );
}; 
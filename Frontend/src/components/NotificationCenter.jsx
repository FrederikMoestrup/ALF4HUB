import { useNotifications } from '../contexts/NotificationContext';
import TeamJoinRequest from './TeamJoinRequest';
import '../styles/NotificationCenter.css';

const NotificationCenter = () => {
  const { 
    notifications, 
    unreadCount, 
    isOpen, 
    toggleNotifications
  } = useNotifications();

  return (
    <div className="notification-center">
      <button 
        className="notification-bell" 
        onClick={toggleNotifications}
        aria-label="Notifications"
      >
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
          <path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path>
          <path d="M13.73 21a2 2 0 0 1-3.46 0"></path>
        </svg>
        {unreadCount > 0 && (
          <span className="notification-badge">{unreadCount}</span>
        )}
      </button>
      
      {isOpen && (
        <div className="notification-panel">
          <div className="notification-header">
            <h3>Notifications</h3>
            {notifications.length > 0 ? (
              <button className="close-btn" onClick={toggleNotifications}>×</button>
            ) : null}
          </div>
          
          <div className="notification-list">
            {notifications.length > 0 ? (
              notifications.map(notification => {
                if (notification.type === 'teamJoinRequest') {
                  return (
                    <TeamJoinRequest 
                      key={notification.id}
                      notification={notification}
                    />
                  );
                }
                return null;
              })
            ) : (
              <div className="no-notifications">
                <p>No notifications</p>
              </div>
            )}
          </div>
        </div>
      )}
    </div>
  );
};

export default NotificationCenter; 
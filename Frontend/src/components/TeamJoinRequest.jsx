import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useNotifications } from '../contexts/NotificationContext';
import '../styles/TeamJoinRequest.css';

const TeamJoinRequest = ({ notification }) => {
  const [status, setStatus] = useState('pending'); // pending, accepted, rejected
  const { removeNotification } = useNotifications();
  const [isProcessing, setIsProcessing] = useState(false);

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
  };

  const handleAccept = async () => {
    setIsProcessing(true);
    try {
      // In a real app, this would call the API
      // await acceptTeamJoinRequest(notification.id);
      
      // For demo, simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      setStatus('accepted');
      // Optionally, remove the notification after a delay
      setTimeout(() => {
        removeNotification(notification.id);
      }, 3000);
    } catch (error) {
      console.error('Error accepting request:', error);
    } finally {
      setIsProcessing(false);
    }
  };

  const handleReject = async () => {
    setIsProcessing(true);
    try {
      // In a real app, this would call the API
      // await rejectTeamJoinRequest(notification.id);
      
      // For demo, simulate API call
      await new Promise(resolve => setTimeout(resolve, 1000));
      
      setStatus('rejected');
      // Optionally, remove the notification after a delay
      setTimeout(() => {
        removeNotification(notification.id);
      }, 3000);
    } catch (error) {
      console.error('Error rejecting request:', error);
    } finally {
      setIsProcessing(false);
    }
  };

  return (
    <div className={`team-join-request ${status !== 'pending' ? 'processed' : ''}`}>
      <div className="request-header">
        <h4>Team Join Request</h4>
        <span className="timestamp">{formatDate(notification.createdAt)}</span>
      </div>
      
      <div className="request-body">
        <p>
          <strong>{notification.requesterName}</strong> wants to join your team 
          <Link to={`/team/${notification.teamId}`} className="team-link">
            <strong> {notification.teamName}</strong>
          </Link>
        </p>
        
        {notification.message && (
          <p className="request-message">"{notification.message}"</p>
        )}
      </div>
      
      {status === 'pending' ? (
        <div className="request-actions">
          <button 
            className="accept-btn" 
            onClick={handleAccept}
            disabled={isProcessing}
          >
            {isProcessing ? 'Processing...' : 'Accept'}
          </button>
          <button 
            className="reject-btn" 
            onClick={handleReject}
            disabled={isProcessing}
          >
            {isProcessing ? 'Processing...' : 'Reject'}
          </button>
        </div>
      ) : (
        <div className={`request-status ${status}`}>
          {status === 'accepted' ? 'Request Accepted' : 'Request Rejected'}
        </div>
      )}
    </div>
  );
};

export default TeamJoinRequest; 
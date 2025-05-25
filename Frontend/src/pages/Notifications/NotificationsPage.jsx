import { useEffect, useState } from "react";
import apiFacade from "../../util/apiFacade";
import styled from "styled-components";

const Container = styled.div`
  padding: 2rem;
  max-width: 700px;
  margin: 0 auto;
  color: white;
`;

const Heading = styled.h2`
  margin-bottom: 1.5rem;
`;

const List = styled.ul`
  list-style: none;
  padding: 0;
`;

const MarkAllButton = styled.button`
  background-color: #007acc;
  color: white;
  border: none;
  padding: 0.5rem 1rem;
  margin-bottom: 1.5rem;
  border-radius: 4px;
  cursor: pointer;

  &:hover {
    background-color: #005fa3;
  }
`;

const NotificationItem = styled.li`
  border: 1px solid #444;
  padding: 1rem;
  margin-bottom: 1rem;
  border-left: 6px solid ${(props) => (props.read ? "#007acc" : "red")};
  background-color: ${(props) => (props.read ? "#3a3a3a" : "#2c2c2c")};
  border-radius: 6px;
  opacity: ${(props) => (props.read ? 0.8 : 1)};
  cursor: pointer;

  &:hover {
    background-color: #444;
  }
`;

const Title = styled.strong`
  display: block;
  margin-bottom: 0.5rem;
`;

const ExtraContent = styled.div`
  margin-top: 1rem;
  padding-top: 0.5rem;
  border-top: 1px dashed #666;
  color: #ccc;
`;

const NotificationsPage = ({ setNotifications, notifications }) => {
  const [loading, setLoading] = useState(true);
  const [selectedId, setSelectedId] = useState(null);

  useEffect(() => {
    if (apiFacade.loggedIn()) {
      apiFacade
        .getAllNotifications()
        .then((data) => {
          setNotifications(data);
          setLoading(false);
        })
        .catch((err) => {
          console.error("Fejl ved hentning af notifikationer:", err);
          setLoading(false);
        });
    }
  }, [setNotifications]);

  const handleMarkAllAsRead = async () => {
    try {
      await apiFacade.markAllNotificationsAsRead();
      setNotifications((prev) => prev.map((n) => ({ ...n, read: true })));
      window.dispatchEvent(new Event("loginChanged"));
    } catch (err) {
      console.error("Fejl ved markering af alle som læst:", err);
    }
  };

  const handleMarkAsRead = async (notification) => {
    if (!notification.isRead) {
      try {
        await apiFacade.markNotificationAsRead(notification.id);
        setNotifications((prev) =>
          prev.map((n) => (n.id === notification.id ? { ...n, read: true } : n))
        );
        window.dispatchEvent(new Event("loginChanged"));
      } catch (err) {
        console.error("Kunne ikke markere som læst:", err);
      }
    }

    setSelectedId((prev) =>
      prev === notification.id ? null : notification.id
    );
  };

  if (!apiFacade.loggedIn()) {
    return (
      <Container>Du skal være logget ind for at se notifikationer.</Container>
    );
  }

  if (loading) return <Container>Indlæser notifikationer...</Container>;

  if (notifications.length === 0)
    return <Container>Du har ingen notifikationer.</Container>;

  return (
    <Container>
      <Heading>Dine notifikationer</Heading>
      <List>
        {notifications.some((n) => !n.read) && (
          <MarkAllButton onClick={handleMarkAllAsRead}>
            Marker alle som læst
          </MarkAllButton>
        )}

        {notifications.map((n) => (
          <NotificationItem
            key={n.id}
            read={n.read}
            onClick={() => handleMarkAsRead(n)}
          >
            <Title>{n.notificationTitle}</Title>
            <div>Type: {n.notificationType}</div>

            {selectedId === n.id && (
              <ExtraContent>
                <p>
                  <strong>Sendt af:</strong> {n.senderId}
                </p>
                <p>
                  <strong>Team ID:</strong> {n.teamId}
                </p>
                <p>
                  <strong>Invitation ID:</strong> {n.invitationId}
                </p>

                <p>
                  <strong>Oprettet: </strong>
                  {Array.isArray(n.createdAt)
                    ? new Date(Date.UTC(...n.createdAt)).toLocaleString()
                    : new Date(n.createdAt).toLocaleString()}{" "}
                </p>
                {n.link && (
                  <p>
                    <strong>Link:</strong> <a href={n.link}>{n.link}</a>
                  </p>
                )}
              </ExtraContent>
            )}
          </NotificationItem>
        ))}
      </List>
    </Container>
  );
};

export default NotificationsPage;
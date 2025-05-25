import { useState } from "react";
import { Link } from "react-router-dom";

const TournamentsPage = () => {
  const [tournaments, setTournaments] = useState([
    {
      id: 1,
      name: "Summer Championship 2025",
      game: "Counter-Strike 2",
      startDate: "2025-07-15",
      endDate: "2025-07-18",
      status: "upcoming",
      maxTeams: 8,
      registeredTeams: 3,
      prize: "€1000",
      description:
        "The biggest summer tournament for CS2 players. Join now and compete for glory!",
    },
    {
      id: 2,
      name: "Winter League 2025",
      game: "League of Legends",
      startDate: "2025-12-03",
      endDate: "2025-12-20",
      status: "upcoming",
      maxTeams: 16,
      registeredTeams: 12,
      prize: "€2500",
      description:
        "Annual winter tournament featuring top LoL teams from across the region.",
    },
    {
      id: 3,
      name: "Rocket Showdown",
      game: "Rocket League",
      startDate: "2025-09-05",
      endDate: "2025-09-07",
      status: "upcoming",
      maxTeams: 12,
      registeredTeams: 6,
      prize: "€750",
      description:
        "Fast-paced rocket-powered action in this weekend tournament!",
    },
    {
      id: 4,
      name: "Spring Arena 2025",
      game: "Valorant",
      startDate: "2025-04-10",
      endDate: "2025-04-14",
      status: "completed",
      maxTeams: 8,
      registeredTeams: 8,
      prize: "€1500",
      description:
        "The spring showdown for tactical shooter enthusiasts. Concluded with Team Alpha as champions!",
    },
    {
      id: 5,
      name: "Battle Royale Masters",
      game: "Fortnite",
      startDate: "2025-11-20",
      endDate: "2025-11-22",
      status: "upcoming",
      maxTeams: 25,
      registeredTeams: 10,
      prize: "€2000",
      description:
        "Last team standing takes it all in this epic battle royale tournament.",
    },
  ]);

  const [activeTab, setActiveTab] = useState("upcoming");
  const [searchTerm, setSearchTerm] = useState("");

  const handleSearch = (e) => {
    setSearchTerm(e.target.value);
  };

  const handleTabChange = (tab) => {
    setActiveTab(tab);
  };

  // Filter tournaments based on search term and active tab
  const filteredTournaments = tournaments.filter((tournament) => {
    const matchesSearch =
      tournament.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      tournament.game.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesTab = activeTab === "all" || tournament.status === activeTab;
    return matchesSearch && matchesTab;
  });

  // Format date for display
  const formatDate = (dateString) => {
    const options = { year: "numeric", month: "short", day: "numeric" };
    return new Date(dateString).toLocaleDateString(undefined, options);
  };

  return (
    <div>
      <div className="tournaments-page-container">
        <div className="tournaments-header">
          <h1>Tournaments</h1>
          <div className="tournaments-actions">
            <Link to="/tournament/create" className="button-primary">
              Create Tournament
            </Link>
          </div>
        </div>

        <div className="tournaments-filters">
          <div className="search-bar">
            <input
              type="text"
              placeholder="Search tournaments..."
              value={searchTerm}
              onChange={handleSearch}
              className="search-input"
            />
          </div>
          <div className="tab-buttons">
            <button
              className={`tab-button ${activeTab === "all" ? "active" : ""}`}
              onClick={() => handleTabChange("all")}
            >
              All
            </button>
            <button
              className={`tab-button ${
                activeTab === "upcoming" ? "active" : ""
              }`}
              onClick={() => handleTabChange("upcoming")}
            >
              Upcoming
            </button>
            <button
              className={`tab-button ${
                activeTab === "completed" ? "active" : ""
              }`}
              onClick={() => handleTabChange("completed")}
            >
              Completed
            </button>
          </div>
        </div>

        <div className="tournaments-list">
          {filteredTournaments.length > 0 ? (
            filteredTournaments.map((tournament) => (
              <div
                key={tournament.id}
                className={`tournament-card ${tournament.status}`}
              >
                <div className="tournament-card-header">
                  <h3>{tournament.name}</h3>
                  <span className="tournament-game">{tournament.game}</span>
                </div>

                <div className="tournament-card-body">
                  <div className="tournament-details">
                    <div className="tournament-date">
                      <p>
                        <strong>Dates:</strong>{" "}
                        {formatDate(tournament.startDate)} -{" "}
                        {formatDate(tournament.endDate)}
                      </p>
                    </div>
                    <div className="tournament-teams">
                      <p>
                        <strong>Teams:</strong> {tournament.registeredTeams}/
                        {tournament.maxTeams}
                      </p>
                    </div>
                    <div className="tournament-prize">
                      <p>
                        <strong>Prize Pool:</strong> {tournament.prize}
                      </p>
                    </div>
                  </div>
                  <p className="tournament-description">
                    {tournament.description}
                  </p>
                </div>

                <div className="tournament-card-footer">
                  <span className={`status-badge ${tournament.status}`}>
                    {tournament.status === "upcoming"
                      ? "Upcoming"
                      : "Completed"}
                  </span>
                  <Link
                    to={`/tournament/${tournament.id}`}
                    className="button-secondary"
                  >
                    View Details
                  </Link>
                </div>
              </div>
            ))
          ) : (
            <div className="no-tournaments-message">
              <p>No tournaments match your search criteria.</p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};

export default TournamentsPage;

package dat.entities;

import dat.dtos.*;
import dat.enums.Game;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tournament")
public class Tournament {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "tournament_name", nullable = false)
    private String tournamentName;

    @Setter
    @Column(name = "game", nullable = false)
    @Enumerated(EnumType.STRING)
    private Game game;

    @Setter
    @Column(name = "tournament_size", nullable = false)
    private int tournamentSize;

    @Setter
    @Column(name = "team_size", nullable = false)
    private int teamSize;

    @Setter
    @Column(name = "price_pool", nullable = false)
    private double prizePool;

    //The rest of the attributes should be changed to a different data type. String is just a placeholder. Use Enum, LocalDate, etc.
    @Setter
    @Column(name = "rules", nullable = false)
    private String rules;

    @Setter
    @Column(name = "entry_requirements", nullable = false)
    private String entryRequirements;

    @Setter
    @Column(name = "status", nullable = false)
    private String status;

    @Setter
    @Column(name = "start_date", nullable = false)
    private String startDate;

    @Setter
    @Column(name = "start_time", nullable = false)
    private String startTime;

    @Setter
    @Column(name = "end_date", nullable = false)
    private String endDate;

    @Setter
    @Column(name = "end_time", nullable = false)
    private String endTime;

    @Setter
    @Column(name = "required_rank", nullable = false)
    private String requiredRank;


    //Relations
    @OneToMany(mappedBy = "tournament", cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    private List<Team> teams;

    @Setter
    @ManyToOne
    @JoinColumn(name = "host_id")
    private User host;

    public Tournament(String tournamentName, Game game, int tournamentSize, int teamSize, double prizePool,
                      String rules,String requiredRank, String entryRequirements, String status,
                      String startDate, String startTime, String endDate, String endTime, User host) {
        this.tournamentName = tournamentName;
        this.game = game;
        this.tournamentSize = tournamentSize;
        this.teamSize = teamSize;
        this.prizePool = prizePool;
        this.rules = rules;
        this.requiredRank = requiredRank;
        this.entryRequirements = entryRequirements;
        this.status = status;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.teams = new ArrayList<>();
        this.host = host;
    }

    public Tournament(TournamentDTO tournamentDTO) {
        if(tournamentDTO.getId()!= 0) {
            this.id = tournamentDTO.getId();
        }
        this.tournamentName = tournamentDTO.getTournamentName();
        this.game = tournamentDTO.getGame();
        this.tournamentSize = tournamentDTO.getTournamentSize();
        this.teamSize = tournamentDTO.getTeamSize();
        this.prizePool = tournamentDTO.getPrizePool();
        this.rules = tournamentDTO.getRules();
        this.requiredRank = tournamentDTO.getRequiredRank();
        this.entryRequirements = tournamentDTO.getEntryRequirements();
        this.status = tournamentDTO.getStatus();
        this.startDate = tournamentDTO.getStartDate();
        this.startTime = tournamentDTO.getStartTime();
        this.endDate = tournamentDTO.getEndDate();
        this.endTime = tournamentDTO.getEndTime();

        if (tournamentDTO.getHost() != null) {
            this.host = new User(tournamentDTO.getHost());
            this.host.addTournament(this);
        }

        this.teams = new ArrayList<>();
        if(tournamentDTO.getTeams() != null){
            setTeams(tournamentDTO.getTeams().stream()
                    .map(Team::new)
                    .collect(Collectors.toList()));
        }
    }

    public void setTeams(List<Team> teams) {
        if(teams != null) {
            this.teams = teams;
            for (Team team : teams) {
                team.setTournament(this);
            }
        }
    }

    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            this.teams.add(team);
            team.setTournament(this);
        }
    }

    public void validatePlayerForTournament(String rank, Team team) {
        if (rank == null || rank.isEmpty()) {
            throw new IllegalArgumentException("Rank is required");
        }

        this.requiredRank = rank;

        List<PlayerAccount> validPlayers = team.getTeamAccounts().stream()
                .filter(PlayerAccount::isActive)
                .filter(playerAccount -> playerAccount.getRank().equals(this.requiredRank))
                .filter(playerAccount -> playerAccount.getGame().equals(this.game))
                .collect(Collectors.toList());

        if (validPlayers.isEmpty()) {
             throw new IllegalArgumentException("No players meet the required rank and game for the tournament");
        }
    }

}

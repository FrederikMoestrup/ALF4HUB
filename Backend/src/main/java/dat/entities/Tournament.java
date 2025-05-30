package dat.entities;

import dat.dtos.*;
import dat.enums.Game;
import dat.enums.TournamentStatus;
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
    @Column(name = "tournament_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private TournamentStatus tournamentStatus;

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

    //Relations
    @OneToMany(mappedBy = "tournament", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<TournamentTeam> tournamentTeams = new ArrayList<>();

    @Setter
    @ManyToOne
    @JoinColumn(name = "host_id", nullable = false)
    private User host;

    public Tournament(String tournamentName, Game game, int tournamentSize, int teamSize, double prizePool,
                      String rules, String entryRequirements, TournamentStatus tournamentStatus,
                      String startDate, String startTime, String endDate, String endTime, User host) {
        this.tournamentName = tournamentName;
        this.game = game;
        this.tournamentSize = tournamentSize;
        this.teamSize = teamSize;
        this.prizePool = prizePool;
        this.rules = rules;
        this.entryRequirements = entryRequirements;
        this.tournamentStatus = tournamentStatus;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
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
        this.entryRequirements = tournamentDTO.getEntryRequirements();
        this.tournamentStatus = tournamentDTO.getTournamentStatus();
        this.startDate = tournamentDTO.getStartDate();
        this.startTime = tournamentDTO.getStartTime();
        this.endDate = tournamentDTO.getEndDate();
        this.endTime = tournamentDTO.getEndTime();

        if (tournamentDTO.getHost() != null) {
            this.host = new User(tournamentDTO.getHost());
            this.host.addTournament(this);
        }

        if (tournamentDTO.getTournamentTeams() != null) {
            setTournamentTeams(tournamentDTO.getTournamentTeams().stream()
                    .map(TournamentTeam::new)
                    .collect(Collectors.toList()));
        }

    }

    public void setTournamentTeams(List<TournamentTeam> tournamentTeams) {
        if (tournamentTeams != null) {
            this.tournamentTeams = tournamentTeams;
            for (TournamentTeam tournamentTeam : tournamentTeams) {
                tournamentTeam.setTournament(this);
            }
        }
    }

    public void addTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam != null && !tournamentTeams.contains(tournamentTeam)) {
            this.tournamentTeams.add(tournamentTeam);
            tournamentTeam.setTournament(this);
        }
    }

    public void removeTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam == null || !tournamentTeams.contains(tournamentTeam)) {
            return;
        }
        tournamentTeams.remove(tournamentTeam);
        tournamentTeam.setTournament(null);
    }

}
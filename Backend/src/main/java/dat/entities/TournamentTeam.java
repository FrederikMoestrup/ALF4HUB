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
@Table(name = "tournament_team")
public class TournamentTeam {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_team_id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "tournament_team_name", nullable = false)
    private String tournamentName;

    @Setter
    @Column(name = "game", nullable = false)
    @Enumerated(EnumType.STRING)
    private Game game;

    @Column(name = "tournament_status")
    @Enumerated(EnumType.STRING)
    private TournamentStatus tournamentStatus;

    //Relations
    @Setter
    @ManyToOne
    @JoinColumn(name = "tournament_team_captain_id")
    private User tournamentTeamCaptain;

    @Setter
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Setter
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "tournament_team_accounts",
            joinColumns = @JoinColumn(name = "tournament_team_id", referencedColumnName = "tournament_team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_account_id", referencedColumnName = "player_account_id")
    )
    private List<PlayerAccount> tournamentTeamAccounts = new ArrayList<>();


    public TournamentTeam(String tournamentName, Game game, User tournamentTeamCaptain) {
        this.tournamentName = tournamentName;
        this.game = game;
        this.tournamentTeamCaptain = tournamentTeamCaptain;
    }



}

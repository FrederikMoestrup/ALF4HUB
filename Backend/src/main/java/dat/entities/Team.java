package dat.entities;

import dat.dtos.*;
import dat.enums.Game;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {

    //Attributes
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "team_name", nullable = false)
    private String teamName;

    @Setter
    @Column(name = "game", nullable = false)
    @Enumerated(EnumType.STRING)
    private Game game;

    //Relations
    @Setter
    @ManyToOne
    @JoinColumn(name = "team_captain_id")
    private User teamCaptain;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "team_accounts",
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "player_account_id", referencedColumnName = "player_account_id")
    )
    private List<PlayerAccount> teamAccounts = new ArrayList<>();

    @OneToMany(mappedBy = "team", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<TournamentTeam> tournamentTeams = new ArrayList<>();


    public Team(String teamName, Game game, User teamCaptain) {
        this.teamName = teamName;
        this.game = game;
        this.teamCaptain = teamCaptain;
    }

    public Team(TeamDTO teamDTO) {
        if(teamDTO.getId()!= 0) {
            this.id = teamDTO.getId();
        }
        this.teamName = teamDTO.getTeamName();
        this.game = teamDTO.getGame();

        if (teamDTO.getTeamCaptain() != null) {
            this.teamCaptain = new User(teamDTO.getTeamCaptain());
            this.teamCaptain.addTeam(this);
        }

        if (teamDTO.getTeamAccounts() != null && !teamDTO.getTeamAccounts().isEmpty()) {
            setTeamAccounts(teamDTO.getTeamAccounts().stream()
                    .map(PlayerAccount::new)
                    .toList());
        }
    }

    public void setTeamAccounts(List<PlayerAccount> playerAccounts) {
        if(playerAccounts != null) {
            this.teamAccounts = playerAccounts;
            for (PlayerAccount playerAccount : playerAccounts) {
                playerAccount.addTeam(this);
            }
        }
    }

    public void addPlayerAccount(PlayerAccount playerAccount) {
        if (playerAccount != null && !teamAccounts.contains(playerAccount)) {
            teamAccounts.add(playerAccount);
            playerAccount.addTeam(this);
        }
    }

    public void removePlayerAccount(PlayerAccount playerAccount) {
        if (playerAccount == null || !teamAccounts.contains(playerAccount)) {
            return;
        }
        teamAccounts.remove(playerAccount);
        playerAccount.removeTeam(this);
    }

    public void setTournamentTeams(List<TournamentTeam> tournamentTeams) {
        if(tournamentTeams != null) {
            this.tournamentTeams = tournamentTeams;
            for (TournamentTeam tournamentTeam : tournamentTeams) {
                tournamentTeam.setTeam(this);
            }
        }
    }

    public void addTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam != null && !tournamentTeams.contains(tournamentTeam)) {
            this.tournamentTeams.add(tournamentTeam);
            tournamentTeam.setTeam(this);
        }
    }

    public void removeTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam == null || !tournamentTeams.contains(tournamentTeam)) {
            return;
        }
        tournamentTeams.remove(tournamentTeam);
        tournamentTeam.setTeam(null);
    }

}

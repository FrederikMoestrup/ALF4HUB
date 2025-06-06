package dat.entities;

import dat.dtos.*;
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


    public Team(String teamName, User teamCaptain) {
        this.teamName = teamName;
        this.teamCaptain = teamCaptain;
    }

    public Team(TeamDTO teamDTO) {
        if(teamDTO.getId()!= 0) {
            this.id = teamDTO.getId();
        }
        this.teamName = teamDTO.getTeamName();

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
    //Tilføjer en spiller til teamAccounts-listen
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
        if (tournamentTeams != null) {
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

    public void detachFromAllTournamentTeams() {
        if (tournamentTeams != null) {
            for (TournamentTeam tt : new ArrayList<>(tournamentTeams)) {
                tt.setTeam(null);
            }
            tournamentTeams.clear();
        }
    }

}

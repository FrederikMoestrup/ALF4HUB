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
@Table(name = "player_account")
public class PlayerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_account_id", nullable = false, unique = true)
    private int id;

    @Setter
    @Column(name = "player_account_name", nullable = false)
    private String playAccountName;

    @Setter
    @Column(name = "game", nullable = false)
    @Enumerated(EnumType.STRING)
    private Game game;

    //Needs to be changed later
    @Setter
    @Column(name = "rank", nullable = false)
    private String rank;

    //Relations
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "teamAccounts", fetch = FetchType.LAZY)
    private List<Team> teams;

    @ManyToMany(mappedBy = "tournamentTeamAccounts", fetch = FetchType.LAZY)
    private List<TournamentTeam> tournamentTeams;


    public PlayerAccount(String playAccountName, Game game, String rank, User user) {
        this.playAccountName = playAccountName;
        this.game = game;
        this.rank = rank;
        this.user = user;
        this.teams = new ArrayList<>();
    }

    public PlayerAccount(PlayerAccountDTO playerAccountDTO) {
        if (playerAccountDTO.getId() != 0) {
            this.id = playerAccountDTO.getId();
        }
        this.playAccountName = playerAccountDTO.getPlayAccountName();
        this.isActive = playerAccountDTO.isActive();
        this.game = playerAccountDTO.getGame();
        this.rank = playerAccountDTO.getRank();

        if (playerAccountDTO.getUser() != null) {
            this.user = new User(playerAccountDTO.getUser());
            this.user.addPlayerAccount(this);
        }

        if (playerAccountDTO.getTeams() != null) {
            setTeam(playerAccountDTO.getTeams().stream()
                    .map(Team::new)
                    .collect(Collectors.toList()));
        }
    }

    public void setTeam(List<Team> teams) {
        if(teams != null) {
            this.teams = teams;
            for (Team team : teams) {
                team.addPlayerAccount(this);
            }
        }
    }

    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            teams.add(team);
            team.addPlayerAccount(this);
        }
    }

    public void removeTeam(Team team){
        if (team == null || !teams.contains(team)) {
            return;
        }
        teams.remove(team);
    }

    public void setTournamentTeams(List<TournamentTeam> tournamentTeams) {
        if(tournamentTeams != null) {
            this.tournamentTeams = tournamentTeams;
            for (TournamentTeam tournamentTeam : tournamentTeams) {
                tournamentTeam.addPlayerAccount(this);
            }
        }
    }

    public void addTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam != null && !tournamentTeams.contains(tournamentTeam)) {
            tournamentTeams.add(tournamentTeam);
            tournamentTeam.addPlayerAccount(this);
        }
    }

    public void removeTournamentTeam(TournamentTeam tournamentTeam) {
        if (tournamentTeam == null || !tournamentTeams.contains(tournamentTeam)) {
            return;
        }
        tournamentTeams.remove(tournamentTeam);
    }


}
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
    @Column(name = "is_active", nullable = false)
    private boolean isActive;

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

    @ManyToMany(mappedBy = "team_accounts", fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();



    public PlayerAccount(String playAccountName, Game game) {
        this.playAccountName = playAccountName;
        this.game = game;
    }

    public void addTeam(Team team) {
        if (team != null && !teams.contains(team)) {
            teams.add(team);
        }
    }

    public void removeTeam(Team team) {teams.remove(team);
    }
}
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

    @Setter
    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "team_accounts", // Name of the join table
            joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "player_account_id", referencedColumnName = "id")
    )
    @OrderColumn(name = "list_order")
    private List<PlayerAccount> teamAccounts = new ArrayList<>();


    public Team(String teamName, Game game, User teamCaptain) {
        this.teamName = teamName;
        this.game = game;
        this.teamCaptain = teamCaptain;
        this.teamAccounts = new ArrayList<>();
    }

    public void addPlayerAccount(PlayerAccount playerAccount) {
        if (playerAccount != null && !teamAccounts.contains(playerAccount)) {
            teamAccounts.add(playerAccount);
            playerAccount.addTeam(this);
        }
    }

    public void removePlayerAccount(PlayerAccount playerAccount) {
        teamAccounts.remove(playerAccount);
        playerAccount.removeTeam(this);
    }

}

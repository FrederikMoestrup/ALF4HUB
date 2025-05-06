package dat.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "team_join_request")
public class TeamJoinRequest extends JoinRequest {

    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_account_id", nullable = false)
    private PlayerAccount playerAccount;


    public TeamJoinRequest(User requester, Team team, PlayerAccount playerAccount) {
        super(requester);
        this.team = team;
        this.playerAccount = playerAccount;
    }

    public User getReceiver() {
        return team.getTeamCaptain();
    }
}

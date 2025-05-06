package dat.entities;

import dat.dtos.TeamJoinRequestDTO;
import jakarta.persistence.*;
import lombok.*;

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

    public TeamJoinRequest(TeamJoinRequestDTO teamJoinRequestDTO) {
        super(new User(teamJoinRequestDTO.getRequester()));
        this.id = teamJoinRequestDTO.getId();
        this.status = teamJoinRequestDTO.getJoinRequestStatus();
        this.createdAt = teamJoinRequestDTO.getCreatedAt();
        this.team = new Team(teamJoinRequestDTO.getTeam());
        this.playerAccount = new PlayerAccount(teamJoinRequestDTO.getPlayerAccount());
    }

    public User getReceiver() {
        return team.getTeamCaptain();
    }
}

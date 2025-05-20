package dat.dtos;

import dat.entities.TeamJoinRequest;
import dat.enums.JoinRequestStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TeamJoinRequestDTO {

    private int id;
    private JoinRequestStatus joinRequestStatus;
    private LocalDateTime createdAt;
    private UserDTO requester;
    private TeamDTO team;
    private PlayerAccountDTO playerAccount;

    public TeamJoinRequestDTO(JoinRequestStatus joinRequestStatus, LocalDateTime createdAt, UserDTO requester, TeamDTO team, PlayerAccountDTO playerAccount) {
        this.joinRequestStatus = joinRequestStatus;
        this.createdAt = createdAt;
        this.requester = requester;
        this.team = team;
        this.playerAccount = playerAccount;
    }

    public TeamJoinRequestDTO(TeamJoinRequest teamJoinRequest){
        this.id = teamJoinRequest.getId();
        this.joinRequestStatus = teamJoinRequest.getStatus();
        this.createdAt = teamJoinRequest.getCreatedAt();
        this.requester = new UserDTO(teamJoinRequest.getRequester());
        this.team = new TeamDTO(teamJoinRequest.getTeam());
        this.playerAccount = new PlayerAccountDTO(teamJoinRequest.getPlayerAccount());
    }

}

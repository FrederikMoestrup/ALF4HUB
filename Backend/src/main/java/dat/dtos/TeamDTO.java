package dat.dtos;

import dat.entities.Team;
import dat.enums.Game;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {

    private int id;
    private String teamName;
    private Game game;
    private UserDTO teamCaptain;
    private TournamentDTO tournament;
    private List<PlayerAccountDTO> teamAccounts;

    public TeamDTO(String teamName, Game game, UserDTO teamCaptain,
                   TournamentDTO tournament, List<PlayerAccountDTO> teamAccounts) {
        this.teamName = teamName;
        this.game = game;
        this.teamCaptain = teamCaptain;
        this.tournament = tournament;
        this.teamAccounts = teamAccounts;
    }

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.game = team.getGame();

        if (team.getTeamCaptain() != null) {
            this.teamCaptain = new UserDTO(team.getTeamCaptain());
        }

        if (team.getTeamAccounts() != null && !team.getTeamAccounts().isEmpty()) {
            this.teamAccounts = team.getTeamAccounts().stream()
                    .map(PlayerAccountDTO::new)
                    .collect(Collectors.toList());
        }
    }
}

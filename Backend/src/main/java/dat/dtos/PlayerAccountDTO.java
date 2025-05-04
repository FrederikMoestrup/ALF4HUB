package dat.dtos;

import dat.entities.PlayerAccount;
import dat.enums.Game;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PlayerAccountDTO {

    private int id;
    private String playAccountName;
    private Game game;
    private String rank;
    private UserDTO user;
    private List<TeamDTO> teams = new ArrayList<>();
    private List<TournamentTeamDTO> tournamentTeams = new ArrayList<>();

    public PlayerAccountDTO(String playAccountName, Game game, String rank,
                            UserDTO user, List<TeamDTO> teams, List<TournamentTeamDTO> tournamentTeams) {
        this.playAccountName = playAccountName;
        this.game = game;
        this.rank = rank;
        this.user = user;
        this.teams = teams;
        this.tournamentTeams = tournamentTeams;
    }

    public PlayerAccountDTO(PlayerAccount playerAccount) {
        this.id = playerAccount.getId();
        this.playAccountName = playerAccount.getPlayAccountName();
        this.game = playerAccount.getGame();
        this.rank = playerAccount.getRank();

        if (playerAccount.getUser() != null) {
            this.user = new UserDTO(playerAccount.getUser());
        }

    }
}

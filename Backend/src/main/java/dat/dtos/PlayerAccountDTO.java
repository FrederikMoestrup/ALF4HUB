package dat.dtos;

import dat.entities.PlayerAccount;
import dat.enums.Game;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerAccountDTO {

    private int id;
    private String playerAccountName;
    private Game game;
    private String rank;
    private UserDTO user;
    private List<TeamDTO> teams = new ArrayList<>();
    private List<TournamentTeamDTO> tournamentTeams = new ArrayList<>();

    public PlayerAccountDTO(String playerAccountName, Game game, String rank,
                            UserDTO user, List<TeamDTO> teams, List<TournamentTeamDTO> tournamentTeams) {
        this.playerAccountName = playerAccountName;
        this.game = game;
        this.rank = rank;
        this.user = user;
        this.teams = teams;
        this.tournamentTeams = tournamentTeams;
    }

    public PlayerAccountDTO(PlayerAccount playerAccount) {
        this.id = playerAccount.getId();
        this.playerAccountName = playerAccount.getPlayerAccountName();
        this.game = playerAccount.getGame();
        this.rank = playerAccount.getRank();

        if (playerAccount.getUser() != null) {
            this.user = new UserDTO(playerAccount.getUser());
        }
    }
}

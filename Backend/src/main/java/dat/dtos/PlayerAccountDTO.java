package dat.dtos;

import dat.entities.PlayerAccount;
import dat.enums.Game;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlayerAccountDTO {

    private int id;
    private String playAccountName;
    private boolean isActive;
    private Game game;
    private String rank;
    private UserDTO user;
    private List<TeamDTO> teams;


    public PlayerAccountDTO() {
    }

    public PlayerAccountDTO(String playAccountName, boolean isActive, Game game, String rank,
                            UserDTO user, List<TeamDTO> teams) {
        this.playAccountName = playAccountName;
        this.isActive = isActive;
        this.game = game;
        this.rank = rank;
        this.user = user;
        this.teams = teams;
    }

    public PlayerAccountDTO(PlayerAccount playerAccount) {
        this.id = playerAccount.getId();
        this.playAccountName = playerAccount.getPlayAccountName();
        this.isActive = playerAccount.isActive();
        this.game = playerAccount.getGame();
        this.rank = playerAccount.getRank();

        if (playerAccount.getUser() != null) {
            this.user = new UserDTO(playerAccount.getUser());
        }

        this.teams = new ArrayList<>();
    }
}

package dat.dtos;

import dat.entities.User;
import lombok.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private String username;
    private Set<String> roles;
    private List<PlayerAccountDTO> playerAccounts;
    private List<TournamentDTO> tournaments;
    private List<TeamDTO> teams;

    public UserDTO(String username, Set<String> roles,
                   List<PlayerAccountDTO> playerAccounts,
                   List<TournamentDTO> tournaments,
                   List<TeamDTO> teams) {
        this.username = username;
        this.roles = roles;
        this.playerAccounts = playerAccounts;
        this.tournaments = tournaments;
        this.teams = teams;
    }

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.roles = user.getRolesAsStrings();

        if (user.getPlayerAccounts() != null) {
            this.playerAccounts = user.getPlayerAccounts().stream()
                    .map(PlayerAccountDTO::new)
                    .collect(Collectors.toList());
        }

        if (user.getTournaments() != null) {
            this.tournaments = user.getTournaments().stream()
                    .map(TournamentDTO::new)
                    .collect(Collectors.toList());
        }

        if (user.getTeams() != null) {
            this.teams = user.getTeams().stream()
                    .map(TeamDTO::new)
                    .collect(Collectors.toList());
        }
    }
}

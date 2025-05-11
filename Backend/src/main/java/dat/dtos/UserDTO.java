package dat.dtos;

import dat.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserDTO {

    private int id;
    private String username;
    private String password;
    private String email;
    private Set<String> roles = new HashSet<>();
    private List<PlayerAccountDTO> playerAccounts = new ArrayList<>();
    private List<TournamentDTO> tournaments = new ArrayList<>();
    private List<TeamDTO> teams = new ArrayList<>();
    private List<TournamentTeamDTO> tournamentTeams = new ArrayList<>();
    private int strikes;

    public UserDTO(String username, Set<String> roles,
                   List<PlayerAccountDTO> playerAccounts,
                   List<TournamentDTO> tournaments,
                   List<TeamDTO> teams, List<TournamentTeamDTO> tournamentTeams) {
        this.username = username;
        this.roles = roles;
        this.playerAccounts = playerAccounts;
        this.tournaments = tournaments;
        this.teams = teams;
        this.tournamentTeams = tournamentTeams;
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.roles = user.getRolesAsStrings();
        this.strikes = user.getStrikes();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            UserDTO dto = (UserDTO) o;
            return Objects.equals(this.username, dto.username) && Objects.equals(this.roles, dto.roles);
        } else {
            return false;
        }
    }

    public UserDTO(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public UserDTO(String username, Set<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.username, this.roles});
    }


    public String toString() {
        String var10000 = this.getUsername();
        return "UserDTO(id=" + id + ", username=" + var10000 + ", password=" + this.getPassword() + ", roles=" + this.getRoles() + ")";
    }

    public UserDTO(String username, String password, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
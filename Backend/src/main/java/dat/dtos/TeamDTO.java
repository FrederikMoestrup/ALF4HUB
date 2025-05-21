package dat.dtos;

import dat.entities.Team;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class TeamDTO {

    private int id;
    private String teamName;
    private UserDTO teamCaptain;
    private TournamentDTO tournament;
    private List<PlayerAccountDTO> teamAccounts = new ArrayList<>();
    private List<TournamentTeamDTO> tournamentTeams = new ArrayList<>();

    public TeamDTO() {
        // Empty constructor needed for Jackson deserialization
    }

    public TeamDTO(String teamName, UserDTO teamCaptain,
                   TournamentDTO tournament, List<PlayerAccountDTO> teamAccounts, List<TournamentTeamDTO> tournamentTeams) {
        this.teamName = teamName;
        this.teamCaptain = teamCaptain;
        this.tournament = tournament;
        this.teamAccounts = teamAccounts;
        this.tournamentTeams = tournamentTeams;
    }

    public TeamDTO(Team team) {
        this.id = team.getId();
        this.teamName = team.getTeamName();

        if (team.getTeamCaptain() != null) {
            this.teamCaptain = new UserDTO(team.getTeamCaptain());
        }

        if (team.getTeamAccounts() != null && !team.getTeamAccounts().isEmpty()) {
            this.teamAccounts = team.getTeamAccounts().stream()
                    .map(PlayerAccountDTO::new)
                    .collect(Collectors.toList());
        }

        if (team.getTournamentTeams() != null && !team.getTournamentTeams().isEmpty()) {
            this.tournamentTeams = team.getTournamentTeams().stream()
                    .map(TournamentTeamDTO::new)
                    .collect(Collectors.toList());
        }
    }
}

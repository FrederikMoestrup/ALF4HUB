package dat.dtos;

import dat.entities.TournamentTeam;
import dat.enums.Game;
import dat.enums.TournamentStatus;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TournamentTeamDTO {

    private int id;
    private String tournamentTeamName;
    private Game game;
    private TournamentStatus tournamentStatus;
    private UserDTO tournamentTeamCaptain;
    private TeamDTO team;
    private TournamentDTO tournament;
    private List<PlayerAccountDTO> tournamentTeamAccounts = new ArrayList<>();

    public TournamentTeamDTO(String tournamentTeamName, Game game, TournamentStatus tournamentStatus,
                              UserDTO tournamentTeamCaptain, TeamDTO team, TournamentDTO tournament,
                              List<PlayerAccountDTO> tournamentTeamAccounts) {
        this.tournamentTeamName = tournamentTeamName;
        this.game = game;
        this.tournamentStatus = tournamentStatus;
        this.tournamentTeamCaptain = tournamentTeamCaptain;
        this.team = team;
        this.tournament = tournament;
        this.tournamentTeamAccounts = tournamentTeamAccounts;
    }


    public TournamentTeamDTO(TournamentTeam tournamentTeam) {
        this.id = tournamentTeam.getId();
        this.tournamentTeamName = tournamentTeam.getTournamentTeamName();
        this.game = tournamentTeam.getGame();
        this.tournamentStatus = tournamentTeam.getTournamentStatus();

        if (tournamentTeam.getTournamentTeamCaptain() != null) {
            this.tournamentTeamCaptain = new UserDTO(tournamentTeam.getTournamentTeamCaptain());
        }

        if (tournamentTeam.getTournamentTeamAccounts() != null && !tournamentTeam.getTournamentTeamAccounts().isEmpty()) {
            this.tournamentTeamAccounts = tournamentTeam.getTournamentTeamAccounts().stream()
                    .map(PlayerAccountDTO::new)
                    .collect(Collectors.toList());
        }

    }
}

package dat.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dat.entities.Team;
import dat.entities.Tournament;
import dat.entities.TournamentTeam;
import dat.enums.Game;
import dat.enums.TournamentStatus;
import lombok.*;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TournamentDTO {

    private int id;
    private String tournamentName;
    private Game game;
    private int tournamentSize;
    private int teamSize;
    private double prizePool;
    private String rules;
    private String entryRequirements;
    private TournamentStatus tournamentStatus;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private List<TournamentTeamDTO> tournamentTeams = new ArrayList<>();
    private UserDTO host;

    public TournamentDTO(String tournamentName, Game game, int tournamentSize, int teamSize, double prizePool,
                         String rules, String entryRequirements, TournamentStatus tournamentStatus,
                         String startDate, String startTime, String endDate, String endTime, UserDTO host, List<TournamentTeamDTO> tournamentTeams) {
        this.tournamentName = tournamentName;
        this.game = game;
        this.tournamentSize = tournamentSize;
        this.teamSize = teamSize;
        this.prizePool = prizePool;
        this.rules = rules;
        this.entryRequirements = entryRequirements;
        this.tournamentStatus = tournamentStatus;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.host = host;
        this.tournamentTeams = tournamentTeams;
    }

    public TournamentDTO(Tournament tournament){
        this.id = tournament.getId();
        this.tournamentName = tournament.getTournamentName();
        this.game = tournament.getGame();
        this.tournamentSize = tournament.getTournamentSize();
        this.teamSize = tournament.getTeamSize();
        this.prizePool = tournament.getPrizePool();
        this.rules = tournament.getRules();
        this.entryRequirements = tournament.getEntryRequirements();
        this.tournamentStatus = tournament.getTournamentStatus();
        this.startDate = tournament.getStartDate();
        this.startTime = tournament.getStartTime();
        this.endDate = tournament.getEndDate();
        this.endTime = tournament.getEndTime();

        if (tournament.getTournamentTeams() != null && !tournament.getTournamentTeams().isEmpty()) {
            this.tournamentTeams = tournament.getTournamentTeams().stream()
                    .map(TournamentTeamDTO::new)
                    .collect(Collectors.toList());
        }

        if(tournament.getHost() != null) {
            this.host = new UserDTO(tournament.getHost());
        }
    }

}
package dat.dtos;

import dat.entities.Team;
import dat.entities.Tournament;
import dat.enums.Game;
import lombok.*;

import java.util.List;
import java.util.ArrayList;

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
    private String requiredRank;
    private String entryRequirements;
    private String status;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private List<TeamDTO> teams;
    private UserDTO host;

    public TournamentDTO(String tournamentName, Game game, int tournamentSize, int teamSize, double prizePool,
                         String rules,String requiredRank, String entryRequirements, String status,
                         String startDate, String startTime, String endDate, String endTime, UserDTO host) {
        this.tournamentName = tournamentName;
        this.game = game;
        this.tournamentSize = tournamentSize;
        this.teamSize = teamSize;
        this.prizePool = prizePool;
        this.rules = rules;
        this.requiredRank = requiredRank;
        this.entryRequirements = entryRequirements;
        this.status = status;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.teams = new ArrayList<>();
        this.host = host;
    }

    public TournamentDTO(Tournament tournament){
        this.id = tournament.getId();
        this.tournamentName = tournament.getTournamentName();
        this.game = tournament.getGame();
        this.tournamentSize = tournament.getTournamentSize();
        this.teamSize = tournament.getTeamSize();
        this.prizePool = tournament.getPrizePool();
        this.rules = tournament.getRules();
        this.requiredRank = tournament.getRequiredRank();
        this.entryRequirements = tournament.getEntryRequirements();
        this.status = tournament.getStatus();
        this.startDate = tournament.getStartDate();
        this.startTime = tournament.getStartTime();
        this.endDate = tournament.getEndDate();
        this.endTime = tournament.getEndTime();

        this.teams = new ArrayList<>();
        if(tournament.getTeams() != null && !tournament.getTeams().isEmpty()) {
            for (Team team : tournament.getTeams()) {
                TeamDTO teamDTO = new TeamDTO(team);
                this.teams.add(teamDTO);
            }
        }

        if(tournament.getHost() != null) {
            this.host = new UserDTO(tournament.getHost());
        }
    }

}

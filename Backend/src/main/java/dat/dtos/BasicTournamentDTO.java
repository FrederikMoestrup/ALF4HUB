package dat.dtos;

import dat.entities.Tournament;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicTournamentDTO {
    private String tournamentName;
    private String startDate;
    private String endDate;

    public BasicTournamentDTO(String tournamentName, String startDate, String endDate) {
        this.tournamentName = tournamentName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BasicTournamentDTO(Tournament tournament) {
        this.tournamentName = tournament.getTournamentName();
        this.startDate = tournament.getStartDate();
        this.endDate = tournament.getEndDate();
    }
}



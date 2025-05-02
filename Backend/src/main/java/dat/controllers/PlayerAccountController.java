package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.PlayerAccountDAO;
import dat.daos.TeamDAO;
import dat.daos.TournamentDAO;
import dat.dtos.PlayerAccountDTO;
import dat.dtos.TeamDTO;
import dat.dtos.TournamentDTO;
import dat.dtos.UserDTO;
import dat.exceptions.ApiException;
import dat.services.EmailService;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PlayerAccountController {

    private final PlayerAccountDAO playerAccountDAO;
    private final TeamDAO teamDAO;
    private final TournamentDAO tournamentDAO;

    public PlayerAccountController() {
        if (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.playerAccountDAO = PlayerAccountDAO.getInstance(emf);
            this.teamDAO = TeamDAO.getInstance(emf);
            this.tournamentDAO = TournamentDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.playerAccountDAO = PlayerAccountDAO.getInstance(emf);
            this.teamDAO = TeamDAO.getInstance(emf);
            this.tournamentDAO = TournamentDAO.getInstance(emf);
        }
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlayerAccountDTO playerAccountDTO = playerAccountDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(playerAccountDTO, PlayerAccountDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "PlayerAccount not found");
        }
    }

    public void getAll(Context ctx) {
        List<PlayerAccountDTO> playerAccountDTOs = playerAccountDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(playerAccountDTOs, PlayerAccountDTO.class);
    }

    public void create(Context ctx) {
        PlayerAccountDTO playerAccountDTO = ctx.bodyAsClass(PlayerAccountDTO.class);
        PlayerAccountDTO createdPlayerAccountDTO = playerAccountDAO.create(playerAccountDTO);
        ctx.res().setStatus(201);
        ctx.json(createdPlayerAccountDTO, PlayerAccountDTO.class);
    }

    public void update(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlayerAccountDTO playerAccountDTO = playerAccountDAO.update(id, validateEntity(ctx));
            ctx.res().setStatus(200);
            ctx.json(playerAccountDTO, PlayerAccountDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "PlayerAccount not found");
        }
    }

    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlayerAccountDTO deletedPlayerAccountDTO = playerAccountDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json(deletedPlayerAccountDTO, PlayerAccountDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "PlayerAccount not found");
        }
    }

    public PlayerAccountDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(PlayerAccountDTO.class)
                .check(p -> p.getPlayAccountName() != null && !p.getPlayAccountName().isEmpty(), "Play account name must be set")
                .check(p -> p.getGame() != null, "Game must be associated")
                .check(p -> p.getRank() != null && !p.getRank().isEmpty(), "Rank must be set")
                .get();
    }

    public void leaveTeam(Context ctx) throws ApiException {
        try {
            int playerAccountId = Integer.parseInt(ctx.pathParam("playerAccountId"));
            int teamId = Integer.parseInt(ctx.pathParam("teamId"));
            playerAccountDAO.leaveTeam(playerAccountId, teamId);
            ctx.res().setStatus(200);
            UserDTO user = playerAccountDAO.getById(playerAccountId).getUser();
            TeamDTO team = teamDAO.getById(teamId);
            EmailService emailService = new EmailService();
            emailService.sendEmail(user.getEmail(), "Team Leave Notification",
                    "You have successfully left team: " + team.getTeamName());

            TournamentDTO tournament = tournamentDAO.getById(team.getTournament().getId());
// TODO apply strike if leaving too close to tournament start.

//            if (tournament.getStartTime() < 0) {
//                emailService.sendEmail(user.getEmail(), "Tournament Notification",
//                        "You have left the team: " + team.getTeamName() + " in tournament: " + tournament.getTournamentName());
//            }

            ctx.result("Player has successfully left the team.");
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: playerAccountId or teamId");
        } catch (ApiException e) {
            throw new ApiException(e.getStatusCode(), e.getMessage());
        }
    }


}

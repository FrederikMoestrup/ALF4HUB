package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.TeamDAO;
import dat.dtos.PlayerAccountDTO;
import dat.daos.PlayerAccountDAO;
import dat.dtos.TeamDTO;
import dat.entities.PlayerAccount;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class TeamController {

    private final TeamDAO teamDAO;

    public TeamController() {
        if (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.teamDAO = TeamDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.teamDAO = TeamDAO.getInstance(emf);
        }
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TeamDTO teamDTO = teamDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(teamDTO, TeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Team not found");
        }
    }

    public void getAll(Context ctx) {
        List<TeamDTO> teamDTOs = teamDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(teamDTOs, TeamDTO.class);
    }

    public void create(Context ctx) throws ApiException {
        int id = Integer.parseInt(ctx.pathParam("id"));
        TeamDTO teamDTO = ctx.bodyAsClass(TeamDTO.class);

        // Check for existing team name (case-insensitive)
        if (teamDAO.teamNameAlreadyExist(teamDTO.getTeamName())) {
            throw new ApiException(409, "Holdnavnet er allerede i brug");
        }

        TeamDTO createdTeamDTO = teamDAO.create(teamDTO, id);
        ctx.res().setStatus(201);
        ctx.json(createdTeamDTO, TeamDTO.class);
    }

    public void update(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TeamDTO teamDTO = teamDAO.update(id, validateEntity(ctx));
            ctx.res().setStatus(200);
            ctx.json(teamDTO, TeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Team not found");
        }
    }

    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TeamDTO deletedTeamDTO = teamDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json(deletedTeamDTO, TeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Team not found");
        }
    }

    public void invitePlayer(Context ctx) throws ApiException {
        try {
            int teamId = Integer.parseInt(ctx.pathParam("id"));
            int playerAccountId = Integer.parseInt(ctx.pathParam("playerAccountId"));

            TeamDTO updatedTeam = teamDAO.invitePlayer(teamId, playerAccountId);

            ctx.res().setStatus(200);
            ctx.json(updatedTeam, TeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid team ID format");
        }
    }

    public void removePlayer(Context ctx) throws ApiException {
        try {
            int teamId = Integer.parseInt(ctx.pathParam("id"));
            int playerAccountId = Integer.parseInt(ctx.pathParam("playerAccountId"));

            TeamDTO updatedTeamDTO = teamDAO.removePlayer(teamId, playerAccountId);

            ctx.status(200).json(updatedTeamDTO);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Invalid team ID format.");
        } catch (Exception e) {
            throw new ApiException(500, "Unexpected error: " + e.getMessage());
        }
    }

    public TeamDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TeamDTO.class)
                .check(t -> t.getTeamName() != null && !t.getTeamName().isEmpty(), "Team name must be set")
                .get();
    }

    public void getPlayersByTeamId(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            PlayerAccountDAO playerAccountDAO = new PlayerAccountDAO();
            List<PlayerAccount> playerAccounts = playerAccountDAO.getPlayersByTeamId(id);

            List<PlayerAccountDTO> playerAccountDTOs = new ArrayList<>();
            for (PlayerAccount player : playerAccounts) {
                playerAccountDTOs.add(new PlayerAccountDTO(player));
            }

            ctx.res().setStatus(200);
            ctx.json(playerAccountDTOs);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }
    }


}

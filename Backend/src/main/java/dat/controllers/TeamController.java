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
        if  (HibernateConfig.getTest()) {
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

    public void getAll(Context ctx){
            List<TeamDTO> teamDTOs = teamDAO.getAll();
            ctx.res().setStatus(200);
            ctx.json(teamDTOs, TeamDTO.class);
    }

    public void create(Context ctx) {
        TeamDTO teamDTO = ctx.bodyAsClass(TeamDTO.class);
        TeamDTO createdTeamDTO = teamDAO.create(teamDTO);
        ctx.res().setStatus(201);
        ctx.json(createdTeamDTO, TeamDTO.class);
    }

    public void update(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TeamDTO teamDTO = teamDAO.update(id, validateEntity(ctx));
            ctx.res().setStatus(200);
            ctx.json(teamDTO, TeamDTO.class);
        }catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        }catch (ApiException e) {
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

    public TeamDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TeamDTO.class)
                .check(t -> t.getTeamName() != null && !t.getTeamName().isEmpty(), "Team name must be set")
                .check(t -> t.getGame() != null, "Game must be associated")
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

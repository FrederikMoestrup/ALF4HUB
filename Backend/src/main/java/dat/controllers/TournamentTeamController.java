package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.TournamentTeamDAO;
import dat.dtos.TournamentDTO;
import dat.dtos.TournamentTeamDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TournamentTeamController {

    private final TournamentTeamDAO tournamentTeamDAO;

    public TournamentTeamController() {
        if (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.tournamentTeamDAO = TournamentTeamDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.tournamentTeamDAO = TournamentTeamDAO.getInstance(emf);
        }
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TournamentTeamDTO tournamentTeamDTO = tournamentTeamDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(tournamentTeamDTO, TournamentTeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "TournamentTeam not found");
        }
    }

    public void getAll(Context ctx) {
        List<TournamentTeamDTO> tournamentTeamDTOs = tournamentTeamDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(tournamentTeamDTOs, TournamentTeamDTO.class);
    }

    public void create(Context ctx) {
        TournamentTeamDTO tournamentTeamDTO = ctx.bodyAsClass(TournamentTeamDTO.class);
        TournamentTeamDTO createdTournamentTeamDTO = tournamentTeamDAO.create(tournamentTeamDTO);
        ctx.res().setStatus(201);
        ctx.json(createdTournamentTeamDTO, TournamentTeamDTO.class);
    }

    public void update(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TournamentTeamDTO tournamentTeamDTO = tournamentTeamDAO.update(id, validateEntity(ctx));
            ctx.res().setStatus(200);
            ctx.json(tournamentTeamDTO, TournamentTeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "TournamentTeam not found");
        }
    }

    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TournamentTeamDTO deletedTournamentTeamDTO = tournamentTeamDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json(deletedTournamentTeamDTO, TournamentTeamDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "TournamentTeam not found");
        }
    }

    public TournamentTeamDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TournamentTeamDTO.class)
                .check(t -> t.getTournamentTeamName() != null, "TournamentTeam name must be set")
                .check(t -> t.getGame() != null, "Game must be associated")
                .get();
    }
}

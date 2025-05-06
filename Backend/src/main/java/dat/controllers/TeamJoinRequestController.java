package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.TeamJoinRequestDAO;
import dat.dtos.TeamJoinRequestDTO;
import dat.enums.JoinRequestStatus;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class TeamJoinRequestController {

    private final TeamJoinRequestDAO teamJoinRequestDAO;

    public TeamJoinRequestController() {
        if  (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.teamJoinRequestDAO = TeamJoinRequestDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.teamJoinRequestDAO = TeamJoinRequestDAO.getInstance(emf);
        }
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TeamJoinRequestDTO teamJoinRequestDTO = teamJoinRequestDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(teamJoinRequestDTO, TeamJoinRequestDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "TeamJoinRequest not found");
        }
    }

    public void getAll(Context ctx){
        List<TeamJoinRequestDTO> teamJoinRequestDTOs = teamJoinRequestDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(teamJoinRequestDTOs, TeamJoinRequestDTO.class);
    }

    public void create(Context ctx) throws ApiException{
        int requesterId = Integer.parseInt(ctx.pathParam("user_id"));
        int teamId = Integer.parseInt(ctx.pathParam("team_id"));
        int playerAccountId = Integer.parseInt(ctx.pathParam("player_account_id"));

        TeamJoinRequestDTO createdTeamDTO = teamJoinRequestDAO.create(requesterId, teamId, playerAccountId);
        ctx.res().setStatus(201);
        ctx.json(createdTeamDTO, TeamJoinRequestDTO.class);
    }


    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TeamJoinRequestDTO deletedTeamJoinRequestDTO = teamJoinRequestDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json(deletedTeamJoinRequestDTO, TeamJoinRequestDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "TeamJoinRequest not found");
        }
    }

    public void respondToRequest(Context ctx) throws ApiException {
        try {
            int requestId = Integer.parseInt(ctx.pathParam("request_id"));
            int userId = Integer.parseInt(ctx.pathParam("user_id"));
            JoinRequestStatus status = JoinRequestStatus.valueOf(ctx.pathParam("status").toUpperCase());

            TeamJoinRequestDTO updatedRequest = teamJoinRequestDAO.respondToRequest(requestId, userId, status);
            ctx.res().setStatus(200);
            ctx.json(updatedRequest, TeamJoinRequestDTO.class);
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new ApiException(400, "Invalid or missing parameter (request_id, user_id, or status)");
        }
    }

    public void getRequestsForTeam(Context ctx) throws ApiException {
        try {
            int teamId = Integer.parseInt(ctx.pathParam("team_id"));
            List<TeamJoinRequestDTO> requests = teamJoinRequestDAO.getRequestsForTeam(teamId);
            ctx.res().setStatus(200);
            ctx.json(requests, TeamJoinRequestDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: team_id");
        }
    }

    public void getRequestsForTeamCaptain(Context ctx) throws ApiException {
        try {
            int captainId = Integer.parseInt(ctx.pathParam("user_id"));
            List<TeamJoinRequestDTO> requests = teamJoinRequestDAO.getRequestsForTeamCaptain(captainId);
            ctx.res().setStatus(200);
            ctx.json(requests, TeamJoinRequestDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: team_captain_id");
        }
    }

}

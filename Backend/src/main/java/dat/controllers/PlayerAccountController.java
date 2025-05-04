package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.PlayerAccountDAO;
import dat.dtos.PlayerAccountDTO;
import dat.exceptions.ApiException;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PlayerAccountController {

    private final PlayerAccountDAO playerAccountDAO;

    public PlayerAccountController() {
        if (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.playerAccountDAO = PlayerAccountDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.playerAccountDAO = PlayerAccountDAO.getInstance(emf);
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

    public void updateStatus(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean status = Boolean.parseBoolean(ctx.queryParam("status"));
            PlayerAccountDTO playerAccountDTO = playerAccountDAO.updateStatus(id, status);
            ctx.res().setStatus(200);
            ctx.json(playerAccountDTO, PlayerAccountDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
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

}

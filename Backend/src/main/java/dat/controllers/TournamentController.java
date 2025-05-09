package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.TournamentDAO;
import dat.dtos.TournamentDTO;
import dat.exceptions.ApiException;
import dat.services.OffensiveWordsCheck;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class TournamentController {

    private final TournamentDAO tournamentDAO;

    public TournamentController() {
        if (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.tournamentDAO = TournamentDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("ALF4HUB_DB");
            this.tournamentDAO = TournamentDAO.getInstance(emf);
        }
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TournamentDTO tournamentDTO = tournamentDAO.getById(id);
            ctx.res().setStatus(200);
            ctx.json(tournamentDTO, TournamentDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Tournament not found");
        }
    }

    public void getAll(Context ctx) {
        List<TournamentDTO> tournamentDTOs = tournamentDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(tournamentDTOs, TournamentDTO.class);
    }

    public void create(Context ctx) {
        TournamentDTO tournamentDTO = validateEntity(ctx);

        OffensiveWordsCheck checker = new OffensiveWordsCheck("badwordslist.txt");
        Optional<String> offensiveWord = checker.findFirstOffensiveWord(tournamentDTO.getTournamentName());
        if (offensiveWord.isPresent()) {
            ctx.status(400).result("Tournament name contains offensive word: " + offensiveWord.get());
            return;
        }

        if (tournamentDAO.nameExists(tournamentDTO.getTournamentName())) {
            ctx.status(400).result("Tournament name already exists");
            return;
        }

        TournamentDTO createdTournamentDTO = tournamentDAO.create(tournamentDTO);
        ctx.res().setStatus(201);
        ctx.json(createdTournamentDTO, TournamentDTO.class);
    }


    public void update(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TournamentDTO tournamentDTO = tournamentDAO.update(id, validateEntity(ctx));
            ctx.res().setStatus(200);
            ctx.json(tournamentDTO, TournamentDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Tournament not found");
        }
    }

    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TournamentDTO deletedTournamentDTO = tournamentDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json(deletedTournamentDTO, TournamentDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing or invalid parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Tournament not found");
        }
    }

    public TournamentDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TournamentDTO.class)
                .check(t -> t.getTournamentName() != null && !t.getTournamentName().isEmpty(), "Tournament name must be set")
                .check(t -> t.getGame() != null, "Game must be associated")
                .check(t -> t.getTournamentSize() > 0, "Tournament size must be greater than 0")
                .check(t -> t.getTeamSize() > 0, "Team size must be greater than 0")
                .check(t -> t.getPrizePool() >= 0, "Prize pool must be 0 or greater")
                .check(t -> t.getRules() != null && !t.getRules().isEmpty(), "Rules must be set")
                .check(t -> t.getEntryRequirements() != null && !t.getEntryRequirements().isEmpty(), "Entry requirements must be set")
                .check(t -> t.getTournamentStatus() != null, "Tournament Status must be set")
                .check(t -> t.getStartDate() != null && !t.getStartDate().isEmpty(), "Start date must be set")
                .check(t -> t.getStartTime() != null && !t.getStartTime().isEmpty(), "Start time must be set")
                .check(t -> t.getEndDate() != null && !t.getEndDate().isEmpty(), "End date must be set")
                .check(t -> t.getEndTime() != null && !t.getEndTime().isEmpty(), "End time must be set")
                .get();
    }

    public void checkNameExists(Context ctx) throws ApiException{
        String name = ctx.queryParam("name");
        if (name == null || name.isEmpty()) {
            throw new ApiException(400, "Missing or invalid parameter: name");
        }

        boolean exists = tournamentDAO.nameExists(name);
        String message = exists
                ? "Tournament name is already taken"
                : "Tournament name is available";

        ctx.json(Map.of(
                "exists", exists,
                "message", message
        ));
    }

}
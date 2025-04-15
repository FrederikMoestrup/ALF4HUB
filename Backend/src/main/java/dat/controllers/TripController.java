package dat.controllers;

import dat.config.HibernateConfig;

import dat.daos.GuideDAO;
import dat.daos.TripDAO;
import dat.dtos.*;
import dat.enums.Category;
import dat.exceptions.ApiException;

import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TripController {

    private TripDAO tripDAO;
    private GuideDAO guideDAO;

    public TripController() {
        if (HibernateConfig.getTest()) {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
            this.tripDAO = TripDAO.getInstance(emf);
            this.guideDAO = GuideDAO.getInstance(emf);
        } else {
            EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory("guidetrip");
            this.tripDAO = TripDAO.getInstance(emf);
            this.guideDAO = GuideDAO.getInstance(emf);
        }

    }

    public void getAll(Context ctx) {
        List<TripDTO> tripDTOS = tripDAO.getAll();
        ctx.res().setStatus(200);
        ctx.json(tripDTOS, TripDTO.class);
    }

    public void getById(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO tripDTO = tripDAO.getById(id);
            GuideDTO guideDTO = guideDAO.getGuideByTrip(id);
            TripGuideResponseDTO response = new TripGuideResponseDTO(tripDTO, guideDTO);
            ctx.res().setStatus(200);
            ctx.json(response);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing required parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Trip not found");
        }
    }

    public void create(Context ctx) {
        TripDTO tripDTO = ctx.bodyAsClass(TripDTO.class);
        TripDTO createdTripDTO = tripDAO.create(tripDTO);
        ctx.res().setStatus(201);
        ctx.json(createdTripDTO, TripDTO.class);
    }

    public void update(Context ctx) {
        int id = ctx.pathParamAsClass("id", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();

        TripDTO tripDTO = tripDAO.update(id, validateEntity(ctx));
        ctx.res().setStatus(200);
        ctx.json(tripDTO, TripDTO.class);
    }

    public void delete(Context ctx) throws ApiException {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            TripDTO tripDTO = tripDAO.delete(id);
            ctx.res().setStatus(200);
            ctx.json(tripDTO, TripDTO.class);
        } catch (NumberFormatException e) {
            throw new ApiException(400, "Missing required parameter: id");
        } catch (ApiException e) {
            throw new ApiException(404, "Trip not found");
        }
    }

    public void getTripsByGuide(Context context) {
        int id = context.pathParamAsClass("id", Integer.class).check(this::validateGuidePrimaryKey, "Not a valid id").get();
        List<TripDTO> tripDTOS = tripDAO.getTripsByGuide(id);
        context.res().setStatus(200);
        context.json(tripDTOS, TripDTO.class);
    }

    public void addGuideToTrip(Context context) {
        int tripId = context.pathParamAsClass("tripId", Integer.class).check(this::validatePrimaryKey, "Not a valid id").get();
        int guideId = context.pathParamAsClass("guideId", Integer.class).check(this::validateGuidePrimaryKey, "Not a valid id").get();
        TripDTO tripDTO = tripDAO.addGuideToTrip(tripId, guideId);
        context.res().setStatus(200);
        context.json(tripDTO, TripDTO.class);
    }

    public void populate(Context ctx) {
        tripDAO.populate();
        ctx.res().setStatus(200);
    }

    public void getTripsByCategory(Context ctx) {
        String category = ctx.pathParam("category");
        List<TripDTO> tripDTOS = tripDAO.getTripsByCategory(Category.valueOf(category.toUpperCase()));
        ctx.res().setStatus(200);
        ctx.json(tripDTOS, TripDTO.class);
    }

    public void getTotalPriceByGuide(Context ctx) {

        List<GuideTotalPriceDTO> result = guideDAO.getTotalPriceByGuide();
        ctx.res().setStatus(200);
        ctx.json(result);
    }

    public TripDTO validateEntity(Context ctx) {
        return ctx.bodyValidator(TripDTO.class)
                .check(d -> d.getName() != null && !d.getName().isEmpty(), "Trip name must be set")
                .check(d -> d.getCategory() != null, "Trip category must be set")
                .check(d -> d.getPrice() > 0, "Trip price must be greater than 0")
                .check(d -> d.getStarttime() != null, "Trip start time must be set")
                .check(d -> d.getStartposition() != null && !d.getStartposition().isEmpty(), "Trip start position must be set")
                .get();
    }

    public boolean validatePrimaryKey(Integer integer) {
        return tripDAO.validatePrimaryKey(integer);
    }

    public boolean validateGuidePrimaryKey(Integer integer) {
        return tripDAO.validateGuidePrimaryKey(integer);
    }


    public void getItemsByCategory(Context context) {
        String category = context.pathParam("category");
        ItemService itemService = new ItemService();
        List<ItemDTO> itemDTOS = itemService.getAllItems(category);
        context.res().setStatus(200);
        context.json(itemDTOS, TripDTO.class);
    }
}

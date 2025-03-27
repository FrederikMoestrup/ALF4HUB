package dat.routes;


import dat.controllers.TripController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class TripRoute {

    private final TripController tripController = new TripController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", tripController::getAll, Role.USER);
            get("/{id}",tripController::getById, Role.USER);
            get("/guide/{id}", tripController::getTripsByGuide, Role.USER);
            post("/", tripController::create, Role.ADMIN);
            put("/{id}", tripController::update, Role.ADMIN);
            delete("/{id}", tripController::delete, Role.ADMIN);
            put("/add/{tripId}/guides/{guideId}", tripController::addGuideToTrip, Role.ADMIN);
            post("/populate", tripController::populate, Role.ADMIN);
            get("/category/{category}", tripController::getTripsByCategory, Role.USER);
            get("/guides/totalprice", tripController::getTotalPriceByGuide, Role.ADMIN);
            get("/items/{category}", tripController::getItemsByCategory, Role.USER);
        };
    }
}

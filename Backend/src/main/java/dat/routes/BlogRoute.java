package dat.routes;

import dat.controllers.BlogController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.delete;

public class BlogRoute {
    private final BlogController blogController = new BlogController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", blogController::getAll, Role.ANYONE); // This could be admin depending on frontend layout
            get("/preview", blogController::getAllPreview, Role.ANYONE); // This could be admin depending on frontend layout
            get("/{id}",blogController::getById, Role.ANYONE);
            post("/", blogController::create, Role.ANYONE);
        };
    }
}
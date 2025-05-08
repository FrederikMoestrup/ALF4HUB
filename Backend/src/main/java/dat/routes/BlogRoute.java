package dat.routes;

import dat.controllers.BlogController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class BlogRoute {
    private final BlogController blogController = new BlogController();

    protected EndpointGroup getRoutes() {

        return () -> {
            get("/", blogController::getAll, Role.ANYONE);
            get("/preview", blogController::getAllPreview, Role.ANYONE);
            get("/{id}",blogController::getById, Role.ANYONE);
            post("/", blogController::create, Role.ANYONE);
            post("/draft", blogController::createDraft, Role.ANYONE);
        };
    }
}
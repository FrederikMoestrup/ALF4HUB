package dat.routes;

import dat.controllers.BlogController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class BlogRoute {
    private final BlogController blogController = new BlogController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", blogController::create, Role.USER);
        };
    }
}
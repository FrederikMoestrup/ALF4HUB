package dat.routes;

import dat.controllers.WordCheckController;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.post;

public class WordCheckRoute {

    private final WordCheckController wordCheckController = new WordCheckController();

    protected EndpointGroup getRoutes() {
        return () -> {
            post("/check", wordCheckController::textChecking);
        };
    }
}


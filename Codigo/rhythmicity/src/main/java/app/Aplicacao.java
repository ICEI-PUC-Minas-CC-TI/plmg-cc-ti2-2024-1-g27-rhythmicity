package app;

import static spark.Spark.*;
import service.UserService;
import service.VideoService;

public class Aplicacao {
	
	private static UserService userService = new UserService();
	private static VideoService videoService = new VideoService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        get("/", (request, response) -> {
            response.redirect("forms.html");
            return null;
        });
                        
        // Rotas de login e cadastro
        post("/usuario/signup", (request, response) -> userService.signup(request, response));
        post("/usuario/login", (request, response) -> userService.login(request, response));

        // Rotas CRUD
        get("/usuario/:id", (request, response) -> userService.get(request, response));
        get("/usuario/list/:orderby", (request, response) -> userService.getAll(request, response));
        put("/usuario/update/:id", (request, response) -> userService.update(request, response));
        delete("/usuario/delete/:id", (request, response) -> userService.delete(request, response));

        // video
        post("/video/insert", (request, response) -> videoService.insert(request, response));

        get("/video/:id", (request, response) -> videoService.get(request, response));
        
        get("/video/list/:orderby", (request, response) -> videoService.getAll(request, response));

        get("/video/update/:id", (request, response) -> videoService.getToUpdate(request, response));
        
        post("/video/update/:id", (request, response) -> videoService.update(request, response));
           
        get("/video/delete/:id", (request, response) -> videoService.delete(request, response));
    }
}

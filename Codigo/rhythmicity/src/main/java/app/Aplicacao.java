package app;

import static spark.Spark.*;
import service.UserService;


public class Aplicacao {
	
	private static UserService userService = new UserService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/usuario/insert", (request, response) -> userService.insert(request, response));

        get("/usuario/:id", (request, response) -> userService.get(request, response));
        
        get("/usuario/list/:orderby", (request, response) -> userService.getAll(request, response));

        get("/usuario/update/:id", (request, response) -> userService.getToUpdate(request, response));
        
        post("/usuario/update/:id", (request, response) -> userService.update(request, response));
           
        get("/usuario/delete/:id", (request, response) -> userService.delete(request, response));
    }
}

package app;

import static spark.Spark.*;
import service.UserService;


public class Aplicacao {
	
	private static UserService userService = new UserService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/user/insert", (request, response) -> userService.insert(request, response));

        get("/user/:id", (request, response) -> userService.get(request, response));
        
        get("/user/list/:orderby", (request, response) -> userService.getAll(request, response));

        get("/user/update/:id", (request, response) -> userService.getToUpdate(request, response));
        
        post("/user/update/:id", (request, response) -> userService.update(request, response));
           
        get("/user/delete/:id", (request, response) -> userService.delete(request, response));

             
    }
}

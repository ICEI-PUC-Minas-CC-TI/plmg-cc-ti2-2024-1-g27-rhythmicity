package service;

import java.time.LocalDateTime;
import java.util.List;
import dao.UserDAO;
import model.User;
import spark.Request;
import spark.Response;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    // Método para cadastro
    public Object signup(Request request, Response response) {
        String email = request.queryParams("email");
        String senha = request.queryParams("senha");
        String nome = request.queryParams("nome");
        LocalDateTime dataCadastro = LocalDateTime.now();
        
        User usuario = new User(email, senha, nome, dataCadastro);
        
        if (userDAO.insert(usuario)) {
            response.status(201); // 201 Created
            response.redirect("/forms.html");
            return null;
        } else {
            response.status(400); // 400 Bad Request
            return "Erro ao cadastrar usuário!";
        }
    }

    // Método para login
    public Object login(Request request, Response response) {
        String email = request.queryParams("email");
        String senha = request.queryParams("senha");

        User usuario = userDAO.getByEmailAndPassword(email, senha);
        
        if (usuario != null) {
            response.status(200); // 200 OK
            response.redirect("/indexperfil.html?nome=" + usuario.getNome());
            return null;
        } else {
            response.status(401); // 401 Unauthorized
            return "Email ou senha inválidos!";
        }
    }

    // Método para buscar um usuário pelo ID
    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        User usuario = userDAO.getById(id);

        if (usuario != null) {
            response.status(200);
            return usuario;
        } else {
            response.status(404); // 404 Not Found
            return "Usuário não encontrado!";
        }
    }

    // Método para buscar todos os usuários
    public Object getAll(Request request, Response response) {
        List<User> usuarios = userDAO.getAll();

        if (!usuarios.isEmpty()) {
            response.status(200);
            return usuarios;
        } else {
            response.status(404); // 404 Not Found
            return "Nenhum usuário encontrado!";
        }
    }

    // Método para atualizar um usuário
    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        String email = request.queryParams("email");
        String senha = request.queryParams("senha");
        String nome = request.queryParams("nome");

        User usuario = new User(id, email, senha, nome, LocalDateTime.now());

        if (userDAO.update(usuario)) {
            response.status(200); // 200 OK
            return "Usuário atualizado com sucesso!";
        } else {
            response.status(400); // 400 Bad Request
            return "Erro ao atualizar usuário!";
        }
    }

    // Método para deletar um usuário
    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));

        if (userDAO.delete(id)) {
            response.status(200); // 200 OK
            return "Usuário deletado com sucesso!";
        } else {
            response.status(400); // 400 Bad Request
            return "Erro ao deletar usuário!";
        }
    }
}

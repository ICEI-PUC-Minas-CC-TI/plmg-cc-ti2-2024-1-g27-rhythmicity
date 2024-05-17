package service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import dao.UserDAO;
import model.User;
import spark.Request;
import spark.Response;
import spark.Session;

public class PerfilService {

    public Object carregaPag(Request request, Response response) {
        String form = "";
        String perfil = "";
        UserDAO userDAO = new UserDAO();
        User user;

        Path resourcePath = Paths.get("src", "main", "resources", "public");

        Path filePath = resourcePath.resolve("indexperfil.html");

        String nomeArquivo = "" + filePath;
        Session session = request.session();

        try {
            Scanner entrada = new Scanner(new File(nomeArquivo));
            while (entrada.hasNext()) {
                form += (entrada.nextLine() + "\n");
            }
            entrada.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (session.attribute("key") != null) {
            String usuario = session.attribute("usuario");
            user = userDAO.getPorNome(usuario);

            perfil += "<h5 id=\"usuario\" style=\"color: #E8E8E8\">" + user.getNome() + "</h5>";
        }

        form = form.replaceFirst("<PERFIL>", perfil);

        return form;
    }
}

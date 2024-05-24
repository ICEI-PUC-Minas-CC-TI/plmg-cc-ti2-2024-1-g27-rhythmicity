package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import dao.DAO;
import dao.UserDAO;
import model.User;
import spark.Request;
import spark.Response;

public class UserService {
	private UserDAO userDAO = new UserDAO();
	//private PaginaService paginaService = new PaginaService();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_Id = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_DATA_CADASTRO= 3;

	public UserService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new User(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new User(), orderBy);
	}

	
	public void makeForm(int tipo, User user, int orderBy) {
		Path resourcePath = Paths.get("src", "main", "resources", "public");

        Path filePath = resourcePath.resolve("cruds.html"); 
        String nomeArquivo = ""+filePath;
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umUser = "";
		if(tipo != FORM_INSERT) {
			umUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/usuario/list/1\">Novo User</a></b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";
			umUser += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/usuario/";
			String usuario, nome, senha, email, buttonLabel;
			LocalDateTime data_cadastro;
			//boolean gerenciador;
			if (tipo == FORM_INSERT){
			    action += "insert";
			    usuario = "Gerenciar Usuarios";
			    senha = "Inserir sua senha";
			    nome = "Inserir seu nome";
			    email = "Inserir e-mail";
			    data_cadastro = LocalDateTime.now();
			    buttonLabel = "Inserir";
			} else {
			    action += "update/" + user.getID();
			    usuario = "Atualizar User (Id " + user.getID() + ")";
			    senha = user.getSenha();
			    nome = user.getNome();
			    email = user.getEmail();
			    data_cadastro = user.getDataCadastro();
			    buttonLabel = "Atualizar";
			}

			umUser += "<!DOCTYPE html>";
			umUser += "<html lang=\"pt-br\">";
			umUser += "<head>";
			umUser += "<meta charset=\"UTF-8\">";
			umUser += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
			umUser += "<title>Formulário de Usuário</title>";
			umUser += "<style>";
			umUser += "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f9; }";
			umUser += ".container { margin-top: 20px; max-width: 800px; margin-left: auto; margin-right: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }";
			umUser += "h1 { text-align: center; color: #333; }";
			umUser += "table { width: 100%; border-collapse: collapse; }";
			umUser += "td { padding: 10px; }";
			umUser += "input[type='text'], input[type='password'], input[type='email'] { width: 100%; padding: 8px; margin: 5px 0; box-sizing: border-box; }";
			umUser += "input[type='submit'] { width: 100%; background-color: #490457; color: white; padding: 10px; margin: 5px 0; border: none; border-radius: 4px; cursor: pointer; }";
			umUser += "input[type='submit']:hover { background-color: #69067d; }";
			umUser += "</style>";
			umUser += "</head>";
			umUser += "<body>";
			umUser += "<div class=\"container\">";
			umUser += "<h1>" + usuario + "</h1>";
			umUser += "<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUser += "<table>";
			umUser += "<tr>";
			umUser += "<td>Senha: <input class=\"input--register\" type=\"password\" name=\"senha\" placeholder=\"" + senha + "\"></td>";
			umUser += "<td>E-mail: <input class=\"input--register\" type=\"email\" name=\"email\" placeholder=\"" + email + "\"></td>";
			umUser += "</tr>";
			umUser += "<tr>";
			umUser += "<td>Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" placeholder=\"" + nome + "\"></td>";
			umUser += "<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
			umUser += "</tr>";
			umUser += "</table>";
			umUser += "</form>";
			umUser += "</div>";
			umUser += "</body>";
			umUser += "</html>";

			} else if (tipo == FORM_DETAIL) {
			    umUser += "<!DOCTYPE html>";
			    umUser += "<html lang=\"pt-br\">";
			    umUser += "<head>";
			    umUser += "<meta charset=\"UTF-8\">";
			umUser += "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">";
			    umUser += "<title>Detalhes do Usuário</title>";
			    umUser += "<style>";
			    umUser += "body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f9; }";
			    umUser += ".container { margin-top: 20px; max-width: 800px; margin-left: auto; margin-right: auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }";
			    umUser += "h1 { text-align: center; color: #333; }";
			    umUser += "table { width: 100%; border-collapse: collapse; }";
			    umUser += "td { padding: 10px; }";
			    umUser += "</style>";
			    umUser += "</head>";
			    umUser += "<body>";
			    umUser += "<div class=\"container\">";
			    umUser += "<h1>Detalhes do Usuário</h1>";
			    umUser += "<table>";
			    umUser += "<tr>";
			    umUser += "<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar User (Id " + user.getID() + ")</b></font></td>";
			    umUser += "</tr>";
			    umUser += "<tr>";
			    umUser += "<td>Senha: " + user.getSenha() + "</td>";
			    umUser += "<td>e-mail: " + user.getEmail() + "</td>";
			    umUser += "</tr>";
			    umUser += "<tr>";
			    umUser += "<td>&nbsp;Nome: " + user.getNome() + "</td>";
			    umUser += "<td>Data de Cadastro: " + user.getDataCadastro().toString() + "</td>";
			    umUser += "<td>&nbsp;</td>";
			    umUser += "</tr>";
			    umUser += "</table>";
			    umUser += "</div>";
			    umUser += "</body>";
			    umUser += "</html>";
			
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-USER>", umUser);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Users</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_Id + "\"><b>Id</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_NOME + "\"><b>Usuario</b></a></td>\n" +
        		"\t<td><a href=\"/usuario/list/" + FORM_ORDERBY_DATA_CADASTRO + "\"><b>Data de Cadastro</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<User> users;
		if (orderBy == FORM_ORDERBY_Id) {                 	users = userDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		users = userDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_DATA_CADASTRO) {			users = userDAO.getOrderByDataCadastro();
		} else {											users = userDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		String color = "";
		for (User p : users) {
			bgcolor = (i++ % 2 == 0) ? "#09081C" : "#dddddd";
			color = (i % 2 == 0) ? "#09081C" : "#dddddd";
			list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
					"\t<td style=\"color: " + color + ";\">" + p.getID() + "</td>\n" +
					"\t<td style=\"color: " + color + ";\">" + p.getNome() + "</td>\n" +
					"\t<td style=\"color: " + color + ";\">" + p.getDataCadastro().toString() + "</td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/usuario/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUser('" + p.getID() + "', '" + p.getNome() + "', '" + p.getEmail() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USER>", list);				
	}
	
	public Object insert(Request request, Response response) throws Exception{
		String nome = request.queryParams("nome");
		String senha = request.queryParams("senha");
		String email = request.queryParams("email");
		LocalDateTime dataCadastro = LocalDateTime.now();
		
		String resp = "";
		
		User user = new User(-1, email, DAO.toMD5(senha), nome, dataCadastro);
		
		if(userDAO.insert(user) == true) {
            resp = "User (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "User (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
    // Método para cadastro
	public Object insertUser(Request request, Response response)throws Exception {
		String nome = request.queryParams("nome");
		String usuario = request.queryParams("usuario");
		String senha = request.queryParams("senha");
		String email = request.queryParams("email");
		LocalDateTime dataCadastro = LocalDateTime.now();
				
		String resp = "";
		
		User user = new User(-1, email, DAO.toMD5(senha), nome, dataCadastro);
		
		if(userDAO.insert(user) == true) {
            resp = "Conta criada com sucesso!";
            response.status(201); // 201 Created
		} else {
			resp = "Falha ao criar conta!";
			response.status(400); // 404 Not found
		}
		
		response.redirect("/forms.html");
			
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

    // Método para login
    public Object login(Request request, Response response) throws Exception {
        String email = request.queryParams("email");
        String senha = request.queryParams("senha");
    	String resp = "";
        User usuario = userDAO.getByEmailAndPassword(email, DAO.toMD5(senha));
        
        if (usuario != null) {
            response.status(200); // 200 OK
            response.redirect("/indexperfil.html?nome=" + usuario.getNome());
            return null;
        } else {
			response.redirect("/login.html");
			resp = "Usuario e/ou senha inválidos!";	
        }
        return resp;
    }

    // Método para buscar um usuário pelo ID
    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        User usuario = userDAO.getById(id);

        if (usuario != null) {
            response.status(200);
            return generateHtml(usuario);
        } else {
            response.status(404); // 404 Not Found
            return "Usuário não encontrado!";
        }
    }
    
    private String generateHtml(User usuario) {
        return "<!DOCTYPE html>" +
               "<html lang=\"pt-br\">" +
               "<head>" +
               "<meta charset=\"UTF-8\">" +
               "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
               "<title>Detalhes do Usuário</title>" +
               "<style>" +
               "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f9; }" +
               ".container { max-width: 600px; margin: 180px auto 150px auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
               "h1 { text-align: center; color: #333; }" +
               "p { line-height: 1.6; color: #666; }" +
               ".user-info { margin-bottom: 15px; }" +
               ".user-info span { font-weight: bold; color: #333; }" +

               /* Estilos do header */
               ".header-navbar { position: fixed; top: 0; left: 0; width: 95%; height: 80px; background: #490457; padding: 20px 40px; display: flex; justify-content: space-between; align-items: center; box-shadow: 0 15px 15px rgba(0,0,0,0.05); z-index: 1000; }" +
               ".logo img { max-width: 200px; display: block; margin: auto; }" +
               ".group { display: flex; align-items: center; }" +
               "header ul { position: relative; display: flex; gap: 30px; }" +
               "header ul li { list-style: none; }" +
               "header ul li a { position: relative; text-decoration: none; font: 1em; color: #E8E8E8; text-transform: uppercase; letter-spacing: 0.2em; }" +
               "header ul li a::before { content: ''; position: absolute; bottom: -2px; width: 100%; height: 2px; background: #E8E8E8; transform: scaleX(0); transition: transform 0.5s ease-in-out; transform-origin: right; }" +
               "header ul li a:hover::before { transform: scaleX(1); transform-origin: left; }" +
               ".search { position: relative; display: flex; justify-content: center; align-items: center; font-size: 1.5em; z-index: 10; cursor: pointer; color: #E8E8E8; }" +
               ".searchBox.active { right: 0; }" +
               ".searchBox { position: absolute; right: -100%; width: 100%; height: 100%; display: flex; background: #490457; align-items: center; padding: 0 30px; transition: 0.5 ease-in-out; }" +
               ".searchBox input { width: 100%; border: none; outline: none; height: 50px; color: #E8E8E8; font-size: 1.25em; background: #490457; border-bottom: 1px solid #E8E8E8; }" +
               ".searchBtn { position: relative; left: 30px; top: 2.5px; transition: 0.5s ease-in-out; }" +
               ".searchBtn.active { left: 0; }" +
               ".closeBtn { opacity: 0; visibility: hidden; transition: 0; scale: 0; }" +
               ".closeBtn.active { opacity: 1; visibility: visible; transition: 0.5s; scale: 1; }" +
               ".searchBtn.hidden { display: none; }" +
               ".searchBox.active .closeBtn { right: 30px; }" +
               ".menuToggle { position: relative; display: none; color: #E8E8E8; }" +
               "@media (max-width: 800px) { " +
               ".searchBtn { left: 0; }" +
               ".menuToggle { position: absolute; display: block; font-size: 2em; cursor: pointer; transform: translateX(30px); z-index: 10; }" +
               "header .navigation { position: absolute; opacity: 0; visibility: hidden; left: 100%; }" +
               "header.open .navigation { top: 80px; opacity: 1; visibility: visible; left: 0; display: flex; flex-direction: column; background: #490457; width: 100%; height: calc(100vh - 80px); padding: 40px; border-top: 1px solid rgba(0,0,0,0.05); }" +
               "header.open .navigation li a { font-size: 1.25em; }" +
               "}" +
               "</style>" +
               "</head>" +
               "<body>" +

               // Cabeçalho
               "<header class=\"header-navbar\">" +
               "<a href=\"/index.html\" class=\"logo\" id=\"logotype\">" +
               "<img src=\"/images/rhythmicity logotipo branco.png\" alt=\"Logo1\">" +
               "</a>" +
               "<div class=\"group\">" +
               "<ul class=\"navigation\">" +
               "<li><a href=\"/forms.html\" id=\"log\"></a></li>" +
               "<li><a href=\"/index.html\" id=\"sign\">Voltar</a></li>" +
               "<li><a href=\"#\" id=\"perfil\"><ion-icon size=\"large\" name=\"person-circle-outline\"></ion-icon></a></li>" +
               "</ul>" +
               "</div>" +
               "</header>" +

               "<div class=\"container\">" +
               "<h1>Detalhes do Usuário</h1>" +
               "<div class=\"user-info\"><span>ID:</span> " + usuario.getID() + "</div>" +
               "<div class=\"user-info\"><span>Nome:</span> " + usuario.getNome() + "</div>" +
               "<div class=\"user-info\"><span>Email:</span> " + usuario.getEmail() + "</div>" +
               "<div class=\"user-info\"><span>Data de Cadastro:</span> " + usuario.getDataCadastro().toString() + "</div>" +
               "</div>" +
               "</body>" +
               "</html>";
    }


    
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		User user = (User) userDAO.get(id);
		
		if (user != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, user, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "User " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

    // Método para buscar todos os usuários
    public Object getAll(Request request, Response response) {
    	int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        return form;
    }

    // Método para atualizar um usuário
    public Object update(Request request, Response response) throws Exception {
        int id = Integer.parseInt(request.params(":id"));
        User user = userDAO.get(id);
        String resp = "";

        if (user != null) {
            user.setSenha(DAO.toMD5(request.queryParams("senha")));
            user.setEmail(request.queryParams("email"));
            user.setNome(request.queryParams("nome"));
            
            // Aqui está a alteração para lidar com o possível valor nulo do parâmetro "data_cadastro"
            String dataCadastroParam = request.queryParams("data_cadastro");
            if (dataCadastroParam != null) {
                LocalDateTime dataCadastro = LocalDateTime.parse(dataCadastroParam);
                user.setDataCadastro(dataCadastro);
            } else {
                user.setDataCadastro(null); // Ou outra lógica apropriada para quando data_cadastro for nulo
            }
            
            userDAO.update(user);
            response.status(200); // success
            resp = "Usuário (Id " + user.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Usuário (Id " + id + ") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }


    // Método para deletar um usuário
    public Object delete(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
    	//System.out.println("id: " + id);
        User user = userDAO.get(id);
        String resp = "";

        if (user != null) {
            userDAO.delete(id);
            response.status(200); // success
            resp = "User (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "User (" + id + ") não encontrado!";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}

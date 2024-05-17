package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.io.File;
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
	private final int FORM_ORDERBY_DATA_NASCIMENTO= 3;

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
		String nomeArquivo = "cruds.html";
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
			umUser += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/user/list/1\">Novo User</a></b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";
			umUser += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/user/";
			String usuario, nome, senha, email, buttonLabel;
			LocalDateTime data_cadastro;
			//boolean gerenciador;
			if (tipo == FORM_INSERT){
				action += "insert";
				usuario = "Inserir User";
				senha = "Inserir sua senha";
				nome = "Inserir seu nome";
				email = "Inserir e-mail";
				data_cadastro = LocalDateTime.now();
				//gerenciador = false;
				buttonLabel = "Inserir";
			} else {
				action += "update/" + user.getID();
				usuario = "Atualizar User (Id " + user.getID() + ")";
				senha = user.getSenha();
				nome = user.getNome();
				email = user.getEmail();
				data_cadastro = user.getDataCadastro();
				//gerenciador = user.getGerenciador();
				buttonLabel = "Atualizar";
			}
			umUser += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + usuario + "</b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td>&nbsp;Usuário: <input class=\"input--register\" type=\"text\" name=\"usuario\" placeholder =\""+ usuario +"\"></td>";
			umUser += "\t\t\t<td>Senha: <input class=\"input--register\" type=\"password\" name=\"senha\" placeholder=\""+ senha +"\"></td>";
			umUser += "\t\t\t<td>E-mail: <input class=\"input--register\" type=\"email\" name=\"email\" placeholder=\""+ email +"\"></td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" placeholder=\""+ nome + "\"></td>";
			umUser += "\t\t\t<td>Data de Nascimento: <input class=\"input--register\" type=\"date\" name=\"data_cadastro\" placeholder=\""+ data_cadastro + "\"></td>";
			//umUser += "\t\t\t<td>Gerenciador: <input class=\"input--register\" type=\"text\" name=\"gerenciador\" placeholder=\""+ gerenciador + "\"></td>";
			umUser += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";
			umUser += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umUser += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar User (Id " + user.getID() + ")</b></font></td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			//umUser += "\t\t\t<td>&nbsp;Usuário: "+ user.getUsuario() +"</td>";
			umUser += "\t\t\t<td>Senha: "+ user.getSenha() +"</td>";
			umUser += "\t\t\t<td>e-mail: "+ user.getEmail() +"</td>";
			umUser += "\t\t</tr>";
			umUser += "\t\t<tr>";
			umUser += "\t\t\t<td>&nbsp;Nome: "+ user.getNome() + "</td>";
			umUser += "\t\t\t<td>Idade: "+ user.getDataCadastro().toString() + "</td>";
			//umUser += "\t\t\t<td>Gerenciador: "+ user.getGerenciador() + "</td>";
			umUser += "\t\t\t<td>&nbsp;</td>";
			umUser += "\t\t</tr>";
			umUser += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-USER>", umUser);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Users</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_Id + "\"><b>Id</b></a></td>\n" +
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_NOME + "\"><b>Usuario</b></a></td>\n" +
        		"\t<td><a href=\"/user/list/" + FORM_ORDERBY_DATA_NASCIMENTO + "\"><b>Idade</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<User> users;
		if (orderBy == FORM_ORDERBY_Id) {                 	users = userDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		users = userDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_DATA_NASCIMENTO) {			users = userDAO.getOrderByDataCadastro();
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
					//"\t<td style=\"color: " + color + ";\">" + p.getUsuario() + "</td>\n" +
					"\t<td style=\"color: " + color + ";\">" + p.getDataCadastro().toString() + "</td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/user/" + p.getID() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/user/update/" + p.getID() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteUser('" + p.getID() + "', '" + "', '" + p.getNome() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-USER>", list);				
	}
	
    // Método para cadastro
    public Object signup(Request request, Response response) throws Exception {
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
    public Object login(Request request, Response response) throws Exception {
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

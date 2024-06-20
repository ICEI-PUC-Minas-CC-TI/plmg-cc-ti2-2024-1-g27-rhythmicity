package service;

import java.util.Scanner;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.VideoDAO;
import model.Video;
import spark.Request;
import spark.Response;


public class VideoService {

	private VideoDAO videoDAO = new VideoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_DESCRICAO = 2;
	private final int FORM_ORDERBY_TITULO = 3;
	
	
	public VideoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Video(), FORM_ORDERBY_DESCRICAO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Video(), orderBy);
	}

	
	public void makeForm(int tipo, Video video, int orderBy) {
		String nomeArquivo = "/form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umVideo = "";
		if(tipo != FORM_INSERT) {
			umVideo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/video/list/1\">Novo Video</a></b></font></td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t</table>";
			umVideo += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/video/";
			String name, descricao, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Video";
				descricao = "leite, pão, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + video.getID();
				name = "Atualizar Video (ID " + video.getID() + ")";
				descricao = video.getDescricao();
				buttonLabel = "Atualizar";
			}
			umVideo += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umVideo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td>&nbsp;Descrição: <input class=\"input--register\" type=\"text\" name=\"descricao\" value=\""+ descricao +"\"></td>";
			umVideo += "\t\t\t<td>Titulo: <input class=\"input--register\" type=\"text\" name=\"titulo\" value=\""+ video.getTitulo() +"\"></td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td>&nbsp;Data de fabricação: <input class=\"input--register\" type=\"text\" name=\"dataPublicacao\" value=\""+ video.getDataPublicacao().toString() + "\"></td>";
			umVideo += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t</table>";
			umVideo += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umVideo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Video (ID " + video.getID() + ")</b></font></td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td>&nbsp;Descrição: "+ video.getDescricao() +"</td>";
			umVideo += "\t\t\t<td>Titulo: "+ video.getTitulo() +"</td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t\t<tr>";
			umVideo += "\t\t\t<td>&nbsp;Data de fabricação: "+ video.getDataPublicacao().toString() + "</td>";
			umVideo += "\t\t\t<td>&nbsp;</td>";
			umVideo += "\t\t</tr>";
			umVideo += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Video>", umVideo);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Videos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/video/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/video/list/" + FORM_ORDERBY_DESCRICAO + "\"><b>Descrição</b></a></td>\n" +
        		"\t<td><a href=\"/video/list/" + FORM_ORDERBY_TITULO + "\"><b>Preço</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Video> videos;
		if (orderBy == FORM_ORDERBY_ID) {                 	videos = videoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_DESCRICAO) {		videos = videoDAO.getOrderByDescricao();
		} else if (orderBy == FORM_ORDERBY_TITULO) {			videos = videoDAO.getOrderByTitulo();
		} else {											videos = videoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Video p : videos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getID() + "</td>\n" +
            		  "\t<td>" + p.getDescricao() + "</td>\n" +
            		  "\t<td>" + p.getTitulo() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/Video/" + p.getID() + "\"><img src=\"/image/detail.svg\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/Video/update/" + p.getID() + "\"><img src=\"/image/update.svg\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteVideo('" + p.getID() + "', '" + p.getDescricao() + "', '" + p.getTitulo() + "');\"><img src=\"/image/delete.svg\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-Video>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String descricao = request.queryParams("descricao");
		String titulo = request.queryParams("titulo");
		LocalDateTime dataPublicacao = LocalDateTime.parse(request.queryParams("dataPublicacao"));
		
		String resp = "";
		
		Video Video = new Video(-1, descricao, titulo, dataPublicacao);
		
		if(videoDAO.insert(Video) == true) {
            resp = "Video (" + descricao + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Video (" + descricao + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Video Video = (Video) videoDAO.get(id);
		
		if (Video != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, Video, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Video " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Video Video = (Video) videoDAO.get(id);
		
		if (Video != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, Video, FORM_ORDERBY_DESCRICAO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Video " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Video Video = videoDAO.get(id);
        String resp = "";       

        if (Video != null) {
        	Video.setDescricao(request.queryParams("descricao"));
        	Video.setTitulo(request.queryParams("titulo"));
        	Video.setDataPublicacao(LocalDateTime.parse(request.queryParams("dataPublicacao")));
        	videoDAO.update(Video);
        	response.status(200); // success
            resp = "Video (ID " + Video.getID() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Video (ID \" + Video.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Video Video = videoDAO.get(id);
        String resp = "";       

        if (Video != null) {
            videoDAO.delete(id);
            response.status(200); // success
            resp = "Video (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Video (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}
package dao;

import model.Video;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class VideoDAO extends DAO {	
	public VideoDAO() {
		super();
		conectar();
	}
	
	
	public void finalize() {
		close();
	}
	
	
	public boolean insert(Video video) {
		boolean status = false;
		try {
			String sql = "INSERT INTO video (descricao, titulo, datapublicacao) "
		               + "VALUES ('" + video.getDescricao() + "', "
		               + video.getTitulo() + ", " + ", ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(video.getDataPublicacao()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Video get(int id) {
		Video video = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM video WHERE id="+id;
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 video = new Video(rs.getInt("id"), rs.getString("descricao"), rs.getString("titulo"), 
	        			               rs.getTimestamp("datapublicacao").toLocalDateTime());
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return video;
	}
	
	
	public List<Video> get() {
		return get("");
	}

	
	public List<Video> getOrderByID() {
		return get("id");		
	}
	
	
	public List<Video> getOrderByDescricao() {
		return get("descricao");		
	}
	
	
	public List<Video> getOrderByTitulo() {
		return get("titulo");		
	}
	
	
	private List<Video> get(String orderBy) {
		List<Video> videos = new ArrayList<Video>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM video" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Video p = new Video(rs.getInt("id"), rs.getString("descricao"), rs.getString("titulo"), 
	        			                rs.getTimestamp("datapublicacao").toLocalDateTime());
	            videos.add(p);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return videos;
	}
	
	
	public boolean update(Video video) {
		boolean status = false;
		try {  
			String sql = "UPDATE video SET descricao = '" + video.getDescricao() + "', "
					   + "titulo = " + video.getTitulo() + ", " 
					   + "datapublicacao = ?, " 
					   + "WHERE id = " + video.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
		    st.setTimestamp(1, Timestamp.valueOf(video.getDataPublicacao()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	public boolean delete(int id) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			st.executeUpdate("DELETE FROM video WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}
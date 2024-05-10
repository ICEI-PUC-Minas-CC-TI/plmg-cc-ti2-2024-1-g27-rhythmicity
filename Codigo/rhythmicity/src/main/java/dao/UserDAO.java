package dao;

import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DAO {
	public UserDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}

	public boolean insert(User usuario) {
		boolean status = false;
		try {
			String sql = "INSERT INTO usuario (email, password, datacadastro) "
					+ "VALUES ('" + usuario.getEmail() + "', "
					+ usuario.getPassword() + ", ?);";
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setTimestamp(1, Timestamp.valueOf(usuario.getDataCadastro()));
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}

	public User get(int id) {
		User usuario = null;

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario WHERE id=" + id;
			ResultSet rs = st.executeQuery(sql);
			if (rs.next()) {
				usuario = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
						rs.getTimestamp("datacadastro").toLocalDateTime());
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuario;
	}

	public List<User> get() {
		return get("");
	}

	public List<User> getOrderByID() {
		return get("id");
	}

	public List<User> getOrderByEmail() {
		return get("email");
	}

	public List<User> getOrderByPassword() {
		return get("password");
	}

	private List<User> get(String orderBy) {
		List<User> usuarios = new ArrayList<User>();

		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				User p = new User(rs.getInt("id"), rs.getString("email"), rs.getString("password"),
						rs.getTimestamp("datacadastro").toLocalDateTime());
				usuarios.add(p);
			}
			st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return usuarios;
	}

	public boolean update(User usuario) {
		boolean status = false;
		try {
			String sql = "UPDATE usuario SET email = '" + usuario.getEmail() + "', "
					+ "password = " + usuario.getPassword() + ", "
					+ "datacadastro = ?, "
					+ "WHERE id = " + usuario.getID();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.setTimestamp(1, Timestamp.valueOf(usuario.getDataCadastro()));
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
			st.executeUpdate("DELETE FROM usuario WHERE id = " + id);
			st.close();
			status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
}
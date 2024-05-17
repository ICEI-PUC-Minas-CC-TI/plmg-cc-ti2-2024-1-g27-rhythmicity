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

    public boolean insert(User user) {
        String sql = "INSERT INTO usuario (email, senha, nome, data_cadastro) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getSenha());
            stmt.setString(3, user.getNome());
            stmt.setTimestamp(4, Timestamp.valueOf(user.getDataCadastro()));
            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User getByEmailAndPassword(String email, String senha) {
        try {
            String sql = "SELECT * FROM usuario WHERE email = ? AND senha = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("email"), rs.getString("senha"), rs.getString("nome"), rs.getTimestamp("data_cadastro").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User get(int id) {
        User usuario = null;

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM usuario WHERE id=" + id;
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                usuario = new User(rs.getInt("id"), rs.getString("email"), rs.getString("senha"), rs.getString("nome"), rs.getTimestamp("data_cadastro").toLocalDateTime());
            }
            st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return usuario;
    }

    public User getById(int id) {
        try {
            String sql = "SELECT * FROM usuario WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("email"), rs.getString("senha"), rs.getString("nome"), rs.getTimestamp("data_cadastro").toLocalDateTime());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM usuario";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("email"), rs.getString("senha"), rs.getString("nome"), rs.getTimestamp("data_cadastro").toLocalDateTime()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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

    public List<User> getOrderBySenha() {
        return get("senha");
    }

    private List<User> get(String orderBy) {
        List<User> usuarios = new ArrayList<User>();

        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            String sql = "SELECT * FROM usuario" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                User p = new User(rs.getInt("id"), rs.getString("email"), rs.getString("senha"), rs.getString("nome"), rs.getTimestamp("data_cadastro").toLocalDateTime());
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
            String sql = "UPDATE usuario SET email = ?, senha = ?, nome = ?, data_cadastro = ? WHERE id = ?";
            PreparedStatement st = conexao.prepareStatement(sql);
            st.setString(1, usuario.getEmail());
            st.setString(2, usuario.getSenha());
            st.setString(3, usuario.getNome());
            st.setTimestamp(4, Timestamp.valueOf(usuario.getDataCadastro()));
            st.setInt(5, usuario.getID());
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

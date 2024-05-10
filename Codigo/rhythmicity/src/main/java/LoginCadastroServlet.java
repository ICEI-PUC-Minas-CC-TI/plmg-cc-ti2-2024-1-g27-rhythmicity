import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LoginCadastroServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/usuario";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "ti2cc";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/login")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                boolean loggedIn = login(connection, username, password);
                connection.close();
                if (loggedIn) {
                    response.sendRedirect("/dashboard.html");
                } else {
                    response.sendRedirect("/login.html?error=1");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("/login.html?error=1");
            }
        } else if (action.equals("/signup")) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            try {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                cadastrar(connection, username, password);
                connection.close();
                response.sendRedirect("/login.html");
            } catch (SQLException e) {
                e.printStackTrace();
                response.sendRedirect("/signup.html?error=1");
            }
        }
    }

    private static boolean login(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    private static void cadastrar(Connection connection, String username, String password) throws SQLException {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.executeUpdate();
    }
}

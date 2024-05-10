import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginCadastro{

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/seu_banco_de_dados";
    private static final String DB_USER = "seu_usuario";
    private static final String DB_PASSWORD = "sua_senha";

    public static void main(String[] args) {
        try {
            // Conecta ao banco de dados
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            // Cria tabela se não existir
            createTable(connection);
            
            // Exemplo de consulta
            String username = "exemplo";
            String password = "senha123";
            if (login(connection, username, password)) {
                System.out.println("Login bem sucedido!");
            } else {
                System.out.println("Usuário ou senha inválidos!");
            }

            // Exemplo de cadastro
            String novoUsername = "novo_usuario";
            String novaSenha = "nova_senha";
            cadastrar(connection, novoUsername, novaSenha);

            // Fecha a conexão
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para criar a tabela se não existir
    private static void createTable(Connection connection) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS usuarios (id SERIAL PRIMARY KEY, username VARCHAR(50) UNIQUE, password VARCHAR(50))";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    // Método para fazer login
    private static boolean login(Connection connection, String username, String password) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();
    }

    // Método para cadastrar um novo usuário
    private static void cadastrar(Connection connection, String username, String password) throws SQLException {
        String sql = "INSERT INTO usuarios (username, password) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        statement.setString(2, password);
        statement.executeUpdate();
        System.out.println("Usuário cadastrado com sucesso!");
    }
}

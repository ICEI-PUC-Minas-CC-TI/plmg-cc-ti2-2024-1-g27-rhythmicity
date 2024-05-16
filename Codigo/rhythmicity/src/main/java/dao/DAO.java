package dao;

import java.sql.*;

public class DAO {
    protected Connection conexao;

    public DAO() {
        conexao = null;
    }

    public boolean conectar() {
        String driverName = "org.postgresql.Driver";
        // Dados do seu servidor PostgreSQL no Azure
        String serverName = "ti2cc.postgres.database.azure.com";
        String mydatabase = "usuario";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta + "/" + mydatabase;
        String username = "adm";
        String password = "@Pucminas";
        boolean status = false;

        // Configurações adicionais para conexão SSL
        String sslMode = "require"; // ou "verify-full" dependendo das configurações do seu servidor
        url += "?sslmode=" + sslMode;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao != null);
            System.out.println("Conexão efetuada com o PostgreSQL no Azure!");
        } catch (ClassNotFoundException e) {
            System.err.println("Conexão NÃO efetuada com o PostgreSQL no Azure -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o PostgreSQL no Azure -- " + e.getMessage());
        }

        return status;
    }

    public boolean close() {
        boolean status = false;

        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                status = true;
                System.out.println("Conexão fechada com sucesso.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar a conexão: " + e.getMessage());
        }
        return status;
    }
}

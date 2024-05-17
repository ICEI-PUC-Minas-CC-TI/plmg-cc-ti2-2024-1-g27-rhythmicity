package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class User {
    private int id;
    private String email;
    private String senha;
    private String nome; // Novo atributo
    private LocalDateTime data_cadastro;
    private String playlistEstudos;

    public User() {
        id = -1;
        email = "";
        senha = "";
        nome = ""; // Inicializa com uma string vazia
        data_cadastro = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }

    // Construtor completo
    public User(int id, String email, String senha, String nome, LocalDateTime dataCadastro) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.data_cadastro = dataCadastro;
    }

    // Construtor sem id
    public User(String email, String senha, String nome, LocalDateTime dataCadastro) {
        this.email = email;
        this.senha = senha;
        this.nome = nome;
        this.data_cadastro = dataCadastro;
    }

    public int getID() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getDataCadastro() {
        return data_cadastro;
    }

    public void setDataCadastro(LocalDateTime data_cadastro) {
        // Pega a Data Atual
        LocalDateTime agora = LocalDateTime.now();
        // Garante que a data de fabricação não pode ser futura
        if (agora.compareTo(data_cadastro) >= 0)
            this.data_cadastro = data_cadastro;
    }


    public String getPlaylistEstudos() {
        return playlistEstudos;
    }

    public void setPlaylistEstudos(String playlistEstudos) {
        this.playlistEstudos = playlistEstudos;
    }

    /**
     * Método sobreposto da classe Object. É executado quando um objeto precisa
     * ser exibido na forma de String.
     */
    @Override
    public String toString() {
        return "Nome: " + nome + "   Email: " + email + "   Data de cadastro: " + data_cadastro;
    }

    @Override
    public boolean equals(Object obj) {
        return (this.getID() == ((User) obj).getID());
    }
}

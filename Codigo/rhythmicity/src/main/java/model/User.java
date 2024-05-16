package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class User {
	private int id;
	private String email;
	private String password;
	private LocalDateTime dataNascimento;
	private String playlistEstudos;
	
	public User() {
		id = -1;
		email = "";
		password = "";
		dataNascimento = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	public User(int id, String email, String password, LocalDateTime Nascimento) {
		setId(id);
		setEmail(email);
		setPassword(password);
		setDataNascimento(Nascimento);
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDateTime dataNascimento) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(dataNascimento) >= 0)
			this.dataNascimento = dataNascimento;
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
		return "Email: " + email + "   Data de cadastro: "
				+ dataNascimento;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((User) obj).getID());
	}	
}
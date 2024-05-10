package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class User {
	private int id;
	private String email;
	private String password;
	private LocalDateTime dataCadastro;	
	
	public User() {
		id = -1;
		email = "";
		password = "";
		dataCadastro = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	public User(int id, String email, String password, LocalDateTime Cadastro) {
		setId(id);
		setEmail(email);
		setPassword(password);
		setDataCadastro(Cadastro);
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

	public LocalDateTime getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(LocalDateTime dataCadastro) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(dataCadastro) >= 0)
			this.dataCadastro = dataCadastro;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Email: " + email + "   Data de cadastro: "
				+ dataCadastro;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((User) obj).getID());
	}	
}
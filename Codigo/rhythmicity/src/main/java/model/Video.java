package model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Video {
	private int id;
	private String descricao;
	private String titulo;
	private LocalDateTime dataPublicacao;	
	
	public Video() {
		id = -1;
		descricao = "";
        titulo = "";
		dataPublicacao = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
	}

	public Video(int id, String descricao, String titulo, LocalDateTime Publicacao) {
		setId(id);
		setDescricao(descricao);
		setDataPublicacao(Publicacao);
        setTitulo(titulo);
	}		
	
	public int getID() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public LocalDateTime getDataPublicacao() {
		return dataPublicacao;
	}

	public void setDataPublicacao(LocalDateTime dataPublicacao) {
		// Pega a Data Atual
		LocalDateTime agora = LocalDateTime.now();
		// Garante que a data de fabricação não pode ser futura
		if (agora.compareTo(dataPublicacao) >= 0)
			this.dataPublicacao = dataPublicacao;
	}

	/**
	 * Método sobreposto da classe Object. É executado quando um objeto precisa
	 * ser exibido na forma de String.
	 */
	@Override
	public String toString() {
		return "Titulo: " + titulo + "  Data de Publicacao: " 
				+ dataPublicacao + "Descricao: " + descricao;
	}
	
	@Override
	public boolean equals(Object obj) {
		return (this.getID() == ((Video) obj).getID());
	}	
}
package bean;

import java.io.Serializable;

public class Ramo implements Serializable {

	private static final long serialVersionUID = -7607267148492803349L;
	private String codigo;
	private String nome;
	private String depto;

	// JIRA-167098
	public Ramo() {};
	public Ramo(String codigo, String nome, String depto) {
		this.codigo = codigo;
		this.nome = nome;
		this.depto = depto;
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

}

package bean;

import java.io.Serializable;

public class Produtor implements Serializable {

	private static final long serialVersionUID = -2351168450970650776L;
	private String codigo;
	private String nome;
	private String email;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return (nome == null ? "" : nome);
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return (email == null ? "" : email);
	}

	public void setEmail(String email) {
		this.email = email;
	}
}

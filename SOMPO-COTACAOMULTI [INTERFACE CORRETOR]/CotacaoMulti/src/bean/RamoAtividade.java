package bean;

import java.io.Serializable;

public class RamoAtividade implements Serializable, Dominio {

	private static final long serialVersionUID = 2211872143745184773L;
	private String id = "";
	private String nome = "";
	private String codSyas = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodSyas() {
		return codSyas;
	}

	public void setCodSyas(String codSyas) {
		this.codSyas = codSyas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int compareTo(Dominio o) {
		if (this.getNome().compareToIgnoreCase(o.getNome()) < 0) {
			return -1;
		}
		if (this.getNome().compareToIgnoreCase(o.getNome()) > 0) {
			return 1;
		}
		return 0;
	}

}

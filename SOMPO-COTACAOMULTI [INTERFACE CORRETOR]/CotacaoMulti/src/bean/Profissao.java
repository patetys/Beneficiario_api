package bean;

import java.io.Serializable;

public class Profissao implements Serializable, Dominio {
	
	private static final long serialVersionUID = -3232023757357621886L;
	private String id = "";
	private String subId = "";
	private String nome = "";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getIdSubId(){
		return this.id + this.subId;
	}

	@Override
	public int compareTo(Dominio o) {
		if (this.nome.compareToIgnoreCase(o.getNome()) < 0) {
            return -1;
        }
        if (this.nome.compareToIgnoreCase(o.getNome()) > 0) {
            return 1;
        }
        return 0;
	}



}

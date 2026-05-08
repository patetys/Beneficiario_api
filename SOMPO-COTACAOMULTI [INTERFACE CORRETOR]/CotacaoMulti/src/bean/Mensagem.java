package bean;

import java.io.Serializable;

public class Mensagem implements Serializable {

	private static final long serialVersionUID = -4965528077325288179L;
	private String flag;
	private String descricao;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}

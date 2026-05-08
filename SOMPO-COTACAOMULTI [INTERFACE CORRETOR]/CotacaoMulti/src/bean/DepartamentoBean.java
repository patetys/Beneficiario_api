package bean;

import java.io.Serializable;

public class DepartamentoBean implements Serializable {

	private static final long serialVersionUID = 5824283251132483650L;

	private String codigo;
	private String nome;
	
	
	/**
	 * @return
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * @param string
	 */
	public void setCodigo(String string) {
		codigo = string;
	}

	/**
	 * @param string
	 */
	public void setNome(String string) {
		nome = string;
	}

}

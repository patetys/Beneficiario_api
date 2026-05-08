package bean;

import java.io.Serializable;

public class Emissao implements Serializable, Comparable<Emissao> {

	private static final long serialVersionUID = -2322850171509321097L;
	private String codigo = "";
	private String nome = "";

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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * Ordenacao decrescente.
	 */
	@Override
	public int compareTo(Emissao o) {
		if (o.getNome().compareToIgnoreCase(this.nome) < 0) {
            return -1;
        }
        if (o.getNome().compareToIgnoreCase(this.nome) > 0) {
            return 1;
        }
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emissao other = (Emissao) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

}

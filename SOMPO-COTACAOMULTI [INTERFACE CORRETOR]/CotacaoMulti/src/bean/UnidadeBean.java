package bean;

import java.io.Serializable;

public class UnidadeBean implements Serializable, Dominio {

	private static final long serialVersionUID = -758629780090274273L;

	private String unidNegocio;
	private String codUnidNegocio;
	private String codProdutor;
	private String nomDepto;

	public String getCodProdutor() {
		return codProdutor;
	}

	public String getNomDepto() {
		return nomDepto;
	}

	public String getUnidNegocio() {
		return unidNegocio;
	}

	public void setCodProdutor(String codProdutor) {
		this.codProdutor = codProdutor;
	}

	public void setNomDepto(String nomDepto) {
		this.nomDepto = nomDepto;
	}

	public void setUnidNegocio(String unidNegocio) {
		this.unidNegocio = unidNegocio;
	}

	public String getCodUnidNegocio() {
		return codUnidNegocio;
	}

	public void setCodUnidNegocio(String codUnidNegocio) {
		this.codUnidNegocio = codUnidNegocio;
	}
	
	public String getUnidadeNegocioCodigoProdutor(){
		return this.unidNegocio+"-"+this.codProdutor;
	}

	@Override
	public int compareTo(Dominio o) {
		if (this.nomDepto.compareToIgnoreCase(o.getNome()) < 0) {
            return -1;
        }
        if (this.nomDepto.compareToIgnoreCase(o.getNome()) > 0) {
            return 1;
        }
        return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codProdutor == null) ? 0 : codProdutor.hashCode());
		result = prime * result + ((codUnidNegocio == null) ? 0 : codUnidNegocio.hashCode());
		result = prime * result + ((nomDepto == null) ? 0 : nomDepto.hashCode());
		result = prime * result + ((unidNegocio == null) ? 0 : unidNegocio.hashCode());
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
		UnidadeBean other = (UnidadeBean) obj;
		if (codProdutor == null) {
			if (other.codProdutor != null)
				return false;
		} else if (!codProdutor.equals(other.codProdutor))
			return false;
		if (codUnidNegocio == null) {
			if (other.codUnidNegocio != null)
				return false;
		} else if (!codUnidNegocio.equals(other.codUnidNegocio))
			return false;
		if (nomDepto == null) {
			if (other.nomDepto != null)
				return false;
		} else if (!nomDepto.equals(other.nomDepto))
			return false;
		if (unidNegocio == null) {
			if (other.unidNegocio != null)
				return false;
		} else if (!unidNegocio.equals(other.unidNegocio))
			return false;
		return true;
	}

	@Override
	public String getId() {
		return unidNegocio;
	}

	@Override
	public String getNome() {
		return nomDepto;
	}

}
package bean;

import java.io.Serializable;
import java.util.HashMap;

public class Cep implements Serializable {

	private static final long serialVersionUID = 5769165074932400907L;
	private String tpLogradouro;
	private String nomeLogradouro;
	private String nomeBairro;
	private String nomeCidade;
	private String nomeEstado;
	private String nomeComplemento;

	public String getTpLogradouro() {
		return tpLogradouro;
	}

	public void setTpLogradouro(String tpLogradouro) {
		this.tpLogradouro = tpLogradouro;
	}

	public String getNomeLogradouro() {
		return nomeLogradouro;
	}

	public void setNomeLogradouro(String nomeLogradouro) {
		this.nomeLogradouro = nomeLogradouro;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
	}

	public String getNomeEstado() {
		return nomeEstado;
	}

	public void setNomeEstado(String nomeEstado) {
		this.nomeEstado = nomeEstado;
	}
	
	public String getNomeComplemento() {
		return nomeComplemento;
	}

	public void setNomeComplemento(String nomeComplemento) {
		this.nomeComplemento = nomeComplemento;
	}


	public void toBean(HashMap map) {
		this.setTpLogradouro((String) map.get("TipLogr"));
		this.setNomeLogradouro((String) map.get("NomLogr"));
		this.setNomeBairro((String) map.get("NomBairro"));
		this.setNomeCidade((String) map.get("NomCid"));
		this.setNomeEstado((String) map.get("SigUF"));
		this.setNomeComplemento((String) map.get("NomCompl"));
	}

}

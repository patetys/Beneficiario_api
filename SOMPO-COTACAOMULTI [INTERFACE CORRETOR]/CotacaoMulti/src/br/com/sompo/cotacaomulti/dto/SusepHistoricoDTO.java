package br.com.sompo.cotacaomulti.dto;

import java.io.Serializable;

public class SusepHistoricoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long codigoParceiro;
	private String numeroSusep;
	private String dataAtualizacao;

	public Long getCodigoParceiro() {
		return codigoParceiro;
	}

	public void setCodigoParceiro(Long codigoParceiro) {
		this.codigoParceiro = codigoParceiro;
	}

	public String getNumeroSusep() {
		return numeroSusep;
	}

	public void setNumeroSusep(String numeroSusep) {
		this.numeroSusep = numeroSusep;
	}

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	@Override
	public String toString() {
		return "SusepHistoricoDTO [codigoParceiro=" + codigoParceiro + ", numeroSusep=" + numeroSusep
				+ ", dataAtualizacao=" + dataAtualizacao + "]";
	}
}
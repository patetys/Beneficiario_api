package br.com.sompo.cotacaomulti.dto;

import java.io.Serializable;

public class HistoricoInternoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String ccotacao;
	private String sequencia;
	private String nom_membro;
	private String observacao;
	private String dat_geracao;
	
	public HistoricoInternoDTO() {
			
	}
	
	public HistoricoInternoDTO(String ccotacao, String sequencia, String nom_membro, String observacao,
			String dat_geracao) {
		super();
		this.ccotacao = ccotacao;
		this.sequencia = sequencia;
		this.nom_membro = nom_membro;
		this.observacao = observacao;
		this.dat_geracao = dat_geracao;
	}

	public String getCcotacao() {
		return ccotacao;
	}

	public void setCcotacao(String ccotacao) {
		this.ccotacao = ccotacao;
	}

	public String getSequencia() {
		return sequencia;
	}

	public void setSequencia(String sequencia) {
		this.sequencia = sequencia;
	}

	public String getNom_membro() {
		return nom_membro;
	}

	public void setNom_membro(String nom_membro) {
		this.nom_membro = nom_membro;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getDat_geracao() {
		return dat_geracao;
	}

	public void setDat_geracao(String dat_geracao) {
		this.dat_geracao = dat_geracao;
	}
	
}

package br.com.sompo.cotacaomulti.dto;

import java.io.Serializable;

public class RamoExcecaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer codigoRamo;
	private String descricaoRamo;

	public Integer getCodigoRamo() {
		return codigoRamo;
	}

	public void setCodigoRamo(Integer codigoRamo) {
		this.codigoRamo = codigoRamo;
	}

	public String getDescricaoRamo() {
		return descricaoRamo;
	}

	public void setDescricaoRamo(String descricaoRamo) {
		this.descricaoRamo = descricaoRamo;
	}

	public RamoExcecaoDTO toDTO(Integer ramo, String descricao) {
		this.codigoRamo = ramo;
		this.descricaoRamo = descricao;
		return this;
	}
}
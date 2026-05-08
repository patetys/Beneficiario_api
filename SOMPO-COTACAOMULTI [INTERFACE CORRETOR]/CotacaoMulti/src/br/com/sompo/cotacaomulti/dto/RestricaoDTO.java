package br.com.sompo.cotacaomulti.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class RestricaoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String dscAcao;
	private Integer idAcao;
	private Integer codigoRamo;
	public Integer getIdAcao() {
		return idAcao;
	}
	public void setIdAcao(Integer idAcao) {
		this.idAcao = idAcao;
	}
	public Integer getCodigoRamo() {
		return codigoRamo;
	}
	public void setCodigoRamo(Integer codigoRamo) {
		this.codigoRamo = codigoRamo;
	}
	public String getDscAcao() {
		return dscAcao;
	}
	public void setDscAcao(String dscAcao) {
		this.dscAcao = dscAcao;
	}

}
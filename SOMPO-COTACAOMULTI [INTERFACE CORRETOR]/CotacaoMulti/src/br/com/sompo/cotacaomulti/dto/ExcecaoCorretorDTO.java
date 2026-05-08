package br.com.sompo.cotacaomulti.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

public class ExcecaoCorretorDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Long codigoCorretor;
	private Integer codigoRamo;
	private Boolean ativo;
	private String dataAtualizacao;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getCodigoCorretor() {
		return codigoCorretor;
	}

	public void setCodigoCorretor(Long codigoCorretor) {
		this.codigoCorretor = codigoCorretor;
	}

	public Integer getCodigoRamo() {
		return codigoRamo;
	}

	public void setCodigoRamo(Integer codigoRamo) {
		this.codigoRamo = codigoRamo;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(String dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public ExcecaoCorretorDTO toDTO(Integer id, Long codCorretor, Integer ramo, Integer ativo, Timestamp dataAtualizacao) {
		this.id = id;
		this.codigoCorretor = codCorretor;
		this.ativo = ativo == 0;
		this.codigoRamo = ramo;
		this.setDataAtualizacao(
				dataAtualizacao.toLocalDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
		return this;
	}
}
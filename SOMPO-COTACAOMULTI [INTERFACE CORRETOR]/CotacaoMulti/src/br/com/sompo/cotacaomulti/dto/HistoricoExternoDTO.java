package br.com.sompo.cotacaomulti.dto;

public class HistoricoExternoDTO {
	private String sequencia;
	private String nom_membro;
	private String end_email;
	private String corpo_email;
	private String resp_email;
	private String dat_resp;
	private String dat_geracao;
	
	public HistoricoExternoDTO() {
	}

	public HistoricoExternoDTO(String sequencia, String nom_membro, String end_email, String corpo_email,
			String resp_email, String dat_resp, String dat_geracao) {
		super();
		this.sequencia = sequencia;
		this.nom_membro = nom_membro;
		this.end_email = end_email;
		this.corpo_email = corpo_email;
		this.resp_email = resp_email;
		this.dat_resp = dat_resp;
		this.dat_geracao = dat_geracao;
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

	public String getEnd_email() {
		return end_email;
	}

	public void setEnd_email(String end_email) {
		this.end_email = end_email;
	}

	public String getCorpo_email() {
		return corpo_email;
	}

	public void setCorpo_email(String corpo_email) {
		this.corpo_email = corpo_email;
	}

	public String getResp_email() {
		return resp_email;
	}

	public void setResp_email(String resp_email) {
		this.resp_email = resp_email;
	}

	public String getDat_resp() {
		return dat_resp;
	}

	public void setDat_resp(String dat_resp) {
		this.dat_resp = dat_resp;
	}

	public String getDat_geracao() {
		return dat_geracao;
	}

	public void setDat_geracao(String dat_geracao) {
		this.dat_geracao = dat_geracao;
	}
	
}

package util;

public enum AplicativoPermissao {

	CORRETOR_EMPRESARIAL(1, "SiscMultiEmpres",
			"Regra: excessao de corretores para cotacoes tipo empresarial.");

	private int sequencia;
	private String aplicativo;
	private String descricao;

	private AplicativoPermissao(int sequencia, String aplicativo,
			String descricao) {
		this.sequencia = sequencia;
		this.aplicativo = aplicativo;
		this.descricao = descricao;
	}

	public int getSequencia() {
		return sequencia;
	}

	public String getAplicativo() {
		return aplicativo;
	}

	public String getDescricao() {
		return descricao;
	}
}

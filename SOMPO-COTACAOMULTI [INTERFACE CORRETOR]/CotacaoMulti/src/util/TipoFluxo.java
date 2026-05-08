package util;

public enum TipoFluxo {
	VALOR("3");

	private String valor;

	private TipoFluxo(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}	
}
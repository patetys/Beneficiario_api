package util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum TipoEmissao {
	VAZIO(" "), 
	SEGURO_NOVO("Seguro Novo"), 
	RENOV_CONGENERE("Renov Congênere"), 
	RENOV_SOMPO("Renov Sompo"),
	ENDOSSO("Endosso");

	private String tipoEmissao;

	private TipoEmissao(String tipoEmissao) {
		this.tipoEmissao = tipoEmissao;
	}

	public String getTipoEmissao() {
		return tipoEmissao;
	}
	
	public static List<String> getTiposEmissao() {
		return Arrays.asList(TipoEmissao.values()).stream().map(TipoEmissao::getTipoEmissao)
				.collect(Collectors.toList());
	}
}
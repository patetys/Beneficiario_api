/*
 * Criado em 06/09/2010
 *
 * Para alterar o gabarito para este arquivo gerado vá para
 * Janela&gt;Preferências&gt;Java&gt;Geração de Códigos&gt;Código e Comentários
 */
package bean;

import java.io.Serializable;

/**
 * @author dcmaia
 *
 *         Para alterar o gabarito para este comentário do tipo gerado vá para
 *         Janela&gt;Preferências&gt;Java&gt;Geração de Códigos&gt;Código e
 *         Comentários
 */
public class Corretor implements Serializable, Comparable<Corretor> {

	private static final long serialVersionUID = 1278346497479191676L;
	// só é string por causa de definição antiga e formatação
	private String codCorretor;
	private String corretor;
	private String cidCorretor;

	public String getCodCorretor() {
		return codCorretor;
	}

	public void setCodCorretor(String codCorretor) {
		this.codCorretor = codCorretor;
	}

	public String getCorretor() {
		return corretor;
	}

	public void setCorretor(String corretor) {
		this.corretor = corretor;
	}

	public String getCidCorretor() {
		return cidCorretor;
	}

	public void setCidCorretor(String cidCorretor) {
		this.cidCorretor = cidCorretor;
	}

	@Override
	public int compareTo(Corretor o) {
		if (this.corretor.compareToIgnoreCase(o.getCorretor()) < 0) {
			return -1;
		}
		if (this.corretor.compareToIgnoreCase(o.getCorretor()) > 0) {
			return 1;
		}
		return 0;
	}
	
	public String removeZerosCorretor(String codCorretor){
		return Integer.toString(Integer.parseInt(codCorretor));
	}

}

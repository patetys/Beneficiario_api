/*
 * Criado em 18/10/2006
 *
 */
package bean;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import db.DbAccess;
import util.Util;

/**
 * @author erialdo
 *
 *         Riscos Diversos - Anuncios luminosos
 * 
 */
public class Cto7108Bean extends CotacaoBean {

	private static final long serialVersionUID = -6551194465317981547L;

	private String modalidade; // 0 - Anuncio / 1 - Antenas
	private String altura; // Altura
	private String areaM2; // Area M2
	private String luminoso; // S - SIM / N - NAO
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div7108Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setModalidade((String) d.get("dados01"));
		setAltura((String) d.get("dados02"));
		setAreaM2((String) d.get("dados03"));
		setLuminoso((String) d.get("dados04"));
		setNumSin5anos((String) d.get("dados5"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getModalidade());
		list.put("dados02", getAltura());
		list.put("dados03", getAreaM2());
		list.put("dados04", getLuminoso());
		list.put("dados05", getNumSin5anos());
		list.put("dados06", " ");
		list.put("dados07", " ");
		list.put("dados08", " ");
		list.put("dados09", " ");
		list.put("dados10", " ");
		list.put("dados11", " ");
		list.put("dados12", " ");
		list.put("dados13", " ");
		list.put("dados14", " ");
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;

	}

	public void setDadosHtml() {

		dados_html = new String[10]; // sempre mais 1

		dados_html[0] = "Modalidade";
		if (getModalidade().equals("0")) {
			dados_html[1] = "ANUNCIO";
		} else {
			dados_html[1] = "ANTENAS";
		}

		dados_html[2] = "Altura";
		dados_html[3] = getAltura();

		dados_html[4] = "Área m2";
		dados_html[5] = getAreaM2();

		dados_html[6] = "Luminoso";
		if (getLuminoso().equals("S")) {
			dados_html[7] = "SIM";
		} else {
			dados_html[7] = "NÃO";
		}

		dados_html[8] = "Sinistros dos últimos 5 Anos";
		dados_html[9] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("Modalidade");
		if (tmp == null)
			tmp = "0";
		setModalidade(tmp);

		tmp = req.getParameter("Altura");
		if (tmp == null)
			tmp = "0";
		setAltura(tmp);

		tmp = req.getParameter("AreaM2");
		if (tmp == null)
			tmp = "0";
		setAreaM2(tmp);

		tmp = req.getParameter("Luminoso");
		if (tmp == null)
			tmp = "N";
		setLuminoso(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

//set	
	public void setModalidade(String i) {
		this.modalidade = i;
	}

	public void setAltura(String i) {
		if (i.equals("")) {
			i = "0";
		}
		this.altura = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setAreaM2(String i) {
		if (i.equals("")) {
			i = "0";
		}
		this.areaM2 = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setLuminoso(String i) {
		this.luminoso = i;
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

	public String getModalidade() {
		return modalidade;
	}

	public String getAltura() {
		return altura;
	}

	public String getAreaM2() {
		return areaM2;
	}

	public String getLuminoso() {
		return luminoso;
	}

	public String getNumSin5anos() {
		return numSin5anos;
	}

	public String[] getDadosHtml() {
		return dados_html;
	}

	public String getPaginaJSP() {
		return DIVCADJSP;
	}

}

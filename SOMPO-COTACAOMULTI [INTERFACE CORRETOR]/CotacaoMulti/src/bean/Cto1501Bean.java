/*
 * Criado em 18/10/2006
 *
 */
package bean;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import db.DbAccess;

/**
 * @author erialdo
 *
 *         Roubo - All Risk's
 */
public class Cto1501Bean extends CotacaoBean {

	private static final long serialVersionUID = -727224879220959689L;

	private String dscObjeto;
	private String codCobertura; // Informar - 1 - Territorio Nacional / 2 - Todo o mundo
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div1501Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setNumSin5anos((String) d.get("dados01"));
		setcodCobertura((String) d.get("dados02"));
		setDscObjeto((String) d.get("dados14"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getNumSin5anos());
		list.put("dados02", getcodCobertura());
		list.put("dados03", " ");
		list.put("dados04", " ");
		list.put("dados05", " ");
		list.put("dados06", " ");
		list.put("dados07", " ");
		list.put("dados08", " ");
		list.put("dados09", " ");
		list.put("dados10", " ");
		list.put("dados11", " ");
		list.put("dados12", " ");
		list.put("dados13", " ");
		list.put("dados14", getDscObjeto());
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;

	}

	public void setDadosHtml() {

		dados_html = new String[6]; // sempre mais um

		dados_html[0] = "Descrição do Objeto";
		dados_html[1] = getDscObjeto();

		dados_html[2] = "Cobertura";
		if (getcodCobertura().equals("1")) {
			dados_html[3] = "TERRITÓRIO NACIONAL";
		} else {
			dados_html[3] = "TODO O MUNDO";
		}

		dados_html[4] = "Sinistros dos últimos 5 Anos";
		dados_html[5] = getNumSin5anos();

	}

	public String[] getDadosHtml() {
		return dados_html;
	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("DscObjeto");
		if (tmp == null)
			tmp = " ";
		setDscObjeto(tmp);

		tmp = req.getParameter("CodCobertura");
		if (tmp == null)
			tmp = "1";
		setcodCobertura(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	public void setDscObjeto(String i) {
		dscObjeto = i;
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

	public void setcodCobertura(String i) {
		codCobertura = i;
	}

	public String getDscObjeto() {
		return dscObjeto;
	}

	public String getNumSin5anos() {
		return numSin5anos;
	}

	public String getcodCobertura() {
		return codCobertura;
	}

	public String getPaginaJSP() {
		return DIVCADJSP;
	}

}

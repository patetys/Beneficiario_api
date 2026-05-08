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
 */
public class Cto5104Bean extends CotacaoBean {

	private static final long serialVersionUID = 2194523325669440273L;

	private String nrSocios; // Nr. de Socios
	private String restSimilares; // Restaurante ou similares
	private String instacEsport; // Instalacoes esportivas
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5104Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setNrSocios((String) d.get("dados01"));
		setRestSimilares((String) d.get("dados02"));
		setInstacEsport((String) d.get("dados03"));
		setNumSin5anos((String) d.get("dados04"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getNrSocios());
		list.put("dados02", getRestSimilares());
		list.put("dados03", getInstacEsport());
		list.put("dados04", getNumSin5anos());
		list.put("dados05", " ");
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

		dados_html = new String[8]; // sempre mais um

		dados_html[0] = "Nr. de Sócios";
		dados_html[1] = getNrSocios();

		dados_html[2] = "Restaurante ou similares";
		if (getRestSimilares().equals("1")) {
			dados_html[3] = "SIM";
		} else {
			dados_html[3] = "NÃO";
		}

		dados_html[4] = "Instalações esportivas";
		if (getInstacEsport().equals("1")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Sinistros dos últimos 5 Anos";
		dados_html[7] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("NrSocios");
		if (tmp == null)
			tmp = "0";
		setNrSocios(tmp);

		tmp = req.getParameter("RestSimilares");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setRestSimilares(tmp);

		tmp = req.getParameter("InstacEsport");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setInstacEsport(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

//set
	public void setNrSocios(String i) {
		this.nrSocios = i;
	}

	public void setRestSimilares(String i) {
		this.restSimilares = i;
	}

	public void setInstacEsport(String i) {
		this.instacEsport = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get		
	public String getNrSocios() {
		return nrSocios;
	}

	public String getRestSimilares() {
		return restSimilares;
	}

	public String getInstacEsport() {
		return instacEsport;
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

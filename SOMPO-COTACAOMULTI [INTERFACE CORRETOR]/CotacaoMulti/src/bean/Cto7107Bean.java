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
 *         Riscos Diversos - Valores
 * 
 */
public class Cto7107Bean extends CotacaoBean {

	private static final long serialVersionUID = 3741472241667092821L;

	private String dscOcup; // ocupacao /ativadade
	private String valores; // Valores / 1 - em transito / 2 interior do estabelecimento
	private String pagtoSalar; // Extensao da cobertura a pagamentos de salarios / S - sim / N - nao
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div7107Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setDscOcup((String) d.get("dados14"));
		setValores((String) d.get("dados01"));
		setPagtoSalar((String) d.get("dados02"));
		setNumSin5anos((String) d.get("dados03"));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getValores());
		list.put("dados02", getPagtoSalar());
		list.put("dados03", getNumSin5anos());
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
		list.put("dados14", getDscOcup());
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;
	}

	public void setDadosHtml() {

		dados_html = new String[8]; // sempre mais 1

		dados_html[0] = "Ocupação Atividade ";
		dados_html[1] = getDscOcup();

		dados_html[2] = "Valores";
		switch (Integer.parseInt(getValores().trim())) {
		case 1:
			dados_html[3] = "EM TRANSITO";
			break;
		case 2:
			dados_html[3] = "INTERIOR DO ESTABELECIMENTO";
			break;
		default:
			dados_html[3] = "";
			break;
		}

		dados_html[4] = "Pagto de Salários";
		if (getPagtoSalar().equals("S")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Sinistros dos últimos 5 Anos";
		dados_html[7] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("DscOcup");
		if (tmp == null)
			tmp = "";
		setDscOcup(tmp);

		tmp = req.getParameter("Valores");
		if (tmp == null)
			tmp = "1";
		setValores(tmp);

		tmp = req.getParameter("PagtoSalar");
		if (tmp == null)
			tmp = "N";
		setPagtoSalar(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);
	}

//set	
	public void setDscOcup(String i) {
		this.dscOcup = i;
	}

	public void setValores(String i) {
		this.valores = i;
	}

	public void setPagtoSalar(String i) {
		this.pagtoSalar = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get
	public String getDscOcup() {
		return dscOcup;
	}

	public String getValores() {
		return valores;
	}

	public String getPagtoSalar() {
		return pagtoSalar;
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

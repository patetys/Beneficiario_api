/*
 * Criado em 09/10/2006
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
public class Cto113Bean extends CotacaoBean {

	private static final long serialVersionUID = 5117505697798057996L;

	private String constAlv; // Contrucao Alvenaria? S/N
	private String tpMoradia; // 1- casa / 2 - Apartamento
	private String tpUtilizacao; // 1 Habitual / 2 - Eventual / 3 - Desocupada
	private String numSin5anos; // informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div113Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setConstAlv((String) d.get("dados01"));
		setTpMoradia((String) d.get("dados02"));
		setTpUtilizacao((String) d.get("dados03"));
		setNumSin5anos((String) d.get("dados04"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getConstAlv());
		list.put("dados02", getTpMoradia());
		list.put("dados03", getTpUtilizacao());
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

		dados_html[0] = "Construção Avenaria";
		if (getConstAlv().equals("S")) {
			dados_html[1] = "SIM";
		} else if (getConstAlv().equals("N")) {
			dados_html[1] = "NÃO";
		} else {
			dados_html[1] = "";
		}

		dados_html[2] = "Tipo de Moradia";
		if (getTpMoradia().equals("1")) {
			dados_html[3] = "CASA";
		} else if (getTpMoradia().equals("2")) {
			dados_html[3] = "APARTAMENTO";
		} else {
			dados_html[3] = "";
		}

		dados_html[4] = "Modalidade da Moradia";
		if (getTpUtilizacao().equals("1")) {
			dados_html[5] = "HABITUAL";
		} else if (getTpUtilizacao().equals("2")) {
			dados_html[5] = "EVENTUAL";
		} else if (getTpUtilizacao().equals("3")) {
			dados_html[5] = "DESOCUPADA";
		} else {
			dados_html[5] = "";
		}

		dados_html[6] = "Sinistros dos últimos 5 Anos";
		dados_html[7] = getNumSin5anos();

	}

	/**
	 * Grava cotacao 113
	 * 
	 */
	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("ConstAlv");
		if (tmp == null)
			tmp = "S";
		setConstAlv(tmp);

		tmp = req.getParameter("TpMoradia");
		if (tmp == null)
			tmp = "1";
		setTpMoradia(tmp);

		tmp = req.getParameter("TpUtilizacao");
		if (tmp == null)
			tmp = "1";
		setTpUtilizacao(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

//set
	public void setConstAlv(String i) {
		constAlv = i;
	}

	public void setTpMoradia(String i) {
		tpMoradia = i;
	}

	public void setTpUtilizacao(String i) {
		tpUtilizacao = i;
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

//get
	public String getConstAlv() {
		return constAlv;
	}

	public String getTpMoradia() {
		return tpMoradia;
	}

	public String getTpUtilizacao() {
		return tpUtilizacao;
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

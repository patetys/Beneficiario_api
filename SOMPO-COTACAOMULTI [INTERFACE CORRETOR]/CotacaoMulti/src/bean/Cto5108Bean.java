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
 *         Responsabilidade Civil - Obras Civis
 *
 */
public class Cto5108Bean extends CotacaoBean {

	private static final long serialVersionUID = 3545832737369357885L;

	private String tpObra; // 1 - construcao / 2 - demolicao
	private String vltMaoObr; // valor da mao de obra
	private String maiorPav; // Base maior pavimento
	private String areaLinFach; // Area linear da fachada
	private String qtdPav; // quantidde pavimento
	private String afastRelTerc; // Afastamento em relacao a terceiros
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5108Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setTpObra((String) d.get("dados01"));
		setVltMaoObr((String) d.get("dados02"));
		setMaiorPav((String) d.get("dados03"));
		setAreaLinFach((String) d.get("dados04"));
		setQtdPav((String) d.get("dados05"));
		setAfastRelTerc((String) d.get("dados06"));
		setNumSin5anos((String) d.get("dados07"));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getTpObra());
		list.put("dados02", getVltMaoObr());
		list.put("dados03", getMaiorPav());
		list.put("dados04", getAreaLinFach());
		list.put("dados05", getQtdPav());
		list.put("dados06", getAfastRelTerc());
		list.put("dados07", getNumSin5anos());
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

		dados_html = new String[14]; // sempre mais um

		dados_html[0] = "Tipo de Obra";
		if (getTpObra().equals("1")) {
			dados_html[1] = "CONSTRUÇÃO/DEMOLIÇÃO";

			dados_html[2] = "Base Maior Pavimento";
			dados_html[3] = getMaiorPav();

			dados_html[4] = "Área Linear da Fachada";
			dados_html[5] = getAreaLinFach();

			dados_html[6] = "Quantidade Pavimento";
			dados_html[7] = getQtdPav();

			dados_html[8] = "Afastamento em Relação à Terceiros";
			dados_html[9] = getAfastRelTerc();

			dados_html[10] = "Sinistros dos últimos 5 Anos";
			dados_html[11] = getNumSin5anos();

		} else {
			dados_html[1] = "OUTROS";

			dados_html[2] = "Valor da Mão de Obra";
			dados_html[3] = getVltMaoObr();

			dados_html[4] = "Sinistros dos últimos 5 Anos";
			dados_html[5] = getNumSin5anos();

		}

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("TpObra");
		if (tmp == null)
			tmp = "2";
		setTpObra(tmp);

		tmp = req.getParameter("VltMaoObr");
		if (tmp == null)
			tmp = "0";
		setVltMaoObr(tmp);

		tmp = req.getParameter("MaiorPav");
		if (tmp == null)
			tmp = "0";
		setMaiorPav(tmp);

		tmp = req.getParameter("AreaLinFach");
		if (tmp == null)
			tmp = "0";
		setAreaLinFach(tmp);

		tmp = req.getParameter("QtdPav");
		if (tmp == null)
			tmp = "0";
		setQtdPav(tmp);

		tmp = req.getParameter("AfastRelTerc");
		if (tmp == null)
			tmp = "0";
		setAfastRelTerc(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);
	}

//set

	public void setTpObra(String i) {
		this.tpObra = i;
	}

	public void setVltMaoObr(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		this.vltMaoObr = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setMaiorPav(String i) {
		this.maiorPav = i;
	}

	public void setAreaLinFach(String i) {
		this.areaLinFach = i;
	}

	public void setQtdPav(String i) {
		this.qtdPav = i;
	}

	public void setAfastRelTerc(String i) {
		this.afastRelTerc = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get

	public String getTpObra() {
		return tpObra;
	}

	public String getVltMaoObr() {
		return vltMaoObr;
	}

	public String getMaiorPav() {
		return maiorPav;
	}

	public String getAreaLinFach() {
		return areaLinFach;
	}

	public String getQtdPav() {
		return qtdPav;
	}

	public String getAfastRelTerc() {
		return afastRelTerc;
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

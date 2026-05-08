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
 */
public class Cto5102Bean extends CotacaoBean {

	private static final long serialVersionUID = 1463107558336076518L;

	private String fatUltim12; // Faturamento nos ultimos 12 meses.
	private String rcEmpregador; // S - SIM / N - NAO
	private String rcRiscoVeic; // S - SIM / N - NAO
	private String rcProdutos; // S - SIM / N - NAO
	private String dscOcupacao; // Ocupacao ou atividade
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5102Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setFatUltim12((String) d.get("dados01"));
		setRcEmpregador((String) d.get("dados02"));
		setRcRiscoVeic((String) d.get("dados03"));
		setRcProdutos((String) d.get("dados04"));
		setDscOcupacao((String) d.get("dados05"));
		setNumSin5anos((String) d.get("dados06"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getFatUltim12());
		list.put("dados02", getRcEmpregador());
		list.put("dados03", getRcRiscoVeic());
		list.put("dados04", getRcProdutos());
		list.put("dados05", getDscOcupacao());
		list.put("dados06", getNumSin5anos());
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

		dados_html = new String[12]; // sempre mais 1

		dados_html[0] = "Faturamento nos ultimos 12 meses: R$";
		dados_html[1] = getFatUltim12();

		dados_html[2] = "Ocupação ou atividade";
		dados_html[3] = getDscOcupacao();

		dados_html[4] = "RC Empregador?";
		if (getRcEmpregador().equals("S")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}
		dados_html[6] = "RC Riscos Contigentes de Veículos?";
		if (getRcRiscoVeic().equals("S")) {
			dados_html[7] = "SIM";
		} else {
			dados_html[7] = "NÃO";
		}

		dados_html[8] = "RC Produtos";
		if (getRcProdutos().equals("S")) {
			dados_html[9] = "SIM";
		} else {
			dados_html[9] = "NÃO";
		}

		dados_html[10] = "Sinistros dos últimos 5 Anos";
		dados_html[11] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		setFatUltim12(req.getParameter("FatUltim12"));

		tmp = req.getParameter("RcEmpregador");
		if (tmp == null)
			tmp = "N";
		setRcEmpregador(tmp);

		tmp = req.getParameter("RcRiscoVeic");
		if (tmp == null)
			tmp = "N";
		setRcRiscoVeic(tmp);

		tmp = req.getParameter("RcProdutos");
		if (tmp == null)
			tmp = "N";
		setRcProdutos(tmp);

		setDscOcupacao(req.getParameter("DscOcupacao"));

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	public void setFatUltim12(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		this.fatUltim12 = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setRcEmpregador(String i) {
		this.rcEmpregador = i;
	}

	public void setRcRiscoVeic(String i) {
		this.rcRiscoVeic = i;
	}

	public void setRcProdutos(String i) {
		this.rcProdutos = i;
	}

	public void setDscOcupacao(String i) {
		this.dscOcupacao = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

	public String getFatUltim12() {
		return fatUltim12;
	}

	public String getRcEmpregador() {
		return rcEmpregador;
	}

	public String getRcRiscoVeic() {
		return rcRiscoVeic;
	}

	public String getRcProdutos() {
		return rcProdutos;
	}

	public String getDscOcupacao() {
		return dscOcupacao;
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

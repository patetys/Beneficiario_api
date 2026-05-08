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
public class Cto5103Bean extends CotacaoBean {

	private static final long serialVersionUID = 6855105686592746689L;

	private String nrFuncTerc; // nr. de funcionarios terceiros
	private String tpServico; // 1 - Manutencao / 2 - Limpeza
	private String rcEmpregados; // S - SIM / N - NAO
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5103Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setNrFuncTerc((String) d.get("dados01"));
		setTpServico((String) d.get("dados02"));
		setNumSin5anos((String) d.get("dados03"));
		setRcEmpregados((String) d.get("dados04"));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getNrFuncTerc());
		list.put("dados02", getTpServico());
		list.put("dados03", getNumSin5anos());
		list.put("dados04", getRcEmpregados());
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

		dados_html = new String[8]; // sempre mais 1

		dados_html[0] = " Nº de func. que prestam serviço em locais de terceiros";
		dados_html[1] = getNrFuncTerc();

		dados_html[2] = "Tipo de Serviço";
		if (getTpServico().equals("1")) {
			dados_html[3] = "MANUTENÇÃO";
		} else {
			dados_html[3] = "LIMPEZA";
		}

		dados_html[4] = "RC Empregados ?";
		if (getRcEmpregados().equals("S")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Sinistros dos últimos 5 Anos";
		dados_html[7] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("NrFuncTerc");
		if (tmp == null)
			tmp = "0";
		setNrFuncTerc(tmp);

		tmp = req.getParameter("TpServico");
		if (tmp == null)
			tmp = "1";
		setTpServico(tmp);

		tmp = req.getParameter("RcEmpregados");
		if (tmp == null)
			tmp = "N";
		setRcEmpregados(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	public void setNrFuncTerc(String i) {
		this.nrFuncTerc = i;
	}

	public void setTpServico(String i) {
		this.tpServico = i;
	}

	public void setRcEmpregados(String i) {
		this.rcEmpregados = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

	public String getNrFuncTerc() {
		return nrFuncTerc;
	}

	public String getTpServico() {
		return tpServico;
	}

	public String getRcEmpregados() {
		return rcEmpregados;
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

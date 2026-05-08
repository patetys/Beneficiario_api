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
public class Cto112Bean extends CotacaoBean {

	private static final long serialVersionUID = 4829928847505720185L;

	private String tipo; // 1 - Indutria / 2 - Comercio
	private String dscAtividade; // descricao da Atividade
	private String procQuente; // Processo quente S/N
	private String MatInflam; // Material Inflamavel S/N
	private String bensCobertos; // 1 - predio e conteudo / 2 - Exclusivamente predio / 3 - Exclusivamente
									// conteudo
	private String constAlvenaria; // S - SIM / N - NAO
	private String sistPrtcao01; // Sistema de protecao /1 - extintor
	private String sistPrtcao02; // Sistema de protecao /1 - hidrantes
	private String sistPrtcao03; // Sistema de protecao /1 - Sprinkler
	private String sistPrtcao04; // Sistema de protecao /1 - Vigia 24hs
	private String sistPrtcao05; // Sistema de protecao /1 - Alarme
	private String numSin5anos; // informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div112Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setTipo((String) d.get("dados01"));
		setDscAtividade((String) d.get("dados02"));
		setProcQuente((String) d.get("dados03"));
		setMatInflam((String) d.get("dados04"));
		if (d.get("dados05").equals("")) {
			setBensCobertos("0");
		} else
			setBensCobertos((String) d.get("dados05"));
		setConstAlvenaria((String) d.get("dados06"));
		setSistPrtcao01((String) d.get("dados07"));
		setSistPrtcao02((String) d.get("dados08"));
		setSistPrtcao03((String) d.get("dados09"));
		setSistPrtcao04((String) d.get("dados10"));
		setSistPrtcao05((String) d.get("dados11"));
		setNumSin5anos((String) d.get("dados12"));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getTipo());
		list.put("dados02", getDscAtividade());
		list.put("dados03", getProcQuente());
		list.put("dados04", getMatInflam());
		list.put("dados05", getBensCobertos());
		list.put("dados06", getConstAlvenaria());
		list.put("dados07", getSistPrtcao01());
		list.put("dados08", getSistPrtcao02());
		list.put("dados09", getSistPrtcao03());
		list.put("dados10", getSistPrtcao04());
		list.put("dados11", getSistPrtcao05());
		list.put("dados12", getNumSin5anos());
		list.put("dados13", " ");
		list.put("dados14", " ");
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;
	}

	public void setDadosHtml() {

		dados_html = new String[24]; // sempre mais um

		dados_html[0] = "Tipo";
		if (getTipo().equals("1")) {
			dados_html[1] = "INDÚSTRIA";
		} else {
			dados_html[1] = "COMÉRCIO";
		}

		dados_html[2] = "Descrição da Atividade";
		dados_html[3] = getDscAtividade();

		dados_html[4] = "Processo Quente";
		if (getProcQuente().equals("S")) {
			dados_html[5] = "SIM";
		} else {
			if (getProcQuente().equals("N")) {
				dados_html[5] = "NãO";
			} else {
				dados_html[5] = "";
			}
		}

		dados_html[6] = "Material Inflamável";
		if (getMatInflam().equals("S")) {
			dados_html[7] = "SIM";
		} else {
			if (getMatInflam().equals("N")) {
				dados_html[7] = "NÃO";
			} else {
				dados_html[7] = "";
			}
		}

		dados_html[8] = "Construção de Alvenaria";
		if (getConstAlvenaria().equals("S")) {
			dados_html[9] = "SIM";
		} else {
			if (getConstAlvenaria().equals("N")) {
				dados_html[9] = "NÃO";
			} else {
				dados_html[9] = "";
			}
		}

		dados_html[10] = "Bens Cobertos";

		switch (Integer.parseInt(getBensCobertos().trim())) {
		case 1:
			dados_html[11] = "PRÉDIO E CONTEÚDO";
			break;
		case 2:
			dados_html[11] = "EXCLUSIVAMENTE PRÉDIO";
			break;
		case 3:
			dados_html[11] = "EXCLUSIVAMENTE CONTEÚDO";
			break;
		default:
			dados_html[11] = "";
			break;
		}

		dados_html[12] = "Extintor";
		if (getSistPrtcao01().equals("1")) {
			dados_html[13] = "SIM";
		} else {
			dados_html[13] = "NÃO";
		}

		dados_html[14] = "hidrantes";
		if (getSistPrtcao02().equals("1")) {
			dados_html[15] = "SIM";
		} else {
			dados_html[15] = "NÃO";
		}

		dados_html[16] = "Sprinkler";
		if (getSistPrtcao03().equals("1")) {
			dados_html[17] = "SIM";
		} else {
			dados_html[17] = "NÃO";
		}

		dados_html[18] = "Vigia 24 hs";
		if (getSistPrtcao04().equals("1")) {
			dados_html[19] = "SIM";
		} else {
			dados_html[19] = "NÃO";
		}

		dados_html[20] = "Alarme";
		if (getSistPrtcao05().equals("1")) {
			dados_html[21] = "SIM";
		} else {
			dados_html[21] = "NÃO";
		}

		dados_html[22] = "Sinistros dos últimos 5 Anos";
		dados_html[23] = getNumSin5anos();

	}

	/**
	 * metodo Grava cotacao 112
	 */
	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("Tipo");
		if (tmp == null)
			tmp = "2";
		setTipo(tmp);

		tmp = req.getParameter("DscAtividade");
		if (tmp == null)
			tmp = "";
		setDscAtividade(tmp);

		tmp = req.getParameter("ProcQuente");
		if (tmp == null)
			tmp = "N";
		setProcQuente(tmp);

		tmp = req.getParameter("MatInflam");
		if (tmp == null)
			tmp = "N";
		setMatInflam(tmp);

		tmp = req.getParameter("BensCobertos");
		if (tmp == null)
			tmp = "1";
		setBensCobertos(tmp);

		tmp = req.getParameter("ConstAlvenaria");
		if (tmp == null)
			tmp = "N";
		setConstAlvenaria(tmp);

		tmp = req.getParameter("SistPrtcao01");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setSistPrtcao01(tmp);

		tmp = req.getParameter("SistPrtcao02");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setSistPrtcao02(tmp);

		tmp = req.getParameter("SistPrtcao03");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setSistPrtcao03(tmp);

		tmp = req.getParameter("SistPrtcao04");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setSistPrtcao04(tmp);

		tmp = req.getParameter("SistPrtcao05");
		if (tmp == null)
			tmp = "0";
		else
			tmp = "1";
		setSistPrtcao05(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	// set
	public void setTipo(String i) {
		tipo = i;
	}

	public void setDscAtividade(String i) {
		dscAtividade = i;
	}

	public void setProcQuente(String i) {
		procQuente = i;
	}

	public void setMatInflam(String i) {
		MatInflam = i;
	}

	public void setBensCobertos(String i) {
		bensCobertos = i;
	}

	public void setConstAlvenaria(String i) {
		constAlvenaria = i;
	}

	public void setSistPrtcao01(String i) {
		sistPrtcao01 = i;
	}

	public void setSistPrtcao02(String i) {
		sistPrtcao02 = i;
	}

	public void setSistPrtcao03(String i) {
		sistPrtcao03 = i;
	}

	public void setSistPrtcao04(String i) {
		sistPrtcao04 = i;
	}

	public void setSistPrtcao05(String i) {
		sistPrtcao05 = i;
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

	// get
	public String getTipo() {
		return tipo;
	}

	public String getDscAtividade() {
		return dscAtividade;
	}

	public String getProcQuente() {
		return procQuente;
	}

	public String getMatInflam() {
		return MatInflam;
	}

	public String getBensCobertos() {
		return bensCobertos;
	}

	public String getConstAlvenaria() {
		return constAlvenaria;
	}

	public String getSistPrtcao01() {
		return sistPrtcao01;
	}

	public String getSistPrtcao02() {
		return sistPrtcao02;
	}

	public String getSistPrtcao03() {
		return sistPrtcao03;
	}

	public String getSistPrtcao04() {
		return sistPrtcao04;
	}

	public String getSistPrtcao05() {
		return sistPrtcao05;
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

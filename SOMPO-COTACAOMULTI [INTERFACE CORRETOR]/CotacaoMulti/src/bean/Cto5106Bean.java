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
 *         Responsabildade Civil - Guarda de Veiculos
 * 
 */
public class Cto5106Bean extends CotacaoBean {

	private static final long serialVersionUID = -6338065467081630214L;

	private String oficEquip; // Oficina com equipamento - S - Sim / N - nao
	private String condominio; // S - Sim / N - nao
	private String mensalista; // S - Sim / N - nao
	private String rotativo; // S - Sim / N - nao
	private String elevador; // S - Sim / N - nao
	private String posto; // S - Sim / N - nao
	private String areaExclEstac; // Area exclusiva para estacionamento de veiculos
	private String tpCobertura; // 1 - global / 2 - Exclusivamente
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5106Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setNumSin5anos((String) d.get("dados01"));
		setoficEquip((String) d.get("dados02"));
		setCondominio((String) d.get("dados03"));
		setMensalista((String) d.get("dados04"));
		setRotativo((String) d.get("dados05"));
		setPosto((String) d.get("dados06"));
		setElevador((String) d.get("dados07"));
		setAreaExclEstac((String) d.get("dados08"));
		setTpCobertura((String) d.get("dados09"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getNumSin5anos());
		list.put("dados02", getOficEquip());
		list.put("dados03", getCondominio());
		list.put("dados04", getMensalista());
		list.put("dados05", getRotativo());
		list.put("dados06", getPosto());
		list.put("dados07", getElevador());
		list.put("dados08", getAreaExclEstac());
		list.put("dados09", getTpCobertura());
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

		dados_html = new String[18]; // sempre mais 1

		dados_html[0] = "Oficina com Equipamento";
		if (getOficEquip().equals("S")) {
			dados_html[1] = "SIM";
		} else {
			dados_html[1] = "NÃO";
		}

		dados_html[2] = "Condominio";
		if (getCondominio().equals("S")) {
			dados_html[3] = "SIM";
		} else {
			dados_html[3] = "NÃO";
		}

		dados_html[4] = "Mensalista";
		if (getMensalista().equals("S")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Rotativo";
		if (getRotativo().equals("S")) {
			dados_html[7] = "SIM";
		} else {
			dados_html[7] = "NÃO";
		}

		dados_html[8] = "Elevador";
		if (getElevador().equals("S")) {
			dados_html[9] = "SIM";
		} else {
			dados_html[9] = "NÃO";
		}

		dados_html[10] = "Posto";
		if (getPosto().equals("S")) {
			dados_html[11] = "SIM";
		} else {
			dados_html[11] = "NÃO";
		}

		dados_html[12] = "Área exclusiva para estacionamento de veículos (m<FONT style='vertical-align: text-top;font-size: 6pt;'>2</FONT>)";
		dados_html[13] = getAreaExclEstac();

		dados_html[14] = "Tipo de Cobertura";
		if (getTpCobertura().equals("1")) {
			dados_html[15] = "GLOBAL";
		} else {
			dados_html[15] = "EXCLUSIVAMENTE INCÊNDIO E ROUBO";
		}

		dados_html[16] = "Sinistros dos últimos 5 Anos";
		dados_html[17] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("OficEquip");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setoficEquip(tmp);

		tmp = req.getParameter("Condominio");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setCondominio(tmp);

		tmp = req.getParameter("Mensalista");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setMensalista(tmp);

		tmp = req.getParameter("Rotativo");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setRotativo(tmp);

		tmp = req.getParameter("Posto");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setPosto(tmp);

		tmp = req.getParameter("Elevador");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setElevador(tmp);

		tmp = req.getParameter("AreaExclEstac");
		if (tmp == null)
			tmp = "0";
		setAreaExclEstac(tmp);

		tmp = req.getParameter("TpCobertura");
		if (tmp == null)
			tmp = "1";
		setTpCobertura(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

//set    
	public void setoficEquip(String i) {
		this.oficEquip = i;
	}

	public void setCondominio(String i) {
		this.condominio = i;
	}

	public void setMensalista(String i) {
		this.mensalista = i;
	}

	public void setRotativo(String i) {
		this.rotativo = i;
	}

	public void setPosto(String i) {
		this.posto = i;
	}

	public void setElevador(String i) {
		this.elevador = i;
	}

	public void setAreaExclEstac(String i) {
		this.areaExclEstac = i;
	}

	public void setTpCobertura(String i) {
		this.tpCobertura = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}
//get	

	public String getOficEquip() {
		return oficEquip;
	}

	public String getCondominio() {
		return condominio;
	}

	public String getMensalista() {
		return mensalista;
	}

	public String getRotativo() {
		return rotativo;
	}

	public String getPosto() {
		return posto;
	}

	public String getElevador() {
		return elevador;
	}

	public String getAreaExclEstac() {
		return areaExclEstac;
	}

	public String getTpCobertura() {
		return tpCobertura;
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

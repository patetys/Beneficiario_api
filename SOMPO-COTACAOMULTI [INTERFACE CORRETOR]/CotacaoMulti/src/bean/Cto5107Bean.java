/*
 * Criado em 18/10/2006
 */
package bean;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import db.DbAccess;

/**
 * @author erialdo
 *
 *         Responsabilidade Civil - Estabelecimento de Ensino
 *
 *
 **/
public class Cto5107Bean extends CotacaoBean {

	private static final long serialVersionUID = -3510790799166236893L;

	private String nrAlunos; // Nr de alunos
	private String laboratorio; // S - Sim / N - Nao
	private String piscina; // S - Sim / N - Nao
	private String intenExtern; // Internato e/ou externato - S - Sim / N - Nao
	private String elevador; // S - Sim / N - Nao
	private String ativForaEstab; // realiza atividade fora do estabelecimento S - Sim / N - Nao
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5107Cad.jsp"; // Pagina jsp
	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setNrAlunos((String) d.get("dados01"));
		setLaboratorio((String) d.get("dados02"));
		setPiscina((String) d.get("dados03"));
		setIntenExtern((String) d.get("dados04"));
		setElevador((String) d.get("dados05"));
		setAtivForaEstab((String) d.get("dados06"));
		setNumSin5anos((String) d.get("dados07"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getNrAlunos());
		list.put("dados02", getLaboratorio());
		list.put("dados03", getPiscina());
		list.put("dados04", getIntenExtern());
		list.put("dados05", getElevador());
		list.put("dados06", getAtivForaEstab());
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

		dados_html = new String[14]; // sempre mais 1

		dados_html[0] = "Nº de alunos";
		dados_html[1] = getNrAlunos();

		dados_html[2] = "Laboratório";
		if (getLaboratorio().equals("S")) {
			dados_html[3] = "SIM";
		} else {
			dados_html[3] = "NÃO";
		}

		dados_html[4] = "Piscina";
		if (getPiscina().equals("S")) {
			dados_html[5] = "SIM";
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Internato e/ou Externato";
		if (getIntenExtern().equals("S")) {
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

		dados_html[10] = "Realiza Atividade Fora do Estabelecimento";
		if (getAtivForaEstab().equals("S")) {
			dados_html[11] = "SIM";
		} else {
			dados_html[11] = "NÃO";
		}

		dados_html[12] = "Sinistros dos últimos 5 Anos";
		dados_html[13] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("NrAlunos");
		if (tmp == null)
			tmp = "0";
		setNrAlunos(tmp);

		tmp = req.getParameter("Laboratorio");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setLaboratorio(tmp);

		tmp = req.getParameter("Piscina");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setPiscina(tmp);

		tmp = req.getParameter("IntenExtern");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setIntenExtern(tmp);

		tmp = req.getParameter("Elevador");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setElevador(tmp);

		tmp = req.getParameter("AtivForaEstab");
		if (tmp == null || tmp.equals("N"))
			tmp = "N";
		else
			tmp = "S";
		setAtivForaEstab(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

//set

	public void setNrAlunos(String i) {
		this.nrAlunos = i;
	}

	public void setLaboratorio(String i) {
		this.laboratorio = i;
	}

	public void setPiscina(String i) {
		this.piscina = i;
	}

	public void setIntenExtern(String i) {
		this.intenExtern = i;
	}

	public void setElevador(String i) {
		this.elevador = i;
	}

	public void setAtivForaEstab(String i) {
		this.ativForaEstab = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get
	public String getNrAlunos() {
		return nrAlunos;
	}

	public String getLaboratorio() {
		return laboratorio;
	}

	public String getPiscina() {
		return piscina;
	}

	public String getIntenExtern() {
		return intenExtern;
	}

	public String getElevador() {
		return elevador;
	}

	public String getAtivForaEstab() {
		return ativForaEstab;
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

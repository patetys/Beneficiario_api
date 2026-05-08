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
 */
public class Cto5105Bean extends CotacaoBean {

	private static final long serialVersionUID = -2489522491435681968L;

	private String tpMoradia; // 1 - comercial / 2 - Residencial/ 3 - Misto / 4 - Escritorio/Consultorio / 5
								// Apart-Hoteis/Flats Residenciais
	private String areaConstrucao; // Area construida m2
	private String nrPavimentos; // Nr de pavimentoes
	private String elevadores; // S - sim / N -nao
	private String elevNrAndars; // Nr de Andares
	private String elevCapPessoa; // Capacidades de pessoas
	private String antenas; // S - sim / N - nao
	private String anuncios; // S - sim / N - nao
	private String piscinas; // S - sim / N - nao
	private String escadRol; // Escadas rolantes // S - sim / N - nao
	private String saunas; // S - sim / N - nao
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div5105Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setTpMoradia((String) d.get("dados01"));
		setAreaConstrucao((String) d.get("dados02"));
		setNrPavimentos((String) d.get("dados03"));
		setElevadores((String) d.get("dados04"));
		setElevNrAndars((String) d.get("dados05"));
		setElevCapPessoa((String) d.get("dados06"));
		setAnuncios((String) d.get("dados07"));
		setPiscinas((String) d.get("dados08"));
		setEscadRol((String) d.get("dados09"));
		setSaunas((String) d.get("dados10"));
		setAntenas((String) d.get("dados11"));
		setNumSin5anos((String) d.get("dados12"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getTpMoradia());
		list.put("dados02", getAreaConstrucao());
		list.put("dados03", getNrPavimentos());
		list.put("dados04", getElevadores());
		list.put("dados05", getElevNrAndars());
		list.put("dados06", getElevCapPessoa());
		list.put("dados07", getAnuncios());
		list.put("dados08", getPiscinas());
		list.put("dados09", getEscadRol());
		list.put("dados10", getSaunas());
		list.put("dados11", getAntenas());
		list.put("dados12", getNumSin5anos());
		list.put("dados13", " ");
		list.put("dados14", " ");
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;

	}

	public void setDadosHtml() {

		dados_html = new String[24]; // sempre mais 1

		dados_html[0] = "Tipo Moradia";
		switch (Integer.parseInt(getTpMoradia().trim())) {
		case 1:
			dados_html[1] = "COMERCIAL";
			break;
		case 2:
			dados_html[1] = "RESIDENCIAL";
			break;
		case 3:
			dados_html[1] = "MISTO";
			break;
		case 4:
			dados_html[1] = "ESCRITÓRIO/CONSULTÓRIO";
			break;
		case 5:
			dados_html[1] = "APART-HOTÉIS/FLATS RESIDÊNCIAIS";
			break;
		default:
			dados_html[1] = "";
			break;
		}

		dados_html[2] = "Área construida m<FONT style='vertical-align: text-top;font-size: 6pt;'>2</FONT>";
		dados_html[3] = getAreaConstrucao();

		dados_html[4] = "Nº de pavimentos";
		dados_html[5] = getNrPavimentos();

		dados_html[6] = "Elevadores";
		if (getElevadores().equals("S")) {
			dados_html[7] = "SIM";
		} else {
			dados_html[7] = "NÃO";
		}

		dados_html[8] = "Nº de Andares";
		dados_html[9] = getElevNrAndars();

		dados_html[10] = "Capacidades de Pessoas";
		dados_html[11] = getElevCapPessoa();

		dados_html[12] = "Antenas";
		if (getAntenas().equals("S")) {
			dados_html[13] = "SIM";
		} else {
			dados_html[13] = "NÃO";
		}

		dados_html[14] = "Anuncios";
		if (getAnuncios().equals("S")) {
			dados_html[15] = "SIM";
		} else {
			dados_html[15] = "NÃO";
		}

		dados_html[16] = "Piscinas";
		if (getPiscinas().equals("S")) {
			dados_html[17] = "SIM";
		} else {
			dados_html[17] = "NÃO";
		}

		dados_html[18] = "Escadas Rolantes";
		if (getEscadRol().equals("S")) {
			dados_html[19] = "SIM";
		} else {
			dados_html[19] = "NÃO";
		}

		dados_html[20] = "Saunas";
		if (getSaunas().equals("S")) {
			dados_html[21] = "SIM";
		} else {
			dados_html[21] = "NÃO";
		}

		dados_html[22] = "Sinistros dos últimos 5 Anos";
		dados_html[23] = getNumSin5anos();

	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("TpMoradia");
		if (tmp == null)
			tmp = "1";
		setTpMoradia(tmp);

		tmp = req.getParameter("AreaConstrucao");
		if (tmp == null)
			tmp = "0";
		setAreaConstrucao(tmp);

		tmp = req.getParameter("NrPavimentos");
		if (tmp == null)
			tmp = "0";
		setNrPavimentos(tmp);

		tmp = req.getParameter("Elevadores");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setElevadores(tmp);
		if (tmp.equals("S")) {
			setElevNrAndars(req.getParameter("ElevNrAndars"));
			setElevCapPessoa(req.getParameter("ElevCapPessoa"));
		} else {
			setElevNrAndars("0");
			setElevCapPessoa("0");
		}

		tmp = req.getParameter("Anuncios");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setAnuncios(tmp);

		tmp = req.getParameter("Piscinas");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setPiscinas(tmp);

		tmp = req.getParameter("EscadRol");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setEscadRol(tmp);

		tmp = req.getParameter("Saunas");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setSaunas(tmp);

		tmp = req.getParameter("Antenas");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";
		setAntenas(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

//set	
	public void setTpMoradia(String i) {
		this.tpMoradia = i;
	}

	public void setAreaConstrucao(String i) {
		this.areaConstrucao = i;
	}

	public void setNrPavimentos(String i) {
		this.nrPavimentos = i;
	}

	public void setElevadores(String i) {
		this.elevadores = i;
	}

	public void setElevNrAndars(String i) {
		this.elevNrAndars = i;
	}

	public void setElevCapPessoa(String i) {
		this.elevCapPessoa = i;
	}

	public void setAnuncios(String i) {
		this.anuncios = i;
	}

	public void setPiscinas(String i) {
		this.piscinas = i;
	}

	public void setEscadRol(String i) {
		this.escadRol = i;
	}

	public void setSaunas(String i) {
		this.saunas = i;
	}

	public void setAntenas(String i) {
		this.antenas = i;
	}

	public void setNumSin5anos(String i) {
		this.numSin5anos = i;
	}

//get

	public String getTpMoradia() {
		return tpMoradia;
	}

	public String getAreaConstrucao() {
		return areaConstrucao;
	}

	public String getNrPavimentos() {
		return nrPavimentos;
	}

	public String getElevadores() {
		return elevadores;
	}

	public String getElevNrAndars() {
		return elevNrAndars;
	}

	public String getElevCapPessoa() {
		return elevCapPessoa;
	}

	public String getAnuncios() {
		return anuncios;
	}

	public String getPiscinas() {
		return piscinas;
	}

	public String getEscadRol() {
		return escadRol;
	}

	public String getSaunas() {
		return saunas;
	}

	public String getAntenas() {
		return antenas;
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

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
public class Cto114Bean extends CotacaoBean {

	private static final long serialVersionUID = -2590693375040713796L;

	private String tpMoradia; // 1- Comercial / 2 - Residencial / 3 - misto / 4 - Escritorio/Consultorio /5
								// Apart-Hoteis/Flats Residencias
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div114Cad.jsp"; // Pagina jsp
	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setTpMoradia((String) d.get("dados01"));
		setNumSin5anos((String) d.get("dados02"));

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getTpMoradia());
		list.put("dados02", getNumSin5anos());
		list.put("dados03", " ");
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
		list.put("dados14", " ");
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;

	}

	public void setDadosHtml() {

		dados_html = new String[4]; // sempre mais um

		dados_html[0] = "Tipo de Moradia";
		if (getTpMoradia().equals("1")) {
			dados_html[1] = "COMERCIAL";
		} else if (getTpMoradia().equals("2")) {
			dados_html[1] = "RESIDENCIAL";
		} else if (getTpMoradia().equals("3")) {
			dados_html[1] = "MISTO";
		} else if (getTpMoradia().equals("4")) {
			dados_html[1] = "ESCRITÓRIO/CONSULTÓRIO";
		} else if (getTpMoradia().equals("5")) {
			dados_html[1] = "APART-HOTÉIS/FLATS RESIDÊNCIAIS";
		} else if (getTpMoradia().equals("6")) {
			dados_html[1] = "SHOPPING CENTER";
		} else if (getTpMoradia().equals("7")) {
			dados_html[1] = "CONDOMINIO HORIZONTAL RESIDENCIAL";
		} else {
			dados_html[1] = "";
		}
		dados_html[2] = "Sinistros dos últimos 5 Anos";
		dados_html[3] = getNumSin5anos();

	}

	public String[] getDadosHtml() {
		return dados_html;
	}

	/**
	 * Grava cotacao 114
	 *
	 */
	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("TpMoradia");
		if (tmp == null)
			tmp = "1";
		setTpMoradia(tmp);

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	/**
	 * Recupera cotacao 114
	 * 
	 * @param NumProtocolo
	 * @return
	 */
	public int getCto114Bean(String NumProtocolo) {
		return 0;
	}

//set
	public void setTpMoradia(String i) {
		tpMoradia = i;
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

//get	
	public String getTpMoradia() {
		return tpMoradia;
	}

	public String getNumSin5anos() {
		return numSin5anos;
	}

	public String getPaginaJSP() {
		return DIVCADJSP;
	}

}

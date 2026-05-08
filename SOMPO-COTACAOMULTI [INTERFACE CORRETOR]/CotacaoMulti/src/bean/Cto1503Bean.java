/*
 * Criado em 18/10/2006
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
 *         Roubo - Residencial
 *
 **/

public class Cto1503Bean extends CotacaoBean {

	private static final long serialVersionUID = 6614821474906454278L;

	private String tpConstrucao; // S- sim / N-nao
	private String tpMoradia; // 1 - casa / 2 - Apartamento
	private String tpUsoMoradia; // 1 - Habitual / 2 - Eventual / 3 - Desocupada
	private String tpMobiliario; // S/N - Mobiliario
	private String tpArtCouro; // S/N - Artigos de Couro
	private String tpCobertDanos; // S/N - Cobertura para danos
	private String vlrMobiliario; // Valor - Mobiliario
	private String vlrArtCouro; // Valor - Artigos de Couro
	private String vlrCobertDanos; // Valor - Cobertura para danos
	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private final String DIVCADJSP = "Div1503Cad.jsp"; // Pagina jsp
	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {

		setTpConstrucao((String) d.get("dados01"));
		setTpMoradia((String) d.get("dados02"));
		setTpUsoMoradia((String) d.get("dados03"));
		setTpMobiliario((String) d.get("dados04"));
		setTpArtCouro((String) d.get("dados05"));
		setTpCobertDanos((String) d.get("dados06"));
		setVlrMobiliario((String) d.get("dados07"));
		setVlrArtCouro((String) d.get("dados08"));
		setVlrCobertDanos((String) d.get("dados09"));
		setNumSin5anos((String) d.get("dados10"));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getTpConstrucao());
		list.put("dados02", getTpMoradia());
		list.put("dados03", getTpUsoMoradia());
		list.put("dados04", getTpMobiliario());
		list.put("dados05", getTpArtCouro());
		list.put("dados06", getTpCobertDanos());
		list.put("dados07", getVlrMobiliario());
		list.put("dados08", getVlrArtCouro());
		list.put("dados09", getVlrCobertDanos());
		list.put("dados10", getNumSin5anos());
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

		dados_html[0] = "Construção Alvenaria";
		if (getTpConstrucao().equals("S")) {
			dados_html[1] = "SIM";
		} else {
			dados_html[1] = "NÃO";
		}

		dados_html[2] = "Tipo Moradia";
		if (getTpMoradia().equals("1")) {
			dados_html[3] = "CASA";
		} else {
			dados_html[3] = "APARTAMENTO";
		}

		dados_html[4] = "Uso da  Moradia";
		switch (Integer.parseInt(getTpUsoMoradia().trim())) {
		case 1:
			dados_html[5] = "HABITUAL";
			break;
		case 2:
			dados_html[5] = "EVENTUAL";
			break;
		case 3:
			dados_html[5] = "DESOCUPADA";
			break;
		default:
			dados_html[5] = "";
			break;
		}

		dados_html[6] = "Mobiliário (R$)";
		if (getTpMobiliario().equals("S")) {
			dados_html[7] = getVlrMobiliario();
		} else {
			dados_html[7] = "NÃO";
		}

		dados_html[8] = "Artigos de Couro (R$)";
		if (getTpArtCouro().equals("S")) {
			dados_html[9] = getVlrArtCouro();
		} else {
			dados_html[9] = "NÃO";
		}

		dados_html[10] = "Cobertura para danos (R$)";
		if (getTpCobertDanos().equals("S")) {
			dados_html[11] = getVlrCobertDanos();
		} else {
			dados_html[11] = "NÃO";
		}

		dados_html[12] = "Sinistros dos últimos 5 Anos";
		dados_html[13] = getNumSin5anos();

	}

	public String[] getDadosHtml() {
		return dados_html;
	}

	@SuppressWarnings("unused")
	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("TpConstrucao");
		if (tmp == null)
			tmp = "S";
		setTpConstrucao(tmp);

		tmp = req.getParameter("TpMoradia");
		if (tmp == null)
			tmp = "1";
		setTpMoradia(tmp);

		tmp = req.getParameter("TpUsoMoradia");
		if (tmp == null)
			tmp = "1";
		setTpUsoMoradia(tmp);

		tmp = req.getParameter("TpMobiliario");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setTpMobiliario("S");
			setVlrMobiliario(req.getParameter("VlrMobiliario"));
		} else {
			setTpMobiliario("N");
			setVlrMobiliario("0");
		}

		tmp = req.getParameter("TpArtCouro");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setTpArtCouro("S");
			setVlrArtCouro(req.getParameter("VlrArtCouro"));
		} else {
			setTpArtCouro("N");
			setVlrArtCouro("0");
		}

		tmp = req.getParameter("TpCobertDanos");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setTpCobertDanos("S");
			setVlrCobertDanos(req.getParameter("VlrCobertDanos"));
		} else {
			setTpCobertDanos("N");
			setVlrCobertDanos("0");
		}

		req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	public void setTpConstrucao(String i) {
		this.tpConstrucao = i;
	}

	public void setTpMoradia(String i) {
		this.tpMoradia = i;
	}

	public void setTpUsoMoradia(String i) {
		this.tpUsoMoradia = i;
	}

	public void setTpMobiliario(String i) {
		this.tpMobiliario = i;
	}

	public void setTpArtCouro(String i) {
		this.tpArtCouro = i;
	}

	public void setTpCobertDanos(String i) {
		this.tpCobertDanos = i;
	}

	public void setVlrMobiliario(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		this.vlrMobiliario = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setVlrArtCouro(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		this.vlrArtCouro = Util.formataMoeda(Util.formataMoedaToDB(i));
		;
	}

	public void setVlrCobertDanos(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		this.vlrCobertDanos = Util.formataMoeda(Util.formataMoedaToDB(i));
		;
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

//get
	public String getTpConstrucao() {
		return tpConstrucao;
	}

	public String getTpMoradia() {
		return tpMoradia;
	}

	public String getTpUsoMoradia() {
		return tpUsoMoradia;
	}

	public String getTpMobiliario() {
		return tpMobiliario;
	}

	public String getTpArtCouro() {
		return tpArtCouro;
	}

	public String getTpCobertDanos() {
		return tpCobertDanos;
	}

	public String getVlrMobiliario() {
		return vlrMobiliario;
	}

	public String getVlrArtCouro() {
		return vlrArtCouro;
	}

	public String getVlrCobertDanos() {
		return vlrCobertDanos;
	}

	public String getNumSin5anos() {
		return numSin5anos;
	}

	public String getPaginaJSP() {
		return DIVCADJSP;
	}

}

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
 *         Roubo - Comercial/Industrial
 * 
 */
public class Cto1502Bean extends CotacaoBean {

	private static final long serialVersionUID = -8562905555328814033L;

	private String dscAtiv; // Descricao da Atividade
	private String tpMercMat; // S/N - Mercadorias e materias primas inerentes ao ramo de negocios
	private String tpMaqEquip; // S/N - Maquinas e equipamentos inerrentes ao ramo de negocios
	private String tpMobiliario; // S/N - Mobiliario
	private String tpCobertDanos; // S/N - Cobertura para danos causados a portas, janelas, etc

	private String vlrMercMat; // Mercadorias e materias primas inerentes ao ramo de negocios
	private String vlrMaqEquip; // Maquinas e equipamentos inerrentes ao ramo de negocios
	private String vlrMobiliario; // Mobiliario
	private String vlrCobertDanos; // Cobertura para danos causados a portas, janelas, etc

	private String numSin5anos; // Informar sinistralidade dos ultimos 5 anos

	private String alarme; // S/N
	private String vigia24hs; // S/N

	private final String DIVCADJSP = "Div1502Cad.jsp"; // Pagina jsp

	private String[] dados_html;

	@SuppressWarnings("rawtypes")
	public void setDBtoBean(Hashtable d) {
		setTpMaqEquip((String) d.get("dados01"));
		setTpMercMat((String) d.get("dados02"));
		setTpMobiliario((String) d.get("dados03"));
		setTpCobertDanos((String) d.get("dados04"));
		setVlrMercMat((String) d.get("dados05"));
		setVlrMaqEquip((String) d.get("dados06"));
		setVlrMobiliario((String) d.get("dados07"));
		setVlrCobertDanos((String) d.get("dados08"));
		setNumSin5anos((String) d.get("dados09"));
		setDscAtiv((String) d.get("dados10"));
		setAlarme((String) d.get("dados11"));
		setVigia24hs((String) d.get("dados12"));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int setDadosToDB(DbAccess dbAccess) throws SQLException {
		int ret = 0;

		Hashtable list = new Hashtable();

		list = setDadosDiv(list); // set dados principais da tabela

		list.put("dados01", getTpMaqEquip());
		list.put("dados02", getTpMercMat());
		list.put("dados03", getTpMobiliario());
		list.put("dados04", getTpCobertDanos());
		list.put("dados05", getVlrMercMat());
		list.put("dados06", getVlrMaqEquip());
		list.put("dados07", getVlrMobiliario());
		list.put("dados08", getVlrCobertDanos());
		list.put("dados09", getNumSin5anos());
		list.put("dados10", getDscAtiv());
		list.put("dados11", getAlarme());
		list.put("dados12", getVigia24hs());
		list.put("dados13", " ");
		list.put("dados14", " ");
		list.put("dados15", " ");

		ret = dbAccess.salvaTabCotacaoDiv(list); // executa gravacao

		return ret;
	}

	public void setDadosHtml() {

		dados_html = new String[16]; // sempre mais um

		dados_html[0] = "Descrição da Atividade";
		dados_html[1] = getDscAtiv();

		dados_html[2] = "Mercadorias e matérias primas inerentes ao ramo de negócio (R$)";
		if (getTpMercMat().equals("S")) {
			dados_html[3] = getVlrMercMat();
		} else {
			dados_html[3] = "NÃO";
		}

		dados_html[4] = "Máquinas e equipamentos inerentes ao ramo de negócios (R$)";
		if (getTpMaqEquip().equals("S")) {
			dados_html[5] = getVlrMaqEquip();
		} else {
			dados_html[5] = "NÃO";
		}

		dados_html[6] = "Mobiliário (R$)";
		if (getTpMobiliario().equals("S")) {
			dados_html[7] = getVlrMobiliario();
		} else {
			dados_html[7] = "NÃO";
		}

		dados_html[8] = "Cobertura para danos causados a portas, janelas, etc. (R$)";
		if (getTpCobertDanos().equals("S")) {
			dados_html[9] = getVlrCobertDanos();
		} else {
			dados_html[9] = "NÃO";
		}

		dados_html[10] = "Alarme";
		if (getAlarme().equals("S")) {
			dados_html[11] = "SIM";
		} else {
			dados_html[11] = "NÃO";
		}
		dados_html[12] = "Vigia 24hs";
		if (getVigia24hs().equals("S")) {
			dados_html[13] = "SIM";
		} else {
			dados_html[13] = "NÃO";
		}

		dados_html[14] = "Sinistros dos últimos 5 Anos";
		dados_html[15] = getNumSin5anos();

	}

	public String[] getDadosHtml() {
		return dados_html;
	}

	public void setDados(HttpServletRequest req) {

		String tmp;

		tmp = req.getParameter("DscAtiv");
		if (tmp == null)
			tmp = " ";
		setDscAtiv(tmp);

		tmp = req.getParameter("TpMercMat");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setTpMercMat("S");
			setVlrMercMat(req.getParameter("VlrMercMat"));
		} else {
			setTpMercMat("N");
			setVlrMercMat(req.getParameter("0"));
		}

		tmp = req.getParameter("TpMaqEquip");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setTpMaqEquip("S");
			setVlrMaqEquip(req.getParameter("VlrMaqEquip"));
		} else {
			setTpMaqEquip("N");
			setVlrMaqEquip(req.getParameter("0"));
		}

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
			setVlrMobiliario(req.getParameter("0"));
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
			setVlrCobertDanos(req.getParameter("0"));
		}

		tmp = req.getParameter("Alarme");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setAlarme("S");
		} else {
			setAlarme("N");
		}

		tmp = req.getParameter("Vigia24hs");
		if (tmp == null)
			tmp = "N";
		else
			tmp = "S";

		if (tmp.equals("S")) {
			setVigia24hs("S");
		} else {
			setVigia24hs("N");

		}

		tmp = req.getParameter("NumSin5anos");
		if (tmp == null)
			tmp = "0";
		setNumSin5anos(tmp);

	}

	public void setDscAtiv(String i) {
		dscAtiv = i;
	}

	public void setAlarme(String i) {
		alarme = i;
	}

	public void setVigia24hs(String i) {
		vigia24hs = i;
	}

	public void setTpMaqEquip(String i) {
		tpMaqEquip = i;
	}

	public void setTpMercMat(String i) {
		tpMercMat = i;
	}

	public void setTpMobiliario(String i) {
		tpMobiliario = i;
	}

	public void setTpCobertDanos(String i) {
		tpCobertDanos = i;
	}

	public void setVlrMercMat(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		vlrMercMat = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setVlrMaqEquip(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		vlrMaqEquip = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setVlrMobiliario(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		vlrMobiliario = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setVlrCobertDanos(String i) {
		if (i == null || i.equals("")) {
			i = "0";
		}
		vlrCobertDanos = Util.formataMoeda(Util.formataMoedaToDB(i));
	}

	public void setNumSin5anos(String i) {
		numSin5anos = i;
	}

	public String getDscAtiv() {
		return dscAtiv;
	}

	public String getAlarme() {
		return alarme;
	}

	public String getVigia24hs() {
		return vigia24hs;
	}

	public String getTpMercMat() {
		return tpMercMat;
	}

	public String getTpMaqEquip() {
		return tpMaqEquip;
	}

	public String getTpMobiliario() {
		return tpMobiliario;
	}

	public String getTpCobertDanos() {
		return tpCobertDanos;
	}

	public String getVlrMercMat() {
		return vlrMercMat;
	}

	public String getVlrMaqEquip() {
		return vlrMaqEquip;
	}

	public String getVlrMobiliario() {
		return vlrMobiliario;
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
